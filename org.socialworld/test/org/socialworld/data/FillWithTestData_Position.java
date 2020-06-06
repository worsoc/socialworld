/*
* Social World
* Copyright (C) 2016  Mathias Sikos
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
package org.socialworld.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.URL;

import org.socialworld.datasource.tablesPool.TableGaussPosition;
import org.socialworld.datasource.tablesPool.TablePoolPosition;

public class FillWithTestData_Position {

	public void fill() {
		
		fillTableFromFile();
		
	}
	
	private void fillTableFromFile() {
		
		try
		{
			
			TableGaussPosition tableGaussPosition;
			TablePoolPosition tablePoolPosition;
			
			
			String line;
			String values[];
			int array[];
			
			int index = 0;
			int vorzeichen = 1;
			int count = 0;
			
			int i;

			int pos_id = 0;
			
			array = new int[3];

			tablePoolPosition = new TablePoolPosition();
			tableGaussPosition = new TableGaussPosition();
			
			InputStream input = new URL("http://sourceforge.net/projects/socialworld/files/hmn_positions.swp").openStream();
			LineNumberReader lnr
			   = new LineNumberReader(new InputStreamReader(input));

			
			while ((line = lnr.readLine()) != null)
			{
				
				if (line.startsWith("("))
				{
					line = line.substring(1);
					line = line.replace(")", "");
					line = line.replace(" ", "");
					line = line.trim();
					
					values = line.split(",");
					for (i = 0; i < 3; i++){
						array[i] = Integer.parseInt(values[i]);
					}
					
					pos_id++;
					
					tablePoolPosition.insert(pos_id, array[0], array[1], array[2]);

					index = index + vorzeichen * count;
					count++;
					vorzeichen = vorzeichen * -1;
					
					tableGaussPosition.insert(index, pos_id);
					
					
				}
			}
			lnr.close();
		}
		catch (IOException e)
		{
			System.out.println("Fehler beim Lesen der Datei");
			e.printStackTrace();
		}
	}
	
}
