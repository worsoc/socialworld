/*
* Social World
* Copyright (C) 2023  Mathias Sikos
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


public class CreatePropertySet
{  

	 private final static int MAX_SHARE_VALUE = 1000;
	 
		
	  public CreatePropertySet() {
	  };	
		
	
	  public  int[][] calculatePropertySet(String input1, int maxSetElementValue)
	  {  
	
	   int[][] tmp = new int[0][0];
	   int[][] result;
	   
	   if (input1.length() == 0) return tmp;
	   
		 
	   char sign;
	   int indexSet;
	   
	   char firstSign;
	   int numberElementsInSet;
	    
	   int elementValue;
	   
	   firstSign = input1.charAt(0);
	   numberElementsInSet = transformSignToInt(firstSign, 20);
	   if (numberElementsInSet == 0) numberElementsInSet = 1;
	   tmp = new int[numberElementsInSet][2];
	  
	 
	   for(int i = 0; i < numberElementsInSet; i++)
	   {
		   sign = input1.charAt(i + 1);
		   // + 1 because of ignoring index 0 (nothing) and modulo operation (maxSetElementValue is a valid value)
		   elementValue = transformSignToInt(sign, maxSetElementValue) + 1;
		   tmp[i][0] = elementValue;
		   tmp[i][1] = 0;
	   }
	
	
	   
	   for(int i = numberElementsInSet + 1; i < input1.length(); i++)
	   {
		   sign = input1.charAt(i);
		   indexSet = transformSignToInt(sign, numberElementsInSet);
		   tmp[indexSet][1]++;
	   }
	
	   
	   
	   result = finalizeShares(tmp, numberElementsInSet);
		    		               
	  
	   return result;
	  
	  }
	  
	  private int transformSignToInt(char sign, int modulo) {
		  return (int) sign % modulo;
	  }
	  
	  private int[][] finalizeShares(int[][] tmp, int numberElementsInSet) {
		  int[][] result;
		  int sum = 0;
		  float factor;
		  int addToMax;
		  int indexMax = 0;
		  int element = 0;
		  int countIgnores = 0;

		  for (int i = 0; i < numberElementsInSet; i++) {
			  element = tmp[i][0];
			  if (element == 0) continue;
			  for (int j = i+1; j < numberElementsInSet; j++) {
				  if (tmp[j][0] == element)  {
					  tmp[i][1] = tmp[i][1] + tmp[j][1];
					  // ignore in next iterations
					  tmp[j][0] = 0;
					  tmp[j][1] = 0;
				  }
			  }
		  }
		  
		  
		  for (int i = 0; i < numberElementsInSet; i++) {
			  sum = sum + tmp[i][1];
			  if (tmp[i][1] > tmp[indexMax][1]) indexMax = i;
			  if ((tmp[i][1]) == 0) countIgnores++;
		  }
		  factor = 100 / sum;
		  for (int i = 0; i < numberElementsInSet; i++) {
			  tmp[i][1] = (int)(tmp[i][1] * factor);
		  }
		  sum = 0;
		  for (int i = 0; i < numberElementsInSet; i++) {
			  sum = sum + tmp[i][1];
		  }
		  addToMax = 100 - sum;
		  tmp[indexMax][1] = tmp[indexMax][1] + addToMax;
		  
		  result = new int[numberElementsInSet - countIgnores][2];
		  for (int i = 0; i < numberElementsInSet - countIgnores; i++) {
			  indexMax = 0;
			  for (int j = 0; j < numberElementsInSet; j++) {
				  if (tmp[j][1] > tmp[indexMax][1]) indexMax = j;
			  }
			  result[i][0] = tmp[indexMax][0];
			  result[i][1] = tmp[indexMax][1];
			  tmp[indexMax][1] = 0;
		  }
		  return result;
	  }

}