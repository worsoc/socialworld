/*
* Social World
* Copyright (C) 2021  Philipp Haack
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
package org.socialworld.attributes.properties;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.socialworld.knowledge.KnowledgeFact_Criterion;
import org.socialworld.tools.SimulationMetaInformation;
import org.socialworld.tools.StringTupel;
import org.socialworld.visualize.SimVisual;

public enum Colour {

	nothing(0,-1),
	black(1,0000000),  
	dimgray(2,105105105),
	gray(3,128128128),
	darkgray(4,169169169),
	silver(5,192192192),
	aliceblue(6,240248255),
	lavender(7,230230250),
	powderblue(8,176224230),
	lightblue(9,173216230),
	lightskyblue(10,135206250),
	skyblue(11,135206235),
	deepskyblue(12,191255),
	lightsteelblue(13,176196222),
	dodgerblue(14,30144255),
	cornflowerblue(15,100149237),
	steelblow(16,70130180),
	cadetblue(17,95158160),
	mediumslateblue(18,123104238),
	slateblue(19,106090205),
	darkeslateblue(20,72061139),
	royelblue(21,65105225),
	blue(22,0000255),
	mediumblue(23,0000205),
	darkeblue(24,139),
	navy(25,128),
	midnightblue(26,25025112),
	blueviolet(27,138043226),
	indigo(28,75000130),
	cornsilk(29,255248220),
	blanchedalmond(30,255235205),
	bisque(31,255228196),
	navajowhite(32,255222173),
	wheat(33,245222179),
	sandybrown(34,244164096),
	goldenrod(35,218165032),
	saddlsebrown(36,139069019),
	sienna(37,160082045),
	burlywood(38,222184135),
	tan(39,210180140),
	rosybrown(40,188143143),
	peru(41,205133063),
	chocolate(42,210105030),
	brown(43,165042042),
	maroon(44,128000000),
	lightcyan(45,224255255),
	cyan(46,0255255),
	aquamarine(47,127255212),
	mediumaquamarine(48,102205170),
	paletturquoise(49,175238238),
	turquoise(50,64224208),
	mediumturquoise(51,72209204),
	darkturquoise(52,206209),
	lightseagreen(53,32178170),
	darkcyan(54,139139),
	teal(55,128128),
	lightgoldenrodyellow(56,250250210),
	palegoldenrod(57,238232170),
	khaki(58,240230140),
	gold(59,255215000),
	orange(60,255165000),
	darkorange(61,255140000),
	saddlebrown(62,139069019),
	golden_yellow(63,255223000),
	metallic_gold(64,212175055),
	old_gold(65,207181059),
	vegas_gold(66,197179088),
	pale_gold(67,230190138),
	golden_brown(68,153101021),
	lawngreen(69,124252000),
	chartreuse(70,127255000),
	limegreen(71,50205050),
	lime(72,0255000),
	forestgreen(73,34139034),
	green(74,128000),
	darkgreen(75,0100000),
	greenyellow(76,173255047),
	yellowgreen(77,154205050),
	springgreen(78,0255127),
	mediumspringgreen(79,0250154),
	lightgreen(80,144238144),
	palegreen(81,152251152),
	darkseagreen(81,143188143),
	mediumseagreen(82,60179113),
	seagreen(83,46139087),
	olive(84,128128000),
	darkolivegreen(85,85107047),
	olivedrab(86,107142035),
	gainsboro(87,220220220),
	lightgray(88,211211211),
	lightslategray(89,119136153),
	slategray(90,112128144),
	darkslategray(91,47079079),
	darkred(92,139000000),
	firebrick(93,178034034),
	crimsonrgb(94,220020060),
	coral(95,255127080),
	tomato(96,255099071),
	orangered(97,255069000),
	pink(98,255192203),
	lightpink(99,255182193),
	hotpink(100,255105180),
	deeppink(101,255020147),
	palevioletred(102,219112147),
	mediumvioletred(103,199021133),
	avender(104,230230250),
	thistle(105,216191216),
	plum(106,221160221),
	violet(107,238130238),
	orchid(108,218112214),
	fuchsia(109,255000255),
	magenta(110,255000255),
	mediumorchid(111,186085211),
	mediumpurple(112,147112219),
	darkviolet(113,148000211),
	darkorchid(114,153050204),
	darkmagenta(115,139000139),
	purple(116,128000128),
	lightsalmon(117,255160122),
	salmon(118,250128114),
	darksalmon(119,233150122),
	lightcoral(120,240128128),
	indianred(121,205092092),
	crimson(122,220020060),
	red(123,255000000),
	white(124,255255255),
	snow(125,255250250),
	honeydew(126,240255240),
	mintcream(127,245255250),
	azure(128,240255255),
	ghostwhite(129,248248255),
	whitesmoke(130,245245245),
	seashell(131,255245238),
	beige(132,245245220),
	oldlace(133,253245230),
	floralwhite(134,255250240),
	ivory(135,255255240),
	antiquewhite(136,250235215),
	linen(137,250240230),
	lavenderblush(138,255240245),
	mistyrose(139,255228225),
	lightyellow(140,255255224),
	lemonchiffon(141,255250205),
	papayawhip(142,255239213),
	moccasin(143,255228181),
	peachpuff(144,255218185),
	darkkhaki(145,189183107),
	yellow(146,255255000),
	light_yellow1(147,255255204),
	light_yellow2(148,255255153),
	light_yellow3(149,255255102),
	light_yellow4(150,255255051),
	dark_yellow1(151,204204000),
	dark_yellow2(152,153153000),
	dark_yellow3(153,102102000),
	dark_yellow4(154,051051000);
	
	public static int getMaxIndex() {
		return 154;
	}

	public Color getColour(Colour colour) {
		switch(colour) {
		case black: return SimVisual.COLOR_BLACK;
		case dimgray: return SimVisual.COLOR_DIMGRAY;
		case gray: return SimVisual.COLOR_GRAY;
		case darkgray: return SimVisual.COLOR_DARKGRAY;
		case silver: return SimVisual.COLOR_SILVER;
		case aliceblue: return SimVisual.COLOR_ALICEBLUE;
		case lavender: return SimVisual.COLOR_LAVENDER;
		case powderblue: return SimVisual.COLOR_POWDERBLUE;
		case lightblue: return SimVisual.COLOR_LIGHTBLUE;
		case lightskyblue: return SimVisual.COLOR_LIGHTSKYBLUE;
		case skyblue: return SimVisual.COLOR_SKYBLUE;
		case deepskyblue: return SimVisual.COLOR_DEEPSKYBLUE;
		case lightsteelblue: return SimVisual.COLOR_LIGHTSTEELBLUE;
		case dodgerblue: return SimVisual.COLOR_DODGERBLUE;
		case cornflowerblue: return SimVisual.COLOR_CORNFLOWERBLUE;
		case steelblow: return SimVisual.COLOR_STEELBLOW;
		case cadetblue: return SimVisual.COLOR_CADETBLUE;
		case mediumslateblue: return SimVisual.COLOR_MEDIUMSLATEBLUE;
		case slateblue: return SimVisual.COLOR_SLATEBLUE;
		case darkeslateblue: return SimVisual.COLOR_DARKESLATEBLUE;
		case royelblue: return SimVisual.COLOR_ROYELBLUE;
		case blue: return SimVisual.COLOR_BLUE;
		case mediumblue: return SimVisual.COLOR_MEDIUMBLUE;
		case darkeblue: return SimVisual.COLOR_DARKEBLUE;
		case navy: return SimVisual.COLOR_NAVY;
		case midnightblue: return SimVisual.COLOR_MIDNIGHTBLUE;
		case blueviolet: return SimVisual.COLOR_BLUEVIOLET;
		case indigo: return SimVisual.COLOR_INDIGO;
		case cornsilk: return SimVisual.COLOR_CORNSILK;
		case blanchedalmond: return SimVisual.COLOR_BLANCHEDALMOND;
		case bisque: return SimVisual.COLOR_BISQUE;
		case navajowhite: return SimVisual.COLOR_NAVAJOWHITE;
		case wheat: return SimVisual.COLOR_WHEAT;
		case sandybrown: return SimVisual.COLOR_SANDYBROWN;
		case goldenrod: return SimVisual.COLOR_GOLDENROD;
		case saddlsebrown: return SimVisual.COLOR_SADDLSEBROWN;
		case sienna: return SimVisual.COLOR_SIENNA;
		case burlywood: return SimVisual.COLOR_BURLYWOOD;
		case tan: return SimVisual.COLOR_TAN;
		case rosybrown: return SimVisual.COLOR_ROSYBROWN;
		case peru: return SimVisual.COLOR_PERU;
		case chocolate: return SimVisual.COLOR_CHOCOLATE;
		case brown: return SimVisual.COLOR_BROWN;
		case maroon: return SimVisual.COLOR_MAROON;
		case lightcyan: return SimVisual.COLOR_LIGHTCYAN;
		case cyan: return SimVisual.COLOR_CYAN;
		case aquamarine: return SimVisual.COLOR_AQUAMARINE;
		case mediumaquamarine: return SimVisual.COLOR_MEDIUMAQUAMARINE;
		case paletturquoise: return SimVisual.COLOR_PALETURQUOISE;
		case turquoise: return SimVisual.COLOR_TURQUOISE;
		case mediumturquoise: return SimVisual.COLOR_MEDIUMTURQUOISE;
		case darkturquoise: return SimVisual.COLOR_DARKTURQUOISE;
		case lightseagreen: return SimVisual.COLOR_LIGHTSEAGREEN;
		case darkcyan: return SimVisual.COLOR_DARKCYAN;
		case teal: return SimVisual.COLOR_TEAL;
		case lightgoldenrodyellow: return SimVisual.COLOR_LIGHTGOLDENRODYELLOW;
		case palegoldenrod: return SimVisual.COLOR_PALEGOLDENROD;
		case khaki: return SimVisual.COLOR_KHAKI;
		case gold: return SimVisual.COLOR_GOLD;
		case orange: return SimVisual.COLOR_ORANGE;
		case darkorange: return SimVisual.COLOR_DARKORANGE;
		case saddlebrown: return SimVisual.COLOR_SADDLEBROWN;
		case golden_yellow: return SimVisual.COLOR_GOLDEN_YELLOW;
		case metallic_gold: return SimVisual.COLOR_METALLIC_GOLD;
		case old_gold: return SimVisual.COLOR_OLD_GOLD;
		case vegas_gold: return SimVisual.COLOR_VEGAS_GOLD;
		case pale_gold: return SimVisual.COLOR_PALE_GOLD;
		case golden_brown: return SimVisual.COLOR_GOLDEN_BROWN;
		case lawngreen: return SimVisual.COLOR_LAWNGREEN;
		case chartreuse: return SimVisual.COLOR_CHARTREUSE;
		case limegreen: return SimVisual.COLOR_LIMEGREEN;
		case lime: return SimVisual.COLOR_LIME;
		case forestgreen: return SimVisual.COLOR_FORESTGREEN;
		case green: return SimVisual.COLOR_GREEN;
		case darkgreen: return SimVisual.COLOR_DARKGREEN;
		case greenyellow: return SimVisual.COLOR_GREENYELLOW;
		case yellowgreen: return SimVisual.COLOR_YELLOWGREEN;
		case springgreen: return SimVisual.COLOR_SPRINGGREEN;
		case mediumspringgreen: return SimVisual.COLOR_MEDIUMSPRINGGREEN;
		case lightgreen: return SimVisual.COLOR_LIGHTGREEN;
		case palegreen: return SimVisual.COLOR_PALEGREEN;
		case darkseagreen: return SimVisual.COLOR_DARKSEAGREEN;
		case mediumseagreen: return SimVisual.COLOR_MEDIUMSEAGREEN;
		case seagreen: return SimVisual.COLOR_SEAGREEN;
		case olive: return SimVisual.COLOR_OLIVE;
		case darkolivegreen: return SimVisual.COLOR_DARKOLIVEGREEN;
		case olivedrab: return SimVisual.COLOR_OLIVEDRAB;
		case gainsboro: return SimVisual.COLOR_GAINSBORO;
		case lightgray: return SimVisual.COLOR_LIGHTGRAY;
		case lightslategray: return SimVisual.COLOR_LIGHTSLATEGRAY;
		case slategray: return SimVisual.COLOR_SLATEGRAY;
		case darkslategray: return SimVisual.COLOR_DARKSLATEGRAY;
		case darkred: return SimVisual.COLOR_DARKRED;
		case firebrick: return SimVisual.COLOR_FIREBRICK;
		case crimsonrgb: return SimVisual.COLOR_CRIMSONRGB;
		case coral: return SimVisual.COLOR_CORAL;
		case tomato: return SimVisual.COLOR_TOMATO;
		case orangered: return SimVisual.COLOR_ORANGERED;
		case pink: return SimVisual.COLOR_PINK;
		case lightpink: return SimVisual.COLOR_LIGHTPINK;
		case hotpink: return SimVisual.COLOR_HOTPINK;
		case deeppink: return SimVisual.COLOR_DEEPPINK;
		case palevioletred: return SimVisual.COLOR_PALEVIOLETRED;
		case mediumvioletred: return SimVisual.COLOR_MEDIUMVIOLETRED;
		case avender: return SimVisual.COLOR_AVENDER;
		case thistle: return SimVisual.COLOR_THISTLE;
		case plum: return SimVisual.COLOR_PLUM;
		case violet: return SimVisual.COLOR_VIOLET;
		case orchid: return SimVisual.COLOR_ORCHID;
		case fuchsia: return SimVisual.COLOR_FUCHSIA;
		case magenta: return SimVisual.COLOR_MAGENTA;
		case mediumorchid: return SimVisual.COLOR_MEDIUMORCHID;
		case mediumpurple: return SimVisual.COLOR_MEDIUMPURPLE;
		case darkviolet: return SimVisual.COLOR_DARKVIOLET;
		case darkorchid: return SimVisual.COLOR_DARKORCHID;
		case darkmagenta: return SimVisual.COLOR_DARKMAGENTA;
		case purple: return SimVisual.COLOR_PURPLE;
		case lightsalmon: return SimVisual.COLOR_LIGHTSALMON;
		case salmon: return SimVisual.COLOR_SALMON;
		case darksalmon: return SimVisual.COLOR_DARKSALMON;
		case lightcoral: return SimVisual.COLOR_LIGHTCORAL;
		case indianred: return SimVisual.COLOR_INDIANRED;
		case crimson: return SimVisual.COLOR_CRIMSON;
		case red: return SimVisual.COLOR_RED;
		case white: return SimVisual.COLOR_WHITE;
		case snow: return SimVisual.COLOR_SNOW;
		case honeydew: return SimVisual.COLOR_HONEYDEW;
		case mintcream: return SimVisual.COLOR_MINTCREAM;
		case azure: return SimVisual.COLOR_AZURE;
		case ghostwhite: return SimVisual.COLOR_GHOSTWHITE;
		case whitesmoke: return SimVisual.COLOR_WHITESMOKE;
		case seashell: return SimVisual.COLOR_SEASHELL;
		case beige: return SimVisual.COLOR_BEIGE;
		case oldlace: return SimVisual.COLOR_OLDLACE;
		case floralwhite: return SimVisual.COLOR_FLORALWHITE;
		case ivory: return SimVisual.COLOR_IVORY;
		case antiquewhite: return SimVisual.COLOR_ANTIQUEWHITE;
		case linen: return SimVisual.COLOR_LINEN;
		case lavenderblush: return SimVisual.COLOR_LAVENDERBLUSH;
		case mistyrose: return SimVisual.COLOR_MISTYROSE;
		case lightyellow: return SimVisual.COLOR_LIGHTYELLOW;
		case lemonchiffon: return SimVisual.COLOR_LEMONCHIFFON;
		case papayawhip: return SimVisual.COLOR_PAPAYAWHIP;
		case moccasin: return SimVisual.COLOR_MOCCASIN;
		case peachpuff: return SimVisual.COLOR_PEACHPUFF;
		case darkkhaki: return SimVisual.COLOR_DARKKHAKI;
		case yellow: return SimVisual.COLOR_YELLOW;
		case light_yellow1: return SimVisual.COLOR_LIGHT_YELLOW1;
		case light_yellow2: return SimVisual.COLOR_LIGHT_YELLOW2;
		case light_yellow3: return SimVisual.COLOR_LIGHT_YELLOW3;			
		case light_yellow4: return SimVisual.COLOR_LIGHT_YELLOW4;			
		case dark_yellow1: return SimVisual.COLOR_DARK_YELLOW1;
		case dark_yellow2: return SimVisual.COLOR_DARK_YELLOW2;
		case dark_yellow3: return SimVisual.COLOR_DARK_YELLOW3;			
		case dark_yellow4: return SimVisual.COLOR_DARK_YELLOW4;	
		
		default:return SimVisual.COLOR_BLACK;
		}
	}
	
	
	
	
	private int index;
	private int rgb;
	
	private Colour(int index, int rgb) {
		this.index = index;
		this.rgb = rgb;
	}
	

	/**
	 * The method returns the index of the material.
	 * 
	 * @return material's index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * The method returns the material name which belongs to the parameter
	 * material index.
	 * 
	 * @param index
	 *            material index
	 * @return material name
	 */
	public static Colour getName(int index) {
		for (Colour element : Colour.values())
			if (element.index == index)
				return element;
		return null;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////meta information    ////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////
	
	public static List<StringTupel> getPropMethodsMetaInfos() {
		List<StringTupel> listOfPropMethodMetaInfo = new ArrayList<StringTupel>();
		listOfPropMethodMetaInfo.add(new StringTupel(SimulationMetaInformation.CLASSNAME_ENUM_INDEX, SimulationMetaInformation.METHODNAME_ENUM_GETINDEX));
		return listOfPropMethodMetaInfo;
	}

	public static List<KnowledgeFact_Criterion> getResultingKFCs() {
		List<KnowledgeFact_Criterion> listOfResultingKFCs = new ArrayList<KnowledgeFact_Criterion>();
		listOfResultingKFCs.add(KnowledgeFact_Criterion.colour);
		return listOfResultingKFCs;
	}
	
	
}



