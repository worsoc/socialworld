/*
* Social World
* Copyright (C) 2019  Mathias Sikos
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.  
*
* or see http://www.gnu.org/licenses/gpl-2.0.html
*
*/

package org.socialworld.tools.mct;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.EventQueue;
//import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.awt.event.ActionEvent;



public class MapCreationTool {

	private JFrame frame;
	private JFrame visualizedMap;
	
	ShowMapPanel panelVisualizedMap;
	
	JPanel panelRechts;
	
	JPanel panelRaster;
	
	JPanel panelRechtsOben;
	JPanel panelRechtsMitte;
	JPanel panelRechtsUnten;
	
	JPanel panelTileSelectionLines[];

	
	JTextField infoFieldOben;
	JTextField infoFieldMitte;
	JTextField infoFieldUnten;
	
	JComboBox<String> chooseTileType;
	JComboBox<String> chooseTileTypeAlternative;
	
	Border thickBorder;
	Border thinBorder;
	Border buttonBorder;
	
	JButton buttonGenerateFromText;
	JButton buttonSaveComplete;
	JButton buttonLoadComplete;
	JButton buttonSaveRaster;
	JButton buttonLoadRaster;
	JButton buttonZoomIn;
	JButton buttonZoomOut;
	JButton buttonChooseTile;
	JButton buttonReduceToChoosableTiles;
	JButton buttonInfo;
	JButton buttonClear;
	JButton buttonGenerate;
	JButton buttonClearAll;
	JButton buttonShowMap;
	
	private RasterField raster[];
	private ChooseTile tileSelection[];
			
	int selectedRasterField = -1;
	int selectedTile = -1;
	
	Modus modus;
	
	private TileType type;
	private int tileTypeAlternative = 1;

	String comboboxEntriesLargeTiles[] = {"grosse Standard-Kacheln"};
	String comboboxEntriesMediumTiles[] = {"mittlere Standard-Kacheln", "Adapter von mittel zu gross"};
	String comboboxEntriesSmallTiles[] = {"kleine Standard-Kacheln", "Adapter von klein zu mittel", "kleine Spezial-Kacheln", "Adapter zu kleiner Spezial-Kachel"};

	String comboboxEntriesAlternativesAdapter[] = {"Anschluss an 0-0-Kante", "Anschluss an 0-1-Kante (bzw.1-0-Kante)", "Anschluss an 1-1-Kante"};
	String comboboxEntriesAlternativesSmallSpecialAdapter[] = {"Anschluss an Spezialkachel der Variante 1", "Anschluss an Spezialkachel der Variante 2", "Anschluss an Spezialkachel der Variante 3"};
	String comboboxEntriesAlternativesSmallSpecial[] = {"1 ... Anstieg im ersten Drittel", "2 ... Anstieg im zweiten Drittel", "3 ... Anstieg im dritten Drittel"};

	TileGrid tileTerm;
	
	TileGrid loadedTileGridFromFile;
	
	int largeParentRastersIndex;
	int mediumParentRastersIndex;
	
	PossibleTiles possibleTiles;
	HeightChangeChecker heightChangeChecker;
	TileSelectionPattern pattern;
	TileInfo tileInfo;
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	/**
	 * Create the application.
	 */
	public MapCreationTool() {
		type = TileType.largeStandard;
		tileTerm = new TileGrid(null);
		initialize();
		initRasterFieldLabels();
		
		possibleTiles =  PossibleTiles.getInstance();
		heightChangeChecker = HeightChangeChecker.getInstance();
		
		pattern = new TileSelectionPattern();
		tileInfo = new TileInfo();
		
		setTileSelection(possibleTiles.getAllLargeStandardTiles());
	}
	
	
	private enum Modus {
		reduce, choose, zoomIn, info, clear
	}
	
	
	private class RasterField extends JButton {
		
		int index;
		
		Tile tile;
		
		int height;
		int heightOffsetFromWest = 0;
		int heightOffsetFromNorth = 0;
		
