/***************************************************************************
                          psbws_position.h  -  description
                             -------------------
    begin                : Sun May 18 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#ifndef PSBWS_POSITION_H
#define PSBWS_POSITION_H


/**
  *@author Mathias Sikos
  */

#include "psbws_types.h"
#include "psbws_attribute.h"

class PSBWS_Simulation;

class PSBWS_Position : public PSBWS_Attribute
{
public: 
	PSBWS_Position(PSBWS_Simulation*, TNatural x, TNatural y, TNatural z);
	PSBWS_Position(PSBWS_Simulation* , STR_3D wk3D);
	virtual ~PSBWS_Position();

	void change_position3D(int, int, int);				    // relative Koordinaten
	void change_position3D(STR_3D );
	TNatural get_x();																			// liefert Koordinaten
	TNatural get_y();
	TNatural get_z();
  void set_x(TNatural);
  void set_y(TNatural);
  void set_z(TNatural);
	TNatural get_location();																		// liefert Location
	unsigned short int get_terrain();									// liefert Terrain
	TNatural determine_location(); 							// liefert Location, wird neu bestimmt
	unsigned short int determine_terrain(); 							// liefert Terrain, wird neu bestimmt

private:
  unsigned short int decide_terrain();							// bestimmt Terrain an aktueller Position
	TNatural decide_location();								// bestimmt Location an aktueller Position
	void init_terrain();												// Startwerte für Terrain
	void init_location();											// und Location
	TNatural search_locationTree(TNatural x, TNatural z);
	unsigned short int search_terrainTree(TNatural x, TNatural z);

  PSBWS_Simulation*     mP_simulation;
  TPNR mu_PNr;					 									// als Index für Person-Feld
	STR_3D mstr_3D;							    // absolute Koordinaten Ursprung (0,0,0)
  TNatural mu_locationID;								    // als Index für Location-Feld
  unsigned short int mu_terrainID;									// als Index für Terrain-Feld
};

#endif
