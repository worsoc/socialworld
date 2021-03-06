/*
* Social World
* Copyright (C) 2018  Mathias Sikos & Damian Zapadka
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
package org.socialworld.tools.dct;


public class CreateVector
{  

 private final static int MAX_ATTRIB_VALUE = 99;
 
 int threshold;			   
	
  public CreateVector(int threshold) {
	  this.threshold = threshold;
  };	
	
  public  int[] calculateVectorPosition(String input1)
  {  

   int[] result;
   result = new int[3];
	 
   					   				   
   int[] exponenten;
   exponenten = new int[3];
   
   int[] koeffizienten;
   koeffizienten = new int[3];
   
   int[] offset;
   offset = new int[3];
   
   int[] x = { 1, 4, 7, 10, 13, 16, 19, 22, 25, 28, 31, 34, 37, 40 }; 
   int[] y = { 2, 5, 8, 11, 14, 17, 20, 23, 26, 29, 32, 35, 38 };
   int[] z = { 3, 6, 9, 12, 15, 18, 21, 24, 27, 30, 33, 36, 39 };	  
   int xout = 0;
   int yout = 0;
   int zout = 0; 
   
   for(int i = 0; i < input1.length(); ++i)
   	{
   	     if(input1.charAt(i) == 'a') 
         	{
         	xout += x[0];
         	if (i < threshold )++exponenten[1];
         	}
         if(input1.charAt(i) == 'b') 
         	{
         	yout += y[0];
         	if (i < threshold )++koeffizienten[2];
         	}
         if(input1.charAt(i) == 'c') 
         	{
         	zout += z[0]; 
         	if (i < threshold )++exponenten[0];       	
         	}
         if(input1.charAt(i) == 'd') 
         	{
         	xout += x[1];
         	if (i < threshold )++offset[1];
         	}
         if(input1.charAt(i) == 'e') 
         	{
         	yout += y[1];
         	if (i < threshold )++koeffizienten[2];
         	}
         if(input1.charAt(i) == 'f') 
         	{
         	zout += z[1];
         	if (i < threshold )++offset[2];
         	}
         if(input1.charAt(i) == 'g') 
         	{
         	xout += x[2];
         	if (i < threshold )++offset[2];
         	}
         if(input1.charAt(i) == 'h') 
         	{
         	yout += y[2];
         	if (i < threshold )++offset[1];
         	}
         if(input1.charAt(i) == 'i') 
         	{
         	zout += z[2];
         	if (i < threshold )++koeffizienten[1];
         	}
         if(input1.charAt(i) == 'j') 
         	{
         	xout += x[3];
         	if (i < threshold )++offset[2];
         	}
         if(input1.charAt(i) == 'k')
         	{
         	yout += y[3];
         	if (i < threshold )++offset[0];
         	}
         if(input1.charAt(i) == 'l') 
         	{
         	zout += z[3];
         	if (i < threshold )++exponenten[2];
         	}
         if(input1.charAt(i) == 'm') 
         	{
         	xout += x[4];
         	if (i < threshold )++koeffizienten[0];
         	}
         if(input1.charAt(i) == 'n') 
         	{
         	yout += y[4];
         	if (i < threshold )++exponenten[1];
         	}
         if(input1.charAt(i) == 'o') 
         	{
         	zout += z[4];
         	if (i < threshold )++exponenten[0];
         	}
         if(input1.charAt(i) == 'p') 
         	{
         	xout += x[5];
         	if (i < threshold )++offset[1];
         	}
         if(input1.charAt(i) == 'q') 
         	{
         	yout += y[5];
         	if (i < threshold )++exponenten[1];
         	}
         if(input1.charAt(i) == 'r') 
         	{
         	zout += z[5];
         	if (i < threshold )++koeffizienten[1];
         	}
         if(input1.charAt(i) == 's') 
         	{
         	xout += x[6];
         	if (i < threshold )++koeffizienten[0];
         	}
         if(input1.charAt(i) == 't') 
         	{
         	yout += y[6];
         	if (i < threshold )++offset[1];
         	}
         if(input1.charAt(i) == 'u') 
         	{
         	zout += z[6];
         	if (i < threshold )++offset[0];
         	}
         if(input1.charAt(i) == 'v') 
         	{
         	xout += x[7];
         	if (i < threshold )++offset[0];
         	}
         if(input1.charAt(i) == 'w') 
         	{
         	yout += y[7];
         	if (i < threshold )++exponenten[1];
         	}
         if(input1.charAt(i) == 'x') 
         	{
         	zout += z[7];
         	if (i < threshold )++koeffizienten[0];
         	}
         if(input1.charAt(i) == 'y') 
         	{
         	xout += x[8];
         	if (i < threshold )++exponenten[0];
         	}
         if(input1.charAt(i) == 'z') 
         	{
         	yout += y[8];
         	if (i < threshold )++koeffizienten[1];
         	}  	
         if(input1.charAt(i) == '�') 
         	{
         	zout += z[8];
         	if (i < threshold )++koeffizienten[1];
         	}
         if(input1.charAt(i) == '�') 
         	{
         	xout += x[9];
         	if (i < threshold )++exponenten[2];
         	}
         if(input1.charAt(i) == '�') 
         	{
         	yout += y[9];
         	if (i < threshold )++offset[0];
         	}
         if(input1.charAt(i) == '�') 
         	{
         	zout += z[9];
         	if (i < threshold )++koeffizienten[0];
         	}
         if(input1.charAt(i) == '0') 
         	{
         	xout += x[10];
         	if (i < threshold )++offset[2];
         	}
         if(input1.charAt(i) == '1') 
         	{
         	yout += y[10];
         	if (i < threshold )++koeffizienten[0];
         	}
         if(input1.charAt(i) == '2') 
         	{
         	zout += z[10];
         	if (i < threshold )++exponenten[2];
         	}
         if(input1.charAt(i) == '3') 
         	{
         	xout += x[11];
         	if (i < threshold )++exponenten[0];
         	}
         if(input1.charAt(i) == '4') 
         	{
         	yout += y[11];
         	if (i < threshold )++koeffizienten[2];
         	}
         if(input1.charAt(i) == '5') 
         	{
         	zout += z[11];
         	if (i < threshold )++exponenten[0];
         	}
         if(input1.charAt(i) == '6') 
         	{
         	xout += x[12];
         	if (i < threshold )++koeffizienten[2];
         	}
         if(input1.charAt(i) == '7') 
         	{
         	yout += y[12];
         	if (i < threshold )++koeffizienten[2];
         	}
         if(input1.charAt(i) == '8') 
         	{
         	zout += z[12];
         	if (i < threshold )++offset[0];
         	}
         if(input1.charAt(i) == '9') 
         	{
         	xout += x[13];
         	if (i < threshold )++exponenten[2];
         	}
   	}
	
   System.out.println(exponenten[0]+" "+exponenten[1]+" "+exponenten[2]);     
   System.out.println(koeffizienten[0]+" "+koeffizienten[1]+" "+koeffizienten[2]);   
   System.out.println(offset[0]+" "+offset[1]+" "+offset[2]);   

   int xafter = xout; 
   int yafter = yout;
   int zafter = zout; 
   
   for(--exponenten[0] ;exponenten[0] > 0; --exponenten[0]) 
   	{
   	xafter *= xout;
   	}
   for(--exponenten[1] ;exponenten[1] > 0; --exponenten[1]) 
   	{
   	yafter *= yout;
   	}
   for(--exponenten[2] ;exponenten[2] > 0; --exponenten[2]) 
   	{
   	zafter *= zout;
   	}
   
   int xend = koeffizienten[0] * xafter + offset[0];
   int yend = koeffizienten[1] * yafter + offset[1];
   int zend = koeffizienten[2] * zafter + offset[2];
   
   System.out.println("===================");
   System.out.println("x="+xend+" "+"y="+yend+" "+"z="+zend);
   System.out.println("===================");
   
   if(xend < 0)
   {
	xend *= -1;
   }
   
   if(yend < 0)
   {
	yend *= -1;
   }
   
   if(zend < 0)
   {
	zend *= -1;
   }
   				   
   xend %= 4000000; 
   yend %= 4000000; 
   zend %= 4000000; 		       
   xend -= 2000000; 
   yend -= 2000000;
   zend -= 2000000;			    		               
   
   System.out.println("===================");
   System.out.println("x="+xout+" "+"y="+yout+" "+"z="+zout);
   System.out.println("===================");
   System.out.println("x="+xend+" "+"y="+yend+" "+"z="+zend);
   System.out.println("===================");

   result[0] = xend;
   result[1] = yend;
   result[2] = zend;
   
   return result;
  
  }
  
  public  int[] calculateVectorAttributeArray(String input1)
  {  

   int[] result;
   result = new int[9];
	 
   					   				   
    
   int out_1 = 0;
   int out_2 = 0;
   int out_3 = 0; 
   int out_4 = 0;
   int out_5 = 0;
   int out_6 = 0; 
   int out_7 = 0;
   int out_8 = 0;
   int out_9 = 0; 
   
   for(int i = 0; i < input1.length(); ++i)
   {
	   switch (input1.charAt(i)) {
	   case 'a': out_1++; break;
	   case 'b': out_2++; break;
	   case 'c': out_3++; break;
	   case 'd': out_4++; break;
	   case 'e': out_5++; break;
	   case 'f': out_6++; break;
	   case 'g': out_7++; break;
	   case 'h': out_8++; break;
	   case 'i': out_9++; break;
	   case 'j': out_1++; break;
	   case 'k': out_2++; break;
	   case 'l': out_3++; break;
	   case 'm': out_4++; break;
	   case 'n': out_5++; break;
	   case 'o': out_6++; break;
	   case 'p': out_7++; break;
	   case 'q': out_8++; break;
	   case 'r': out_9++; break;
	   case 's': out_1++; break;
	   case 't': out_2++; break;
	   case 'u': out_3++; break;
	   case 'v': out_4++; break;
	   case 'w': out_5++; break;
	   case 'x': out_6++; break;
	   case 'y': out_7++; break;
	   case 'z': out_8++; break;
	   case 'ä': out_9++; break;
	   case 'ö': out_1++; break;
	   case 'ü': out_2++; break;
	   case 'ß': out_3++; break;
	   case '0': out_4++; break;
	   case '1': out_5++; break;
	   case '2': out_6++; break;
	   case '3': out_7++; break;
	   case '4': out_8++; break;
	   case '5': out_9++; break;
	   case '6': out_1++; break;
	   case '7': out_2++; break;
	   case '8': out_3++; break;
	   case '9': out_4++; break;
	   case ' ': out_5++; break;
	   case ',': out_6++; break;
	   case '.': out_7++; break;
	   default: out_8++;
   	   }
   }

   if (out_1 > MAX_ATTRIB_VALUE) out_1 = MAX_ATTRIB_VALUE;
   if (out_2 > MAX_ATTRIB_VALUE) out_2 = MAX_ATTRIB_VALUE;
   if (out_3 > MAX_ATTRIB_VALUE) out_3 = MAX_ATTRIB_VALUE;
   if (out_4 > MAX_ATTRIB_VALUE) out_4 = MAX_ATTRIB_VALUE;
   if (out_5 > MAX_ATTRIB_VALUE) out_5 = MAX_ATTRIB_VALUE;
   if (out_6 > MAX_ATTRIB_VALUE) out_6 = MAX_ATTRIB_VALUE;
   if (out_7 > MAX_ATTRIB_VALUE) out_7 = MAX_ATTRIB_VALUE;
   if (out_8 > MAX_ATTRIB_VALUE) out_8 = MAX_ATTRIB_VALUE;
   if (out_9 > MAX_ATTRIB_VALUE) out_9 = MAX_ATTRIB_VALUE;
   
   
   System.out.println("===================");
   System.out.println("("+out_1+", "+ out_2+", "+out_3 +", "+ out_4+", "+out_5+", "+ out_6+", "+out_7+", "+ out_8+", "+out_9 + ")");
   System.out.println("===================");
   
	    		               
   result[0] = out_1;
   result[1] = out_2;
   result[2] = out_3;
   result[3] = out_4;
   result[4] = out_5;
   result[5] = out_6;
   result[6] = out_7;
   result[7] = out_8;
   result[8] = out_9;
  
   return result;
  
  }

}