/*
* Social World
* Copyright (C) 2018  Mathias Sikos
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



public class CreateAttributeCalcMatrix 
{
	
	/////////////////////
	//Constant variable//
	/////////////////////
	
	int test;
	static int anzahlMatrixRows = 9;
	
	/////////////////////////////////////////////////////////
	//Getting ready for importing commands to another class//
	/////////////////////////////////////////////////////////
	
	public CreateAttributeCalcMatrix() 
	{
		  
	};
	
	public  double[][] calculate(String input1)
	 {
		
		//////////////////////////////////////////////////////////////////////////
		//Changing the amount of change in Matrix (depends on how big is input1)//
		//////////////////////////////////////////////////////////////////////////
		
		double change = 1;
		double lchange = 1; 
		
		if (input1.length() > 150) input1 = input1.substring(0,  150);
		if ( input1.length() < 50) change = 4;
		if ( input1.length() < 100) change = 3;
		if ( input1.length() <= 150) change = 2;
		
		//////////////////////////////////////
		//Separation of input1 to two inputs//
		//////////////////////////////////////
		
		String[] input = new String [2];
		
		if(input1.length() == 1)
		{
			input[0] = input1; 
			input[1] =" ";
		}
		
		else
		{
			if (input1.length() % 2 == 0)
			{
				input[0] = input1.substring(0, input1.length() / 2);
				input[1] = input1.substring(input1.length() / 2, input1.length());		
			}
			
			else
			{
				int t = (input1.length() - 1) / 2; 
				input[0] = input1.substring(0, t);
				input[1] = input1.substring(t, input1.length());
			}
		}
		
		/////////////////////////////////////////////////
		//Setting input 0 and 1 to lower and upper case//
		/////////////////////////////////////////////////
		
		input[0] = input[0].toLowerCase();
		input[1] = input[1].toUpperCase();
		
		///////////////////////////////
		//Testing if everything is OK//
		///////////////////////////////
		
		System.out.println(input[0]);
		System.out.println(input[1]);
		
		//////////////////////////////
		//Creating constant variable//
		//////////////////////////////
		 
		double matrixT[][] = new double [anzahlMatrixRows][anzahlMatrixRows];
		char zeichen;
		int zufall_i = 0;
		int zufall_j = 0;
		
		///////////////////////////////////////////////////////////
		//Creating change in Matrix for each symbol in two inputs//
		///////////////////////////////////////////////////////////
		
		for(int r = 0; 0 < 2; ++r)
		{	
			
		 for(int i = 0; i < input[r].length(); ++i)
		   	{
			 
			    zeichen =  input[r].charAt(i);
			    
			    switch (zeichen)
			    {
			    
			     case 'a': matrixT[0][0] += change; break;
			     case 'b': matrixT[0][1] += change; break;
			     case 'c': matrixT[0][2] += lchange; break;
			     case 'd': matrixT[0][3] += lchange; break;
			     case 'j': matrixT[8][1] += change; break;
			     case 'f': matrixT[0][5] += change; break;
			     case 'g': matrixT[0][6] += lchange; break;
			     case 'h': matrixT[0][7] += lchange; break;
			     case '1': matrixT[1][0] += change; break;
			     case 'e': matrixT[1][1] += lchange; break;
			     case 'k': matrixT[1][2] += change; break;
			     case 'l': matrixT[1][3] += change; break;
			     case 'm': matrixT[1][4] += change; break;
			     case 'n': matrixT[1][5] += change; break;
			     case 'o': matrixT[1][6] += change; break;
			     case 'p': matrixT[8][5] += change; break;
			     case 'q': matrixT[8][3] += change; break;
			     case 'r': matrixT[2][1] += lchange; break;
			     case 's': matrixT[2][2] += lchange; break;
			     case 't': matrixT[2][3] += change; break;
			     case 'u': matrixT[2][4] += lchange; break;
			     case 'v': matrixT[2][5] += change; break;
			     case 'w': matrixT[2][6] += change; break;
			     case 'x': matrixT[2][7] += change; break;
			     case 'y': matrixT[3][0] += change; break;
			     case 'z': matrixT[3][1] += change; break;
			     case '0': matrixT[3][2] += change; break;
			     case 'T': matrixT[3][3] += lchange; break;
			     case '2': matrixT[3][4] += change; break;
			     case '3': matrixT[3][5] += change; break;
			     case '4': matrixT[8][4] += change; break;
			     case '5': matrixT[3][7] += change; break;
			     case '6': matrixT[4][0] += change; break;
			     case '7': matrixT[4][1] += change; break;
			     case '8': matrixT[8][6] += change; break;
			     case '9': matrixT[4][3] += change; break;
			     case 'A': matrixT[4][4] += lchange; break;
			     case 'B': matrixT[4][5] += change; break;
			     case 'C': matrixT[4][6] += change; break;
			     case 'D': matrixT[4][7] += change; break;
			     case ',': matrixT[5][0] += change; break;
			     case 'F': matrixT[5][1] += change; break;
			     case 'G': matrixT[5][2] += change; break;
			     case 'H': matrixT[5][3] += change; break;
			     case 'J': matrixT[5][4] += change; break;
			     case 'N': matrixT[5][5] += lchange; break;
			     case 'K': matrixT[8][2] += change; break;
			     case 'L': matrixT[5][7] += lchange; break;
			     case 'M': matrixT[6][0] += lchange; break;
			     case 'I': matrixT[6][1] += lchange; break;
			     case 'O': matrixT[6][2] += lchange; break;
			     case 'P': matrixT[6][3] += change; break;
			     case 'Q': matrixT[8][7] += change; break;
			     case 'R': matrixT[6][5] += change; break;
			     case 'S': matrixT[6][6] += change; break;
			     case 'i': matrixT[6][7] += change; break;
			     case 'U': matrixT[7][0] += change; break;
			     case 'V': matrixT[7][1] += change; break;
			     case 'W': matrixT[7][2] += lchange; break;
			     case 'X': matrixT[7][3] += change; break;
			     case 'Y': matrixT[7][4] += change; break;
			     case 'Z': matrixT[7][5] += change; break;
			     case '.': matrixT[8][0] += change; break;
			     case 'E': matrixT[7][7] += change; break;
			      
			     default: 
			    	 zufall_i = (int)(Math.random() * anzahlMatrixRows);
			    	 zufall_j = (int)(Math.random() * anzahlMatrixRows);
			    	 System.out.println("Zufallspaar: (" +  zufall_i + "," + zufall_j + ")");
			    	 matrixT[zufall_i][zufall_j]  += lchange;

			    }			    				        
		         
			 switch  (zeichen) 
			 {
			     case 'A': matrixT[7][6] += lchange; break;
			     case 'd': matrixT[0][4] += lchange; break;
			     case 'O': matrixT[5][6] += lchange; break;
			     case 'h': matrixT[2][0] += lchange; break;
			     case 'L': matrixT[3][6] += lchange; break;
			     case 'c': matrixT[1][7] += lchange; break;
			     case 'M': matrixT[4][2] += lchange; break;
			     case 'g': matrixT[6][4] += lchange; break;
			     case 'r': matrixT[8][8] += lchange; break;
			     case 'f': matrixT[0][8] += lchange; break;
			     case 'W': matrixT[1][8] += lchange; break;
			     case 's': matrixT[2][8] += lchange; break;
			     case 'e': matrixT[3][8] += lchange; break;
			     case 'I': matrixT[4][8] += lchange; break;
			     case 'N': matrixT[5][8] += lchange; break;
			     case 'T': matrixT[6][8] += lchange; break;
			     case 'u': matrixT[7][8] += lchange; break;

		   	}
		 
		   	} 
		 
		 /////////////////////
		 //Returning results//
		 /////////////////////
		 
		 if(r == 1)
		 {
			 funktion (matrixT);
			 return matrixT;
		 }
		 
		}	
		
	 }
    
	/////////////////////////////////////////////////////////////////////
	//Function algorithm for Matrix (a[i][i] = 1 - every other in line)//  
	/////////////////////////////////////////////////////////////////////
	
	public static void funktion (double[][] matrixT )
	{
		
		double sum;

		for (int i = 0; i < anzahlMatrixRows; ++i)
		{
			sum = 0;
			for(int j = 0; j < anzahlMatrixRows; ++j)
			{
				if (i != j) 	sum += matrixT[i][j];
		    }
			matrixT[i][i] = 100 - sum ; 
		}
		
	}
	
	///////////////////////////////////////
	//Algorithm for creating matrix table//
	///////////////////////////////////////
	
	public void printResults(double matrixT[][]) 
	{
		
		System.out.print("|");
		
		for(int i = 0; i < anzahlMatrixRows; ++i)
		{
			for(int j = 0; j < anzahlMatrixRows; ++j)
			{
				System.out.print(matrixT[i][j] + "|");
			}
			
			System.out.println();
			if (i < anzahlMatrixRows) System.out.print("|");
		
		}
		
	}
	
	//////////////////////////////////////////////
	//Printing results (used in MatrixMain.java)//
	//////////////////////////////////////////////
	
	public void writeResultsToFile(double matrixT[][]) 
	{
		
		for(int i = 0; i < anzahlMatrixRows; ++i)
		{
			for(int j = 0; j < anzahlMatrixRows; ++j)
			{
				System.out.print((int)matrixT[i][j] + " ");
			}
		
		}
		
	}
}