		static final long serialVersionUID = 1;
		
		
		private RasterField(int index) {
			super();
			//this.setFont(new Font("Arial", Font.PLAIN, 15));
			setIndex(index);
			unselect();
			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {				  
					clickRasterField(((RasterField) e.getSource()).getIndex());
				}															
			});
		}
		
		private void clear() {
			this.setToDo();
			this.setText();
		}
		
		private void setIndex(int index) {
			this.index = index;
		}

		private int getIndex() {
			return this.index;
		}
			
		private void select() {
			setBorder(thickBorder);
		}

		private void unselect() {
			setBorder(thinBorder);
			setBackground(Color.LIGHT_GRAY );
		}
		
		private void setToDo() {
			this.setTile(TileType.todo, 0,-2);
		}
		
		private void setSub() {
			this.tile = new Tile(TileType.sub);
		}
		
		private void setTile(TileType type, int alternative, int tileNumber) {
			this.tile = new Tile(type);
			this.tile.setAlternative(alternative);
			//if (tileNumber >= 0) {
				this.tile.setNumber(tileNumber);
			//}
		}
		
		private Tile getTile() {
			return this.tile;
		}
		
		private TileType getTileType() {
			return this.tile.getType();
		}
		
		private int getTileTypeAlternative() {
			return this.tile.getTileTypeAlternative();
		}

		private int getTileNumber() {
			return this.tile.getNumber();
		}
		
		private void setText() {
			String text;
			
			switch (this.tile.getType()) {
				case smallStandard: text = "S" + this.tile.getNumber(); break;
				case smallAdapter: text = "SA" + this.tile.getNumber(); break;
				case smallSpecial: text = "SX" + this.tile.getNumber(); break;
				case smallSpecialAdapter: text = "SXA" + this.tile.getNumber(); break;
				case mediumStandard: text = "M" + this.tile.getNumber(); break;
				case mediumAdapter: text = "MA" + this.tile.getNumber(); break;
				case largeStandard: text = "L" + this.tile.getNumber(); break;
				case sub: text = "sub"; break;
				case todo: text = "TODO"; break;
				default: text = "TODO";
			}
			this.setText(text);
		}
	}
	
	private class ChooseTile extends JButton {
		int index;
		int tileNumber;
		
		static final long serialVersionUID = 2;
		
		private ChooseTile(int index) {
			super();
			setIndex(index);
			unselect();
			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					clickTileSelction(((ChooseTile) e.getSource()).getIndex());
				}
			});
		}
		
		private void setIndex(int index) {
			this.index = index;
		}

		private int getIndex() {
			return this.index;
		}
		
		private void setTileNumber(int number) {
			this.tileNumber =  number;
		}

		private int getTileNumber() {
			return this.tileNumber;
		}
	
		
		private void select() {
			setBackground(Color.GREEN);
		}

		private void unselect() {
			setBackground(Color.LIGHT_GRAY );
		}
		
		private void activate(int tileNumber, String label) {
			setTileNumber(tileNumber);
			setText(label);
			setEnabled(true);
		}
		
		private void deactivate() {
			setText("");
			setEnabled(false);
		}
	
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MapCreationTool window = new MapCreationTool();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}	
	
	private void clickRasterField(int index) {
		
		TileGrid child;
		int tileNumber;
		
		if ( (modus == Modus.choose) || (modus == Modus.clear) || (modus == Modus.reduce) || (modus == Modus.info) ) {
		
			if (selectedRasterField == -1) {
				raster[index].select();
				selectedRasterField = index;
			}
			else if (index != selectedRasterField) {
				raster[selectedRasterField].unselect();
				raster[index].select();
				selectedRasterField = index;
			}
			
			///////////////////////////////////////////
			
			if (modus == Modus.choose) {
				if (selectedTile >= 0) {
					
					clearInfoFieldMitte();
					tileNumber = tileSelection[selectedTile].getTileNumber();
					if (check(index, tileNumber) == true) {
					
						switch (type) {
						case smallAdapter:
							tileNumber = tileNumber + tileTypeAlternative * 100;
							break;
						case smallSpecial:
							tileNumber = tileNumber + tileTypeAlternative * 100;
							break;
						case smallSpecialAdapter:
							tileNumber = tileNumber + tileTypeAlternative * 100;
							break;
						case mediumAdapter:
							tileNumber = tileNumber + tileTypeAlternative * 100;
							break;
						default: ;
						}

						raster[index].setTile(type, tileTypeAlternative, tileNumber);
						raster[index].setText();
						
						if (index == 0) {
							// TODO input height
							raster[index].height = 1;
						}
					
					}
					else {
						
						infoFieldMitte.setText("Platzierung der gewaehlten Kachel nicht zulaessig");
						
					}
					
				}
			}

			///////////////////////////////////////////
			
			if (modus == Modus.clear) {
				
				raster[index].setToDo();
				raster[index].setText();
				clearInfoFieldMitte();
			}
			
			///////////////////////////////////////////
			
			if (modus == Modus.reduce) {

				reduce(index);
			}
			
			
			if (modus == Modus.info) {
				infoFieldMitte.setText(tileInfo.getTileInfo(raster[index].getTileType(), raster[index].getTileNumber()));
			}
			
		}
		
		if ( modus == Modus.zoomIn) {
			
			switch (type) {
				case largeStandard: largeParentRastersIndex = index; break;
				case mediumStandard: mediumParentRastersIndex = index; break;
				case mediumAdapter: mediumParentRastersIndex = index; break;
				default:
			}
			    
			raster[index].setSub();
			raster[index].setText();
			saveTileTermIntern();

			child = tileTerm.getChildGrid(index );
			tileTerm.addTile(child, index );
			tileTerm = child;
			
			switch (type) {
				case largeStandard: type = TileType.mediumStandard;
					setTypeComboboxEntries(comboboxEntriesMediumTiles);
					setTileSelection(possibleTiles.getAllMediumStandardTiles());
					break;
				case mediumStandard:  type = TileType.smallStandard; 
					setTypeComboboxEntries(comboboxEntriesSmallTiles);
					setTileSelection(possibleTiles.getAllSmallStandardTiles());
					break;
				case  mediumAdapter: type = TileType.smallStandard; 
					setTypeComboboxEntries(comboboxEntriesSmallTiles);
					setTileSelection(possibleTiles.getAllSmallStandardTiles());
					break;
				default: break;
			}
			
			
			fillTileRaster();
			
			// zoomIn wieder raus, auf Info, damit man sich nix kaputt macht
			info();
		}
	}
	
	private Set<Integer> getReducedSet(int index) {
	
		int indexEast;
		int indexWest;
		int indexNorth;
		int indexSouth;
		
		int neighbourTileNumberWithoutOffset;
		int neighbourAlternative;
		
		Set<Integer> neighboursAllowedFromEast;
		Set<Integer> neighboursAllowedFromWest;
		Set<Integer> neighboursAllowedFromNorth;
		Set<Integer> neighboursAllowedFromSouth;
		
		Integer emptyset[] = {};						
		Set<Integer> empty = new HashSet<Integer>(Arrays.asList(emptyset));

		neighboursAllowedFromEast = empty;
		neighboursAllowedFromWest = empty;
		neighboursAllowedFromNorth = empty;
		neighboursAllowedFromSouth = empty;
		
		///////////////////////////////////////////
		
		if ((index == 8 ) || (index == 17) || (index == 26 ) || (index == 35 ) || (index == 44 ) || (index == 53 ) || 
				(index == 62 ) || (index == 71 ) || (index == 80 )) indexEast = -1;
		else	indexEast = index + 1;
		
		if ((index == 0) ||(index == 9 ) || (index == 18) || (index == 27 ) || (index == 36 ) || (index == 45 ) || (index == 54 ) || 
				(index == 63 ) || (index == 72 )) indexWest = -1;
		else	indexWest = index - 1;	
		
		if (index > 8)	indexNorth    = index - 9;
		else 			indexNorth = -1;
		
		if (index < 72)	indexSouth  = index + 9;
		else 			indexSouth = -1;
		
		///////////////////////////////////////////
		
		Tile tileFromEast; 
		if (indexEast >= 0) {
			 tileFromEast = raster[indexEast].tile;
		}
		else {
			tileFromEast = Tile.getInstance();
		}
	
		Tile tileFromWest; 
		if (indexWest >= 0) {
			tileFromWest = raster[indexWest].tile;
		}
		else {
			tileFromWest = Tile.getInstance();
		}

		Tile tileFromNorth; 
		if (indexNorth >= 0) {
			tileFromNorth = raster[indexNorth].tile;
		}
		else {
			tileFromNorth = Tile.getInstance();
		}
		
		Tile tileFromSouth; 
		if (indexSouth >= 0) {
			tileFromSouth = raster[indexSouth].tile;
		}
		else {
			tileFromSouth = Tile.getInstance();
		}
		
		return CalculatePossibleTiles.getReducedSet(type, tileTypeAlternative, 
				 tileFromEast,  tileFromWest,  tileFromNorth,  tileFromSouth) ;
	//	return possibleTiles.getReducedTileSet(neighboursAllowedFromEast, neighboursAllowedFromWest, neighboursAllowedFromNorth, neighboursAllowedFromSouth);
				
	}
	
	void reduce(int index) {
		
		Set<Integer> reduced;
		setRasterNumberToInfoField(index);
		reduced = getReducedSet(index);
		setTileSelection(reduced);
		
	}
	
	boolean check(int index, int tileNumber) {
		
		Set<Integer> allowedTileNumbers;
		Set<Integer> tmpSetWithSelectTileNumber;
		
		int tileNumberWithoutOffset;
		
		switch (type) {
			case smallAdapter:
				tileNumberWithoutOffset = tileNumber - tileTypeAlternative * 100; break;
			case smallSpecial:
				tileNumberWithoutOffset = tileNumber - tileTypeAlternative * 100; break;
			case smallSpecialAdapter:
				tileNumberWithoutOffset = tileNumber - tileTypeAlternative * 100; break;
			case mediumAdapter:
				tileNumberWithoutOffset = tileNumber - tileTypeAlternative * 100; break;
			default: 			tileNumberWithoutOffset = tileNumber;
		}

		tmpSetWithSelectTileNumber = new  HashSet<Integer>(); 
		tmpSetWithSelectTileNumber.add(tileNumberWithoutOffset);
		
		allowedTileNumbers = getReducedSet(index);
		
		return allowedTileNumbers.containsAll(tmpSetWithSelectTileNumber);
		
	}

	private void clearInfoFieldOben() {
		infoFieldOben.setText("");
	}
	
	private void clearInfoFieldMitte() {
		infoFieldMitte.setText("");
	}

	
	private void setRasterNumberToInfoField(int rasterNumber) {
		
		int zeile = ((rasterNumber) / 9) + 1;
		int spalte = rasterNumber % 9;
		String spalteLetter = "";
		
		switch (spalte ) {
		case 1: spalteLetter = "A"; break;
		case 2: spalteLetter = "B"; break;
		case 3: spalteLetter = "C"; break;
		case 4: spalteLetter = "D"; break;
		case 5: spalteLetter = "E"; break;
		case 6: spalteLetter = "F"; break;
		case 7: spalteLetter = "G"; break;
		case 8: spalteLetter = "H"; break;
		case 0: spalteLetter = "I"; break;
		}
		String strI = spalteLetter + zeile + " (" + rasterNumber + ")";
		infoFieldMitte.setText(strI);
	}
	
	private void clickTileSelction(int index) {
		
		clearInfoFieldMitte();

		if (index >= 0)  {
			if (selectedTile == -1) {
				tileSelection[index].select();
				selectedTile = index;
			}
			else if (index != selectedTile) {
				tileSelection[selectedTile].unselect();
				tileSelection[index].select();
				selectedTile = index;
			}
			showChoosenTileInfo(tileSelection[index].getTileNumber());
		}
	}
	
	private void showChoosenTileInfo(int tileNumber) {
		infoFieldOben.setText(tileInfo.getTileInfo(type, tileNumber));
	}
	
	
	private void saveTileTermIntern() {
		
		
		//tileTerm.setHeights();
		return;
		
/*		int heightFromWest;
		int heightFromNorth;
		int height;
		
		for (int i = 0; i < 81; i++) {
			
			if ((i % 9) < 8) {
				// check height increase/decrease to right neighbour
				
					
					if (heightChangeChecker.isRightIncrease(raster[i].getTile(), raster[i+1].getTile())) {
						raster[i+1].heightOffsetFromWest = 1;
					}
					else if (heightChangeChecker.isRightDecrease(raster[i].getTile(), raster[i+1].getTile())) {
						raster[i+1].heightOffsetFromWest = -1;
					}
					else {
						raster[i+1].heightOffsetFromWest = 0;
					}
			}
			
			if (i < 72) {
				// check height increase/decrease to lower neighbour

					
					if (heightChangeChecker.isDownIncrease(raster[i].getTile(), raster[i+9].getTile())) {
						raster[i+9].heightOffsetFromNorth = 1;
					}
					else if (heightChangeChecker.isDownDecrease(raster[i].getTile(), raster[i+9].getTile())) {
						raster[i+9].heightOffsetFromNorth = -1;
					}
					else {
						raster[i+9].heightOffsetFromNorth = 0;
					}
			
			
			}
			
		}
		
		for (int i = 0; i < 81; i++) {
			
			heightFromWest = -888;
			heightFromNorth = -777;
			if ((i % 9) > 0) {
				heightFromWest = raster[i-1].height + raster[i].heightOffsetFromWest;
			}
			if (i > 8) {
				heightFromNorth = raster[i-9].height + raster[i].heightOffsetFromNorth;
			}
			if ((heightFromWest == -888) && (heightFromNorth == -777) ) height = tileTerm.getHeightLevel();
			else if (heightFromNorth == -777) height =  heightFromWest;
			else if (heightFromWest == -888) height =  heightFromNorth;
			else {
				
				if (heightFromWest == heightFromNorth) height = heightFromWest;
				else {
					height = -999;
					System.out.println("Fehler Höhenbestimmung bei Index: " + i + " --> Höhe von oben: " + heightFromNorth + " und Höhe von links: " + heightFromWest);
				}

				if (heightFromWest == -888) height = heightFromNorth;
				if (heightFromNorth == -777) height = heightFromWest;
			}
			
			raster[i].height = height;
			System.out.println("Index " + i + " mit Höhe " + height);
		
		}
			
		
		for (int i = 0; i < 81; i++) {
			
			if ( (!raster[i].getText().equals( "sub")) && (!raster[i].getText().equals( "TODO")) ) {
				tileTerm.addTile(new Tile(raster[i].getTileType(),  raster[i].getTileNumber(), raster[i].height), i);
			}
			
				
		}
		*/
	
	}
	
	

	////////////////////////////////////////////////////////////////
	
	private void saveTotal() {

		clearInfoFieldOben();
		clearInfoFieldMitte();
		
		zoomOut();
		zoomOut();
		
		modus = null;
		
		buttonReduceToChoosableTiles.setBorder(buttonBorder);
		buttonChooseTile.setBorder(buttonBorder);
		buttonInfo.setBorder(buttonBorder);
		buttonClear.setBorder(buttonBorder);
		buttonZoomIn.setBorder(buttonBorder);
		
		resetRasterFieldSelection();
	
		infoFieldUnten.setText("");
		
		saveTileTermIntern();
		
		String filename =  "tileterm_save.txt";
		try
		{
			// false ... replace
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename, false));
			writer.write(tileTerm.toString());
			writer.close();
			
		}
		
		catch(IOException e1)
		{
			e1.printStackTrace();	
		}
		
		
	}
	
	////////////////////////////////////////////////////////////////

	
	private void saveRaster() {

		clearInfoFieldOben();
		clearInfoFieldMitte();
		
		modus = null;
		
		buttonReduceToChoosableTiles.setBorder(buttonBorder);
		buttonChooseTile.setBorder(buttonBorder);
		buttonInfo.setBorder(buttonBorder);
		buttonClear.setBorder(buttonBorder);
		buttonZoomIn.setBorder(buttonBorder);
		
		resetRasterFieldSelection();
	
		infoFieldUnten.setText("");
		
		saveTileTermIntern();
		
		long millis=System.currentTimeMillis(); 
		String typeLevel = "";
		if (this.type.isSmall()) typeLevel = "S";
		if (this.type.isMedium()) typeLevel = "M";
		if (this.type.isLarge()) typeLevel = "L";
		String filename =  "raster_" + typeLevel + "_"+ millis+ ".txt";
		
		try
		{
			// false ... replace
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename, false));
			writer.write(tileTerm.toString());
			writer.close();
			
		}
		
		catch(IOException e1)
		{
			e1.printStackTrace();	
		}
		
		
	}
	
	////////////////////////////////////////////////////////////////
	
	private void generateFromText() {
		
		clearInfoFieldOben();
		clearInfoFieldMitte();
		
		modus = null;
		
		buttonReduceToChoosableTiles.setBorder(buttonBorder);
		buttonChooseTile.setBorder(buttonBorder);
		buttonInfo.setBorder(buttonBorder);
		buttonClear.setBorder(buttonBorder);
		buttonZoomIn.setBorder(buttonBorder);
		
		resetRasterFieldSelection();
		
		infoFieldUnten.setText("");
		
		String tileTermLoad = "";
		String dateiname = "tileterm_generate.txt";
		try
		{
			File datei = new File(dateiname);
			FileReader fr = new FileReader(datei);
			BufferedReader br = new BufferedReader (fr);
			tileTermLoad =  br.lines().collect(Collectors.joining());
			br.close();
		}
		catch (IOException e1)
		{
			e1.printStackTrace();	
		}
		System.out.println(tileTermLoad);
		
		type = TileType.largeStandard;
		setTypeComboboxEntries(comboboxEntriesLargeTiles);
		
		System.out.println();
		System.out.println();
		System.out.println();
		TileGrid grid = new TileGrid(tileTermLoad, 0); 
		System.out.println();
//		System.out.println(grid.toString());
		
		this.tileTerm = grid;
		
		fillTileRaster();
		
		setTileSelection(possibleTiles.getAllLargeStandardTiles());

	}
	
	
	private void loadTotal() {
		
		clearInfoFieldOben();
		clearInfoFieldMitte();
		
		modus = null;
		
		buttonReduceToChoosableTiles.setBorder(buttonBorder);
		buttonChooseTile.setBorder(buttonBorder);
		buttonInfo.setBorder(buttonBorder);
		buttonClear.setBorder(buttonBorder);
		buttonZoomIn.setBorder(buttonBorder);
		
		resetRasterFieldSelection();
		
		infoFieldUnten.setText("");
		
		String tileTermLoad = "";
		String dateiname = "tileterm.txt";
		try
		{
			File datei = new File(dateiname);
			FileReader fr = new FileReader(datei);
			BufferedReader br = new BufferedReader (fr);
			tileTermLoad = br.readLine();
			br.close();
		}
		catch (IOException e1)
		{
			e1.printStackTrace();	
		}
		String tileTermWithoutSub = tileTermLoad.replaceAll("\n","");
		tileTermWithoutSub = tileTermWithoutSub.replaceAll("sub","");
		parseString(tileTermWithoutSub, 1, null);
		this.tileTerm = loadedTileGridFromFile;
		
		System.out.println(tileTerm.toString());
		fillTileRaster();
		
		type = TileType.largeStandard;
		setTypeComboboxEntries(comboboxEntriesLargeTiles);
		setTileSelection(possibleTiles.getAllLargeStandardTiles());
		
		
	}

	////////////////////////////////////////////////////////////////
	
	private void loadRaster() {
		
		clearInfoFieldOben();
		clearInfoFieldMitte();
		
		modus = null;
		
		buttonReduceToChoosableTiles.setBorder(buttonBorder);
		buttonChooseTile.setBorder(buttonBorder);
		buttonInfo.setBorder(buttonBorder);
		buttonClear.setBorder(buttonBorder);
		buttonZoomIn.setBorder(buttonBorder);
		
		resetRasterFieldSelection();
		
		infoFieldUnten.setText("");
		
		String tileTermLoad = "";
		
		String dateiname;
        JFileChooser chooser = new JFileChooser();
        chooser.changeToParentDirectory();
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
        	dateiname = chooser.getSelectedFile().getName();
        	
        	if ( (!dateiname.contains("raster_L")) && (!dateiname.contains("raster_M")) && (!dateiname.contains("raster_S")) ) return;
        			
       		if ( (dateiname.contains("raster_L")) && (!this.type.isLarge()) ) return;
       		if ( (dateiname.contains("raster_M")) && (!this.type.isMedium()) ) return;
       		if ( (dateiname.contains("raster_S")) && (!this.type.isSmall()) ) return;
        	
        	
        	
    		try
    		{
    			File datei = new File(dateiname);
    			FileReader fr = new FileReader(datei);
    			BufferedReader br = new BufferedReader (fr);
    			tileTermLoad = br.readLine();
    			br.close();
    		}
    		catch (IOException e1)
    		{
    			e1.printStackTrace();	
    		}
    		
    		String tileTermWithoutSub = tileTermLoad.replaceAll("sub","");
    		parseString(tileTermWithoutSub, 1, this.tileTerm.getParentGrid());
    		
    		if (this.type.isMedium())
    			 this.tileTerm.getParentGrid().addTile(loadedTileGridFromFile, largeParentRastersIndex); 
    		else if	(this.type.isSmall()) 
    			this.tileTerm.getParentGrid().addTile(loadedTileGridFromFile, mediumParentRastersIndex); 
    		
    		this.tileTerm = loadedTileGridFromFile;
    		
    		System.out.println(tileTerm.toString());
    		fillTileRaster();
    		
    		if (dateiname.contains("raster_L") ) {
    			this.type = TileType.largeStandard;
        		setTypeComboboxEntries(comboboxEntriesLargeTiles);
        		setTileSelection(possibleTiles.getAllLargeStandardTiles());
    		}
    		if (dateiname.contains("raster_M") )  {
    			this.type = TileType.mediumStandard;
        		setTypeComboboxEntries(comboboxEntriesMediumTiles);
        		setTileSelection(possibleTiles.getAllMediumStandardTiles());
    		}
    		if (dateiname.contains("raster_S") )  		{
    			this.type = TileType.smallStandard;
        		setTypeComboboxEntries(comboboxEntriesSmallTiles);
        		setTileSelection(possibleTiles.getAllSmallStandardTiles());
    		}
    		

        };
		
	}
	
	
	////////////////////////////////////////////////////////////////
	
	private void zoomIn() {
		
		clearInfoFieldOben();
		clearInfoFieldMitte();

		if ( (type == TileType.largeStandard) || (type == TileType.mediumStandard) ) {

			modus = Modus.zoomIn;
	
			buttonZoomIn.setBorder(thickBorder);		
			buttonReduceToChoosableTiles.setBorder(buttonBorder);
			buttonInfo.setBorder(buttonBorder);
			buttonClear.setBorder(buttonBorder);
			buttonChooseTile.setBorder(buttonBorder);
			
			resetRasterFieldSelection();
			
			infoFieldUnten.setText("bei Click auf ein Rasterfeld erfolgt ein Hereinzoomen in das Rasterfeld (Levelabstieg)");
		}
	}
	
	////////////////////////////////////////////////////////////////
	
	private void zoomOut() {

		clearInfoFieldOben();
		clearInfoFieldMitte();
		
		if ( (type == TileType.smallStandard) || (type == TileType.smallAdapter) || (type == TileType.smallSpecialAdapter) || (type == TileType.smallSpecial) || (type == TileType.mediumStandard) || (type == TileType.mediumAdapter) ) {
		
			modus = null;
			
			buttonReduceToChoosableTiles.setBorder(buttonBorder);
			buttonChooseTile.setBorder(buttonBorder);
			buttonInfo.setBorder(buttonBorder);
			buttonClear.setBorder(buttonBorder);
			buttonZoomIn.setBorder(buttonBorder);
			
			resetRasterFieldSelection();
	
			infoFieldUnten.setText("");
			
			saveTileTermIntern();
			
			if (tileTerm.getParentGrid() != null) {
				tileTerm = tileTerm.getParentGrid();
				
				switch (type) {
					case smallStandard: type = TileType.mediumStandard;
						setTypeComboboxEntries(comboboxEntriesMediumTiles);
						setTileSelection(possibleTiles.getAllMediumStandardTiles());
						break;
					case smallAdapter: type = TileType.mediumStandard;
						setTypeComboboxEntries(comboboxEntriesMediumTiles);
						setTileSelection(possibleTiles.getAllMediumStandardTiles());
						break;
					case smallSpecial: type = TileType.mediumStandard;
						setTypeComboboxEntries(comboboxEntriesMediumTiles);
						setTileSelection(possibleTiles.getAllMediumStandardTiles());
						break;
					case smallSpecialAdapter: type = TileType.mediumStandard;
						setTypeComboboxEntries(comboboxEntriesMediumTiles);
						setTileSelection(possibleTiles.getAllMediumStandardTiles());
						break;
					case mediumStandard: type = TileType.largeStandard; 
						setTypeComboboxEntries(comboboxEntriesLargeTiles);
						setTileSelection(possibleTiles.getAllLargeStandardTiles());
						break;
					case mediumAdapter: type = TileType.largeStandard; 
						setTypeComboboxEntries(comboboxEntriesLargeTiles);
						setTileSelection(possibleTiles.getAllLargeStandardTiles());
						break;
					default: break;
				}

				
				fillTileRaster();
			}

		}
	}

	private void clear() {

			modus = Modus.clear;
			
			buttonClear.setBorder(thickBorder);
			buttonChooseTile.setBorder(buttonBorder);
			buttonReduceToChoosableTiles.setBorder(buttonBorder);
			buttonInfo.setBorder(buttonBorder);
			buttonZoomOut.setBorder(buttonBorder);
			
			resetRasterFieldSelection();
			
			infoFieldUnten.setText("bei Click auf ein Rasterfeld erfolgt das Zuruecksetzen auf TODO der gewaehlten Kachel des Rasters");
			
			
			clearInfoFieldMitte();
		
		
		
		
	}

	private void reduce() {
		
		if (modus == Modus.reduce) {
			
			modus = null;
			
			buttonReduceToChoosableTiles.setBorder(buttonBorder);
			buttonChooseTile.setBorder(buttonBorder);
			buttonClear.setBorder(buttonBorder);
			buttonInfo.setBorder(buttonBorder);
			buttonZoomOut.setBorder(buttonBorder);
			
			switch (type) {
				case smallStandard:
					setTileSelection(possibleTiles.getAllSmallStandardTiles()); break;
				case smallAdapter:
					setTileSelection(possibleTiles.getAllAdapterTiles()); break;
				case smallSpecial:
					setTileSelection(possibleTiles.getAllSmallSpecialTiles()); break;
				case smallSpecialAdapter:
					setTileSelection(possibleTiles.getAllSmallSpecialAdapterTiles()); break;
				case mediumStandard:
					setTileSelection(possibleTiles.getAllMediumStandardTiles()); break;
				case mediumAdapter:
					setTileSelection(possibleTiles.getAllAdapterTiles()); break;
				case largeStandard:
					setTileSelection(possibleTiles.getAllLargeStandardTiles()); break;	
				default: break;
			}
			
		}
		else {
			modus = Modus.reduce;
		
			buttonReduceToChoosableTiles.setBorder(thickBorder);
			buttonChooseTile.setBorder(buttonBorder);
			buttonClear.setBorder(buttonBorder);
			buttonInfo.setBorder(buttonBorder);
			buttonZoomOut.setBorder(buttonBorder);
		
			resetRasterFieldSelection();
		
			infoFieldUnten.setText("bei Click auf ein Rasterfeld erfolgt eine Reduzierung der auswaehlbaren Kacheln");
		
		}
		
	}

	private void choose() {
		
		modus = Modus.choose;
		
		buttonChooseTile.setBorder(thickBorder);
		buttonReduceToChoosableTiles.setBorder(buttonBorder);
		buttonClear.setBorder(buttonBorder);
		buttonInfo.setBorder(buttonBorder);
		buttonZoomIn.setBorder(buttonBorder);
		
		resetRasterFieldSelection();
		
		infoFieldUnten.setText("bei Click auf ein Rasterfeld erfolgt die Uebernahme der gewaehlten Kachel in das Raster");
		
	}
	
	private void info() {
		
		
		modus = Modus.info;
		
		buttonInfo.setBorder(thickBorder);
		buttonReduceToChoosableTiles.setBorder(buttonBorder);
		buttonChooseTile.setBorder(buttonBorder);
		buttonClear.setBorder(buttonBorder);
		buttonZoomIn.setBorder(buttonBorder);
		
		resetRasterFieldSelection();

		infoFieldMitte.setText("");
		infoFieldUnten.setText("bei Click auf ein Rasterfeld wird die Information zur Kachel des Rasterfeldes angezeigt");
		
	}
	
	private void resetRasterFieldSelection() {
		if (selectedRasterField >= 0)	{
			raster[selectedRasterField].unselect();
			selectedRasterField = -1;
		}	
	}
	
	private void fillTileRaster() {
		
		int number;
		TileType type;
		int tileTypeAlternative;
		
		for (int i = 0; i < 81; i++) {
			
			type = tileTerm.getTileType(i);
			number = tileTerm.getTileNumber(i);
			tileTypeAlternative = tileTerm.getTileTypeAlternative(i);
			
			if (type == TileType.sub) {
				raster[i].setSub();
			}
			else if (type == TileType.todo) {
				
			}
			else {
				raster[i].setTile(type, tileTypeAlternative, number);
			}
			
			raster[i].setText();
		}
	}
	
	private void initRasterFieldLabels() {
		int i;
		
		switch (type) {
		case largeStandard:
			for (i = 0; i < 81; i++) {
				raster[i].setToDo(); // + i);
				raster[i].setText(); // + i);
			}
		default:
		}
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		int i;
		int j;
		
		
		thickBorder = new LineBorder(Color.GREEN, 6);
		thinBorder = new LineBorder(Color.BLACK, 1);

		raster = new RasterField[81];
		
		panelRaster = new JPanel();
		panelRaster.setLayout(new GridLayout(9,9));
		for (i = 0; i < 81; i++) {
			raster[i] = new RasterField(i);
			panelRaster.add(raster[i]);
		}

		
		
		
		panelRechts = new JPanel();
		panelRechts.setLayout(new GridLayout(3,1));
		
		panelRechtsOben = new JPanel();
		panelRechtsOben.setLayout(new GridLayout(10,1));
	
		String[] tileTypeAlternatives = { "1", "2", "3"};
		chooseTileTypeAlternative = new JComboBox<String>(tileTypeAlternatives);
		chooseTileTypeAlternative.addActionListener(
		        new ActionListener()
		        {
		            public void actionPerformed(final ActionEvent e)		    {
		                   switch (chooseTileTypeAlternative.getSelectedIndex()) {
			                   case 0:
			                	   tileTypeAlternative = 1;
			                	   break;
			                   case 1:  
			                	   tileTypeAlternative = 2;
			                	   break;
			                   case 2: 	
			                	   tileTypeAlternative = 3;
			                	   break;
		                	  default: break;
		                   }	
		                   if (type == TileType.mediumAdapter) setTileSelection(possibleTiles.getAllAdapterTiles());
		                   if (type == TileType.smallAdapter) setTileSelection(possibleTiles.getAllAdapterTiles());
		                   if (type == TileType.smallSpecial) setTileSelection(possibleTiles.getAllSmallSpecialTiles());
		                   if (type == TileType.smallSpecialAdapter) setTileSelection(possibleTiles.getAllSmallSpecialAdapterTiles());
		            }
		        });
		chooseTileTypeAlternative.setEnabled(false);
		chooseTileTypeAlternative.setVisible(false);

		chooseTileType = new JComboBox<String>(comboboxEntriesLargeTiles);
		chooseTileType.addActionListener(
		        new ActionListener()
		        {
		            public void actionPerformed(final ActionEvent e)		    {
		                   switch (chooseTileType.getSelectedIndex()) {
			                   case 0:
			                	   if (type.isLarge() == true ) {
			                		   type = TileType.largeStandard;
			                		   setTileSelection(possibleTiles.getAllLargeStandardTiles());
			                	   }
			                	   else if (type.isMedium() == true ) {
			                		   type = TileType.mediumStandard;
			                		   setTileSelection(possibleTiles.getAllMediumStandardTiles());
			                	   }
			                	   else if (type.isSmall() == true ) {
			                		   type = TileType.smallStandard;
			                		   setTileSelection(possibleTiles.getAllSmallStandardTiles());
			                	   }
			                	   break;
			                   case 1:  
			                	   if (type.isMedium() == true ) {
			                		   type = TileType.mediumAdapter;
			                		   setAlternativeComboboxEntries(comboboxEntriesAlternativesAdapter);
			                		   setTileSelection(possibleTiles.getAllAdapterTiles());
			                	   }
			                	   else if (type.isSmall() == true ) {
			                		   type = TileType.smallAdapter;
			                		   setAlternativeComboboxEntries(comboboxEntriesAlternativesAdapter);
			                		   setTileSelection(possibleTiles.getAllAdapterTiles());
			                	   }
			                	   break;
			                   case 2: 	
			                	   type = TileType.smallSpecial;
		                		   setAlternativeComboboxEntries(comboboxEntriesAlternativesSmallSpecial);
		                		   setTileSelection(possibleTiles.getAllSmallSpecialTiles());
			                	   break;
			                   case 3: 	
			                	   type = TileType.smallSpecialAdapter;
		                		   setAlternativeComboboxEntries(comboboxEntriesAlternativesSmallSpecialAdapter);
		                		   setTileSelection(possibleTiles.getAllSmallSpecialAdapterTiles());
			                	   break;
		                	  default: break;
		                   }	  
		            }
		        });
		
		panelRechtsOben.add(chooseTileType);
		panelRechtsOben.add(chooseTileTypeAlternative);
		
			
		tileSelection = new ChooseTile[72];
		
		panelTileSelectionLines = new JPanel[8];
		for (i = 0; i < 8; i++) {
			panelTileSelectionLines[i] = new JPanel();
			panelTileSelectionLines[i].setLayout(new GridLayout(1,9));
			
			for (j = 0; j < 9; j++) {
				tileSelection[i*9 + j] = new ChooseTile(i*9 + j);
				tileSelection[i*9 + j].setTileNumber(i*9 + j + 1);
				tileSelection[i*9 + j].setText("" + tileSelection[i*9 + j].getTileNumber());
				panelTileSelectionLines[i].add(tileSelection[i*9 + j]);
			}
			
			panelRechtsOben.add(panelTileSelectionLines[i]);
		}
		
		
		
		panelRechts.add(panelRechtsOben);

		
		panelRechtsMitte = new JPanel();
		panelRechtsMitte.setLayout(new GridLayout(3,1));
		
		infoFieldOben = new JTextField("Infofeld zur Kachelauswahl");
		panelRechtsMitte.add(infoFieldOben);
		infoFieldMitte = new JTextField("Infofeld zum Rasterfeld");
		panelRechtsMitte.add(infoFieldMitte);
		infoFieldUnten = new JTextField("Infofeld zu Aktionen");
		panelRechtsMitte.add(infoFieldUnten);
		
		panelRechts.add(panelRechtsMitte);
		
		panelRechtsUnten = new JPanel();
		panelRechtsUnten.setLayout(new GridLayout(7,2));
		
		buttonGenerateFromText = new JButton("Generieren aus Text");
		buttonGenerateFromText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { generateFromText(); }	
		} );

		
		buttonSaveComplete = new JButton("Speichern Gesamt");
		buttonSaveComplete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { saveTotal(); }	
		} );
		buttonSaveRaster = new JButton("Speichern Raster");
		buttonSaveRaster.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { saveRaster(); }	
		} );

		buttonLoadComplete = new JButton("Laden Gesamt");
		buttonLoadComplete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { loadTotal(); }	
		} );
		buttonLoadRaster = new JButton("Laden Raster");
		buttonLoadRaster.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { loadRaster(); }	
		} );
		
		buttonZoomIn = new JButton("Zoom In");
		buttonZoomIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { zoomIn(); }	
		} );
		buttonZoomOut = new JButton("Zoom Out");
		buttonZoomOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { zoomOut(); }	
		} );
		buttonChooseTile = new JButton("Auswahl");
		buttonChooseTile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { choose(); }	
		} );
		buttonReduceToChoosableTiles = new JButton("Reduzieren");
		buttonReduceToChoosableTiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { reduce(); }	
		} );
		buttonInfo = new JButton("Info");
		buttonInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { info(); }	
		} );
		buttonClear = new JButton("Loeschen");
		buttonClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { clear(); }
		});
		buttonGenerate = new JButton("Generieren");
		buttonGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { generate(); }
		});
		buttonClearAll = new JButton("Reset");
		buttonClearAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { clearRaster(); }
		});
		buttonShowMap = new JButton("Ansehen");
		buttonShowMap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { showMap(); }
		});

		panelRechtsUnten.add(buttonReduceToChoosableTiles);
		panelRechtsUnten.add(buttonChooseTile);
		panelRechtsUnten.add(buttonGenerate);
		panelRechtsUnten.add(buttonInfo);
		panelRechtsUnten.add(buttonClearAll);
		panelRechtsUnten.add(buttonClear);
		panelRechtsUnten.add(buttonZoomOut);
		panelRechtsUnten.add(buttonZoomIn);
		panelRechtsUnten.add(buttonSaveRaster);
		panelRechtsUnten.add(buttonLoadRaster);
		panelRechtsUnten.add(buttonSaveComplete);
		panelRechtsUnten.add(buttonLoadComplete);
		panelRechtsUnten.add(buttonShowMap);
		panelRechtsUnten.add(buttonGenerateFromText);

		panelRechts.add(panelRechtsUnten);

		frame = new JFrame();
		frame.setTitle("Map Creation Tool");
		frame.setBounds(100, 100, 1100, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.getContentPane().setLayout(new GridLayout(1,2));
		
		frame.getContentPane().add(panelRaster);
		frame.getContentPane().add(panelRechts);
		
		buttonBorder = buttonReduceToChoosableTiles.getBorder();
		
		
		visualizedMap = new JFrame();
		visualizedMap.setTitle("Visualisierung");
		visualizedMap.setBounds(100, 100, 750, 750);
		visualizedMap.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		visualizedMap.getContentPane().setLayout(new GridLayout(1,1));
		
		panelVisualizedMap = new ShowMapPanel(729,729);
		visualizedMap.getContentPane().add(panelVisualizedMap);
	}
	
	private void setTileSelection(Set<Integer> tiles) {
	
		int i;
		Integer tileNumbers[];
		int tileNumber;
		int tileNumberWithoutOffset;
		
		
		int position;
		
		tileNumbers = new Integer[tiles.size()];
		tiles.toArray(tileNumbers);
		
		selectedTile = -1;
		
		for (i = 0; i < 72; i++) {
			tileSelection[i].unselect();
			tileSelection[i].deactivate();
		}
		
		if ( (type == TileType.mediumAdapter) || (type == TileType.smallAdapter) || (type == TileType.smallSpecialAdapter) || (type == TileType.smallSpecial) ) {
			chooseTileTypeAlternative.setEnabled(true);
			chooseTileTypeAlternative.setVisible(true);
		}
		else {
			chooseTileTypeAlternative.setEnabled(false);
			chooseTileTypeAlternative.setVisible(false);
		}
		
		for (i = 0; i < tileNumbers.length; i++) {
			tileNumber = tileNumbers[i];
			switch (type) {
			case smallStandard:
				position = pattern.getPatternPositionSmallStandard(tileNumber); break;
			case smallAdapter:
				tileNumberWithoutOffset = tileNumber;
				tileNumber = tileNumber + tileTypeAlternative * 100;
				position = pattern.getPatternPositionAdapter(tileNumberWithoutOffset); break;
			case smallSpecial:
				tileNumberWithoutOffset = tileNumber;
				tileNumber = tileNumber + tileTypeAlternative * 100;
				position = pattern.getPatternPositionSmallSpecial(tileNumberWithoutOffset); 
				break;
			case smallSpecialAdapter:
				tileNumberWithoutOffset = tileNumber;
				tileNumber = tileNumber + tileTypeAlternative * 100;
				position = pattern.getPatternPositionSmallSpecialAdapter(tileNumberWithoutOffset);
				break;
			case mediumStandard:
				position = pattern.getPatternPositionMediumStandard(tileNumber); break;
			case mediumAdapter:
				tileNumberWithoutOffset = tileNumber;
				tileNumber = tileNumber + tileTypeAlternative * 100;
				position = pattern.getPatternPositionAdapter(tileNumberWithoutOffset); break;
			case largeStandard:
				position = pattern.getPatternPositionLargeStandard(tileNumber); break;
			default: position = 0;
			}
			if ( position > 0 ) {
				tileSelection[position - 1].activate(tileNumber, "" + tileNumber);
			}
			
		}
		
	}
	
	private void setTypeComboboxEntries(String [] entries) {
		chooseTileType.removeAllItems();
		for (int i = 0; i < entries.length ; i++) {
				chooseTileType.addItem(entries[i]);
		}
		
	}
	
	private void setAlternativeComboboxEntries(String [] entries) {
		chooseTileTypeAlternative.removeAllItems();
		for (int i = 0; i < entries.length ; i++) {
				chooseTileTypeAlternative.addItem(entries[i]);
		}
		
	}
	
	private int parseString(String input, int indexStart, TileGrid parent) {

		int indexString;
		int index = 0;
		
		indexString = indexStart;
		
		String tile = "";
		
		TileGrid thisTileGrid;
		thisTileGrid = new TileGrid(parent);

		Tile child = null;
		
		
		while(indexString < input.length()) {
			char c = input.charAt(indexString);
			
			if(c == ')') {
					thisTileGrid.addTile(child, index);
					loadedTileGridFromFile = thisTileGrid;
					return indexString;
			}

				
			if(c == '(') {
				loadedTileGridFromFile = null;
				indexString =  parseString(input, indexString + 1, thisTileGrid);
				child = loadedTileGridFromFile;
			}
			
			
			if(c == ',') {
				if (child == null) {
					if (tile.equals("TODO"))
						child = new Tile(TileType.todo);					
					else 
						child = new Tile(tile);
				}
				thisTileGrid.addTile(child, index); 
				
				child = null;
				index++;
				tile = "";
			}
			
			if ( (c != ',') && (c != '(') && (c != ')') && (c != ' '))	tile = tile + c;
			
			indexString++;    
			
		}
		
		return indexString;
	}		
	
	void generate() {
		
		final int rasterSize = 81;
		boolean trackBack = false;
		int countBacktracks = 0;
		int i;

		int rasterFieldSequenceAlternative;
		Integer[] rasterFieldSequence = new Integer[rasterSize]; 
		int randomIndex;
		
		Integer[] rasterFieldSequenceAlternative2 = 
			{0,1,2,3,4,5,6,7,8,17,16,15,14,13,12,11,10,9,18,19,20,21,22,23,24,25,26,35,34,33,32,31,30,29,28,27,
			36,37,38,39,40,41,42,43,44,53,52,51,50,49,48,47,46,45,54,55,56,57,58,59,60,61,62,71,70,69,68,67,66,65,64,63,72,73,74,75,76,77,78,79,80};

		Integer[] rasterFieldSequenceAlternative3 = 
			{80,79,78,77,76,75,74,73,72,63,64,65,66,67,68,69,70,71,62,61,60,59,58,57,56,55,54,45,46,47,48,49,50,51,52,53,
			44,43,42,41,40,39,38,37,36,27,28,29,30,31,32,33,34,35,26,25,24,23,22,21,20,19,18,9,10,11,12,13,14,15,16,17,8,7,6,5,4,3,2,1,0};

		Integer[] rasterFieldSequenceAlternative4 =
			{40,39,48,49,50,41,32,31,30,29,38,47,56,57,58,59,60,51,42,33,24,23,22,21,20,19,28,37,46,55,64,65,66,67,68,69,
			70,61,52,43,34,25,16,15,14,13,12,11,10,9,18,27,36,45,54,63,72,73,74,75,76,77,78,79,80,71,62,53,44,35,26,17,8,7,6,5,4,3,2,1,0};

		Integer[] rasterFieldSequenceAlternative5 = 
			{0,1,2,3,4,5,6,7,8,17,26,35,44,53,62,71,80,79,78,77,76,75,74,73,72,63,54,45,36,27,18,9,10,11,12,13,14,15,16,25,34,43,52,61,70,
			69,68,67,66,65,64,55,46,37,28,19,20,21,22,23,24,33,42,51,60,59,58,57,56,47,38,29,30,31,32,41,50,49,48,39,40};
		
		int tileNumber;
		
		Set<Integer> reducedAsSet;
		Integer[] reducedAsArray;
	
		Set<Integer> remainingPossibleTilesForRasterFieldAtIndex[] = new Set[rasterSize];  	
		boolean rasterFieldAtIndexInitialized[] = new boolean[rasterSize];
		
		Set<Integer> alreadyUsedRasterFields;
		Integer empty[] = {};
		alreadyUsedRasterFields = new HashSet<Integer>(Arrays.asList(empty));
		Set<Integer> possibleNeigbourRasterFieldIndexes;
		Integer possibleNeigbourRasterFieldIndexesAsArray[];
		
		rasterFieldSequenceAlternative = (int)(Math.random() * 7);
		int maxBacktrackings = 1000 + (int)Math.pow(5, rasterFieldSequenceAlternative);
		
		randomIndex = (int)(Math.random() * rasterSize); 
		
		for (i = 0; i < rasterSize; i++) {
			
			if (rasterFieldSequenceAlternative == 6) {
	  			possibleNeigbourRasterFieldIndexes = getNeighbourRFIs(randomIndex);
	  			possibleNeigbourRasterFieldIndexes.removeAll(alreadyUsedRasterFields);
	  			
	  			if (possibleNeigbourRasterFieldIndexes.isEmpty()) {
		  			do {
						randomIndex = (int)(Math.random() * rasterSize); 
					}
					while (alreadyUsedRasterFields.contains(randomIndex));
	  			}
	  			else {
	  				randomIndex /* temporary used */ = (int)(Math.random() * possibleNeigbourRasterFieldIndexes.size());
	  				possibleNeigbourRasterFieldIndexesAsArray = 
	  						possibleNeigbourRasterFieldIndexes.toArray(new Integer[possibleNeigbourRasterFieldIndexes.size()]);
	  				randomIndex = possibleNeigbourRasterFieldIndexesAsArray[randomIndex];
	  			}
	  				
	  			alreadyUsedRasterFields.add(randomIndex);
				rasterFieldSequence[i] = randomIndex; 
				
			}
			else {
				switch (rasterFieldSequenceAlternative) {
					case 0: rasterFieldSequence[i] = i; break;
					case 1: rasterFieldSequence[i] = 80 - i; break;
					case 2: rasterFieldSequence[i] = rasterFieldSequenceAlternative2[i];
					case 3: rasterFieldSequence[i] = rasterFieldSequenceAlternative3[i];
					case 4: rasterFieldSequence[i] = rasterFieldSequenceAlternative4[i];
					case 5: rasterFieldSequence[i] = rasterFieldSequenceAlternative5[i];
					default:  
				}
			}
			
			rasterFieldAtIndexInitialized[i] = false;
		}	

		int reducedSetIndex;
		
		alreadyUsedRasterFields.clear();
		
		for (i = 0; i < rasterSize; i++) {
		
			randomIndex = rasterFieldSequence[i];
		    
			
			if ( rasterFieldAtIndexInitialized[i] == false ) {
				reducedAsSet = getReducedSet(randomIndex);
				reducedAsArray = reducedAsSet.toArray(new Integer[reducedAsSet.size()]);
				remainingPossibleTilesForRasterFieldAtIndex[i] = new HashSet<Integer>(Arrays.asList(reducedAsArray));
				rasterFieldAtIndexInitialized[i] = true;
			}
			else {
				reducedAsArray = remainingPossibleTilesForRasterFieldAtIndex[i].toArray(new Integer[remainingPossibleTilesForRasterFieldAtIndex[i].size()]);
				reducedAsSet = remainingPossibleTilesForRasterFieldAtIndex[i];
			}
			
			
				
			if ( reducedAsSet.size() == 0) {
					trackBack = true;
			}
			else {
				
				trackBack = false;
					
				
				reducedSetIndex = (int)(Math.random() * reducedAsSet.size());
				tileNumber = reducedAsArray[reducedSetIndex];
				remainingPossibleTilesForRasterFieldAtIndex[i].remove(tileNumber);
				
				switch (type) {
				case smallAdapter:
					tileNumber = tileNumber + tileTypeAlternative * 100;
					break;
				case smallSpecial:
					tileNumber = tileNumber + tileTypeAlternative * 100;
					break;
				case smallSpecialAdapter:
					tileNumber = tileNumber + tileTypeAlternative * 100;
					break;
				case mediumAdapter:
					tileNumber = tileNumber + tileTypeAlternative * 100;
					break;
				default: ;
				}
	
				raster[randomIndex].setTile(type,tileTypeAlternative,tileNumber);
				raster[randomIndex].setText();
				
				if (randomIndex == 0) {
					// TODO input height
					raster[0].height = 1;
				}
			
				
			}
			
			if (trackBack == true) {
				if (i > 0) {
					
					// track back
					
					raster[randomIndex].clear();
					rasterFieldAtIndexInitialized[i] = false;
					
/*					String tileNumbers = "";
					for (int j=0; j < 81; j++) {
						tileNumbers = tileNumbers + " " + raster[rasterFieldSequence[j]].getText();
					}
			    	System.out.println("backtracking Nr. "+ countBacktracks + " depth i: " + i + " - Belegung: "+ tileNumbers); */
					i = i - 2;
					
					countBacktracks++;
					
					if (countBacktracks == maxBacktrackings) {
						// break back tracking;
						return; // without success
					}

				}
				else
				{	
					return; // without success
				}	
			}
			else {
				
			}
			
		}
		
	}
	
	private void clearRaster() {
		for (int i = 0; i < 81;i++) {
			raster[i].clear();
		}
	}
	
	private Set<Integer> getNeighbourRFIs(int index) {
		// getting neighbour raster field indexes
		
		Set<Integer> result;
		Integer emptyset[] = {};						
		result = new HashSet<Integer>(Arrays.asList(emptyset));
		
		if ((index == 8 ) || (index == 17) || (index == 26 ) || (index == 35 ) || (index == 44 ) || (index == 53 ) || 
				(index == 62 ) || (index == 71 ) || (index == 80 )) /*no right neighbour */;
		else	result.add(index + 1);
		
		if ((index == 0) ||(index == 9 ) || (index == 18) || (index == 27 ) || (index == 36 ) || (index == 45 ) || (index == 54 ) || 
				(index == 63 ) || (index == 72 )) /*no left neighbour */;
		else	result.add( index - 1);	
		
		if (index > 8)	result.add(index - 9);
		
		if (index < 72)	result.add(index + 9);
		
		return result;
		
	}
	
	void showMap() {
		MapDotsColors colors = new MapDotsColors();
		saveTileTermIntern();
		this.tileTerm.setColorsForHeight(colors);
		panelVisualizedMap.setPixels(colors.colorsForXY);
		visualizedMap.setVisible(true);
	}
	
}


