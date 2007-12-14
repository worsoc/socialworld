/***************************************************************************
                          psbws_position.cpp  -  description
                             -------------------
    begin                : Sun May 18 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#include "psbws_position.h"
#include "psbws_simulation.h"

PSBWS_Position::PSBWS_Position(PSBWS_Simulation* aP_simulation, TNatural au_x, TNatural au_y, TNatural au_z)
{
    mP_simulation = aP_simulation;
    mstr_3D.u_x = au_x;                  // Koordinaten initialisieren
		mstr_3D.u_y = au_y;
		mstr_3D.u_z = au_z;
		init_terrain();													// Terrain und Location bestimmen
		init_location();
}

PSBWS_Position::PSBWS_Position(PSBWS_Simulation* aP_simulation, STR_3D wk3D)
{
    mP_simulation = aP_simulation;
		mstr_3D = wk3D;                 // Koordinaten initialisieren
		init_terrain();													// Terrain und Location bestimmen
		init_location();
}

PSBWS_Position::~PSBWS_Position()
{
}


TNatural PSBWS_Position::get_x()
{
	 return mstr_3D.u_x;
}

TNatural PSBWS_Position::get_y()
{
	 return mstr_3D.u_y;
}
TNatural PSBWS_Position::get_z()
{
	 return mstr_3D.u_z;
}

void PSBWS_Position::set_x(TNatural au_x)
{
	 mstr_3D.u_x = au_x;
}
void PSBWS_Position::set_y(TNatural au_y)
{
	 mstr_3D.u_y = au_y;
}
void PSBWS_Position::set_z(TNatural au_z)
{
	 mstr_3D.u_z = au_z;
}



void PSBWS_Position::init_location()
{
    mu_locationID = decide_location();
}	

void PSBWS_Position::init_terrain()
{
    mu_terrainID = decide_terrain();
}

TNatural PSBWS_Position::get_location()
{
	 return mu_locationID;
}

unsigned short int PSBWS_Position::get_terrain()
{
	 return mu_terrainID;
}

TNatural PSBWS_Position::determine_location()
{
    mu_locationID = decide_location();
	return mu_locationID;
}

unsigned short int PSBWS_Position::determine_terrain()
{
    mu_terrainID = decide_terrain();
	return mu_terrainID;
}

void PSBWS_Position::change_position3D(int ai_dx, int ai_dy, int ai_dz)
{																    // Position ändert sich um dx ,dy, dz
	mstr_3D.u_x += ai_dx;										  // x = x + dx
	mstr_3D.u_y += ai_dy;											// y = y + dy
	mstr_3D.u_z += ai_dz;											// z = z + dz
}

void PSBWS_Position::change_position3D(STR_3D astr_3D)
{																    // Position ändert sich um d_wk3D
	mstr_3D.u_x += astr_3D.u_x;										
	mstr_3D.u_y += astr_3D.u_y;											
	mstr_3D.u_z += astr_3D.u_z;											
}

TNatural PSBWS_Position::decide_location()
{																	// ruft die Suchbaumfunktion auf
	TNatural uErgebnis = search_locationTree(mstr_3D.u_x, mstr_3D.u_z);
	return uErgebnis;
}

unsigned short int PSBWS_Position::decide_terrain()
{																	// ruft die Suchbaumfunktion auf
	unsigned short int uErgebnis = search_terrainTree(mstr_3D.u_x, mstr_3D.u_z);
	return uErgebnis;
}

 // --- Suchbaum für Location und Terrain ---

TNatural PSBWS_Position::search_locationTree(TNatural au_x, TNatural au_z)
{
/*
  STR_NodeSearchLocationX* lSTR_node_location_x = mP_simulation->worldMap->STR_location_x;
  STR_NodeSearchLocationZ* lSTR_node_location_z = mP_simulation->worldMap->STR_location_z;

	do {															// erst über die x-Koordinaten suchen
		lSTR_node_location_x = lSTR_node_location_x->STR_max;				//  linke Grenze nach rechts
		while (au_x < lSTR_node_location_x->u_x) 						//	  gehe solange nach links, 	
			lSTR_node_location_x = lSTR_node_location_x->STR_min;			// bis x-Koord. von linker Grenze beschränkt
	} while (lSTR_node_location_x->STR_max != 0);						// das Blatt ist erreicht !
	lSTR_node_location_z = lSTR_node_location_x->STR_z;				// Zeiger auf den Z-Suchbaum, in dem
																	//    weitergesucht werden muss
	do {															// dann über die z-Koordinaten; Baum enthält nur noch
		lSTR_node_location_z = lSTR_node_location_z->STR_max;				// Locations, die durch x bestimmt wurden
		while (au_z < lSTR_node_location_z->u_z)
				 lSTR_node_location_z = lSTR_node_location_z->STR_min;
	} while (lSTR_node_location_z->STR_max != 0);						// Blatt erreicht
	TNatural lu_result = lSTR_node_location_z->u_locationID;
	return lu_result;												// Location eindeutig bestimmt
*/
  return 0;
}

unsigned short int PSBWS_Position::search_terrainTree(TNatural au_x, TNatural au_z)
{																	// analog
/* 
  STR_NodeSearchTerrainX* lSTR_node_terrain_x = mP_simulation->worldMap->STR_terrain_x;
  STR_NodeSearchTerrainZ* lSTR_node_terrain_z = mP_simulation->worldMap->STR_terrain_z;
	do {
		lSTR_node_terrain_x = lSTR_node_terrain_x->STR_max;
		while (au_x < lSTR_node_terrain_x->u_x)
					lSTR_node_terrain_x = lSTR_node_terrain_x->STR_min;
	} while (lSTR_node_terrain_x->STR_max != 0);
	lSTR_node_terrain_z = lSTR_node_terrain_x->STR_z;
	do {
		lSTR_node_terrain_z = lSTR_node_terrain_z->STR_max;
		while (au_z < lSTR_node_terrain_z->u_z) lSTR_node_terrain_z = lSTR_node_terrain_z->STR_min;
	} while (lSTR_node_terrain_z->STR_max != 0);
	unsigned short int lu_result = lSTR_node_terrain_z->u_terrainID;
	return lu_result;												// Terrain eindeutig bestimmt
*/
  return 0;  
}