/*
black(000000000),  
dimgray(105105105),
gray(128128128),
darkgray(169169169),
silver(192192192),
aliceblue(240248255),
lavender(230230250),
powderblue(176224230),
lightblue(173216230),
lightskyblue(135206250),
skyblue(135206235),
deepskyblue(000191255),
lightsteelblue(176196222),
dodgerblue(030144255),
cornflowerblue(100149237),
steelblow(070130180),
cadetblue(095158160),
mediumslateblue(123104238),
slateblue(106090205),
darkeslateblue(072061139),
royelblue(065105225),
blue(000000255),
mediumblue(000000205),
darkeblue(000000139),
navy(000000128),
midnightblue(025025112),
blueviolet(138043226),
indigo(075000130),
cornsilk(255248220),
blanchedalmond(255235205),
bisque(255228196),
navajowhite(255222173),
wheat(245222179),
sandybrown(244164096),
goldenrod(218165032),
saddlsebrown(139069019),
sienna(160082045),
burlywood(222184135),
tan(210180140),
rosybrown(188143143),
peru(205133063),
chocolate(210105030),
brown(165042042),
maroon(128000000),
lightcyan(224255255),
cyan(000255255),
aquamarine(127255212),
mediumaquamarine(102205170),
paletturquoise(175238238),
turquoise(064224208),
mediumturquoise(072209204),
darkturquoise(000206209),
lightseagreen(032178170),
darkcyan(000139139),
teal(000128128),
lightgoldenrodyellow(250250210),
palegoldenrod(238232170),
khaki(240230140),
gold(255215000),
orange(255165000),
darkorange(255140000),
saddlebrown(139069019),
golden_yellow(255223000),
metallic_gold(212175055),
old_gold(207181059),
vegas_gold(197179088),
pale_gold(230190138),
golden_brown(153101021),
lawngreen(124252000),
chartreuse(127255000),
limegreen(050205050),
lime(000255000),
forestgreen(034139034),
green(000128000),
darkgreen(000100000),
greenyellow(173255047),
yellowgreen(154205050),
springgreen(000255127),
mediumspringgreen(000250154),
lightgreen(144238144),
palegreen(152251152),
darkseagreen(143188143),
mediumseagreen(060179113),
seagreen(046139087),
olive(128128000),
darkolivegreen(085107047),
olivedrab(107142035),
gainsboro(220220220),
lightgray(211211211),
lightslategray(119136153),
slategray(112128144),
darkslategray(047079079),
darkred(139000000),
firebrick(178034034),
crimsonrgb(220020060),
coral(255127080),
tomato(255099071),
orangered(255069000),
pink(255192203),
lightpink(255182193),
hotpink(255105180),
deeppink(255020147),
palevioletred(219112147),
mediumvioletred(199021133),
avender(230230250),
thistle(216191216),
plum(221160221),
violet(238130238),
orchid(218112214),
fuchsia(255000255),
magenta(255000255),
mediumorchid(186085211),
mediumpurple(147112219),
darkviolet(148000211),
darkorchid(153050204),
darkmagenta(139000139),
purple(128000128),
lightsalmon(255160122),
salmon(250128114),
darksalmon(233150122),
lightcoral(240128128),
indianred(205092092),
crimson(220020060),
red(255000000),
white(255255255),
snow(255250250),
honeydew(240255240),
mintcream(245255250),
azure(240255255),
ghostwhite(248248255),
whitesmoke(245245245),
seashell(255245238),
beige(245245220),
oldlace(253245230),
floralwhite(255250240),
ivory(255255240),
antiquewhite(250235215),
linen(250240230),
lavenderblush(255240245),
mistyrose(255228225),
lightyellow(255255224),
lemonchiffon(255250205),
papayawhip(255239213),
moccasin(255228181),
peachpuff(255218185),
darkkhaki(189183107),
yellow(255255000),
light_yellow1(255255204),
light_yellow2(255255153),
light_yellow3(255255102);
 */ 
