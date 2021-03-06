package org.socialworld.tools.mct;
import java.awt.Color;

class MapDotsColors {

	public static final Color COLOR_CORNSILK = new Color(255,248,220);
	public static final Color COLOR_BLANCHEDALMOND = new Color(255,235,205);
	public static final Color COLOR_BISQUE = new Color(255,228,196);
	public static final Color COLOR_NAVAJOWHITE = new Color(255,222,173);
	public static final Color COLOR_WHEAT = new Color(245,222,179);
	public static final Color COLOR_SANDYBROWN = new Color(244,164,96);
	public static final Color COLOR_GOLDENROD = new Color(218,165,32);
	public static final Color COLOR_SADDLSEBROWN = new Color(139,69,19);
	public static final Color COLOR_SIENNA = new Color(160,82,45);
	public static final Color COLOR_BURLYWOOD =  new Color(222,184,135);
	public static final Color COLOR_TAN =  new Color(210,180,140);
	public static final Color COLOR_ROSYBROWN =  new Color(188,143,143);
	public static final Color COLOR_PERU =  new Color(205,133,63);
	public static final Color COLOR_CHOCOLATE =  new Color(210,105,30);
	public static final Color COLOR_BROWN =  new Color(165,42,42);
	public static final Color COLOR_MAROON = new Color(128,0,0);

	public static final Color COLOR_ALICEBLUE = new Color(240,248,255);
	public static final Color COLOR_LAVENDER = new Color(230,230,250);
	public static final Color COLOR_POWDERBLUE = new Color(176,224,230);
	public static final Color COLOR_LIGHTBLUE = new Color(173,216,230);
	public static final Color COLOR_LIGHTSKYBLUE = new Color(135,206,250);
	public static final Color COLOR_SKYBLUE = new Color(135,206,235);	
	public static final Color COLOR_DEEPSKYBLUE = new Color(0,191,255);
	public static final Color COLOR_LIGHTSTEELBLUE = new Color(176,196,222);
	public static final Color COLOR_DODGERBLUE = new Color(30,144,255);
	public static final Color COLOR_CORNFLOWERBLUE = new Color(100,149,237);
	public static final Color COLOR_STEELBLOW = new Color(70,130,180);
	public static final Color COLOR_CADETBLUE = new Color(95,158,160);
	public static final Color COLOR_MEDIUMSLATEBLUE = new Color(123,104,238);
	public static final Color COLOR_SLATEBLUE = new Color(106,90,205);
	public static final Color COLOR_DARKESLATEBLUE = new Color(72,61,139);
	public static final Color COLOR_ROYELBLUE = new Color(65,105,225);
	public static final Color COLOR_BLUE = new Color(0,0,255);
	public static final Color COLOR_MEDIUMBLUE = new Color(0,0,205);
	public static final Color COLOR_DARKEBLUE = new Color(0,0,139);
	public static final Color COLOR_NAVY = new Color(0,0,128);
	public static final Color COLOR_MIDNIGHTBLUE = new Color(25,25,112);
	public static final Color COLOR_BLUEVIOLET = new Color(138,43,226);
	public static final Color COLOR_INDIGO = new Color(75,0,130);

	public static final Color COLOR_LIGHTSEAGREEN = new Color(32,178,170);
	

	public static final Color COLOR_LAWNGREEN = new Color(124,252,0);
	public static final Color COLOR_CHARTREUSE = new Color(127,255,0);
	public static final Color COLOR_LIMEGREEN = new Color(50,205,50);
	public static final Color COLOR_LIME = new Color(0,255,0);
	public static final Color COLOR_FORESTGREEN = new Color(34,139,34);
	public static final Color COLOR_GREEN = new Color(0,128,0);
	public static final Color COLOR_DARKGREEN = new Color(0,100,0);
	public static final Color COLOR_GREENYELLOW = new Color(173,255,47);
	public static final Color COLOR_YELLOWGREEN = new Color(154,205,50);
	public static final Color COLOR_SPRINGGREEN = new Color(0,255,127);
	public static final Color COLOR_MEDIUMSPRINGGREEN = new Color(0,250,154);
	public static final Color COLOR_LIGHTGREEN = new Color(144,238,144);
	public static final Color COLOR_PALEGREEN = new Color(152,251,152);
	public static final Color COLOR_DARKSEAGREEN = new Color(143,188,143);
	public static final Color COLOR_MEDIUMSEAGREEN = new Color(60,179,113);
	public static final Color COLOR_SEAGREEN = new Color(46,139,87);
	public static final Color COLOR_OLIVE = new Color(128,128,0);
	public static final Color COLOR_DARKOLIVEGREEN = new Color(85,107,47);
	public static final Color COLOR_OLIVEDRAB = new Color(107,142,35);
	
	public static final Color COLOR_LIGHTGOLDENRODYELLOW = new Color(250,250,210);
	public static final Color COLOR_PALEGOLDENROD = new Color(238,232,170);
	public static final Color COLOR_KHAKI = new Color(240,230,140);
	public static final Color COLOR_GOLD = new Color(255,215,0);
	public static final Color COLOR_ORANGE = new Color(255,165,0);
	public static final Color COLOR_DARKORANGE = new Color(255,140,0);
	public static final Color COLOR_SADDLEBROWN = new Color(139,69,19);
	public static final Color COLOR_GOLDEN_YELLOW = new Color(255,223,0);
	public static final Color COLOR_METALLIC_GOLD = new Color(212,175,55);
	public static final Color COLOR_OLD_GOLD = new Color(207,181,59);
	public static final Color COLOR_VEGAS_GOLD = new Color(197,179,88);
	public static final Color COLOR_PALE_GOLD = new Color(230,190,138);
	public static final Color COLOR_GOLDEN_BROWN = new Color(153,101,21);

	public static final Color COLOR_PINK = new Color(255,192,203);

	int[][] colorsForXY = new int[729][729];
	
	int getColorForHeight(int height) {
		
		Color color = COLOR_PINK;
		
		switch (height) {
		case 17:
			color = COLOR_POWDERBLUE; break;
		case 18:
			color = COLOR_CADETBLUE; break;
		case 19:
			color = COLOR_LIGHTSEAGREEN; break;
		case 20:
			color = COLOR_MEDIUMSPRINGGREEN; break;
		case 21:
			color = COLOR_MEDIUMSEAGREEN; break;
		case 22:
			color = COLOR_SEAGREEN; break;
		case 23:
			color = COLOR_DARKGREEN; break;
		case 24:
			color = COLOR_GREEN; break;
		case 25:
			color = COLOR_LIMEGREEN; break;
		case 26:
			color = COLOR_LIME; break;
		case 27:
			color = COLOR_GREENYELLOW; break;
		case 28:
			color = COLOR_YELLOWGREEN; break;
		case 29:
			color = COLOR_OLIVE; break;
		case 30:
			color = COLOR_KHAKI; break;
		case 31:
			color = COLOR_GOLD; break;
		case 32:
			color = COLOR_ORANGE; break;
		case 33:
			color = COLOR_CHOCOLATE; break;
		case 34:
			color = COLOR_SIENNA; break;
		case 35:
			color = COLOR_SADDLEBROWN; break;
		case 36:
			color = COLOR_BROWN; break;
		case 37:
			color = COLOR_MAROON; break;
		default:
			System.out.println("keine Farbe für Höhe " + height);
		}
		int colorRGB = color.getRGB();
		return colorRGB;
	}
}
