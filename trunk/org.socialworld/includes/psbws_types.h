/***************************************************************************
                          psbws_types.h.h  -  description
                             -------------------
    begin                : Mon Jun 9 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/

#ifndef PSBWS_TYPES_H
#define PSBWS_TYPES_H

#include <stdlib.h>

class PSBWS_Event;


#include "psbws_number_types.h"
#include "psbws_tweltkoordinaten.h"

typedef struct STR_Mood
{
}STR_Mood;

typedef struct STR_ActionElement
{
  TNatural u_actionCode;				 // Steuerwort: enthält Befehl und Operanden
	TNatural u_time_min;				 // wann soll Aktion ausgefuehrt werden
	TNatural u_time_max;				 // bis wann soll Aktionausfuehrung gestartet werden
	TUChar u_priority;					 // welche Aktion bevorzugt
	unsigned short u_duration_remained;			 // wie viele Zeiteinheiten läuft Aktion noch
	unsigned short u_duration;					     // wie lange insgesamt        0 - abgeschlossen
	struct STR_ActionElement* STR_next;				 // Zeiger auf die Struktur (aufs nächste Aktionselement)
} STR_ActionElement;

typedef struct STR_Candidate
{
	TONR u_ONr;
	struct STR_Candidate* STR_next;
} STR_Candidate;


typedef struct STR_LaterEvent
{
	PSBWS_Event* P_event;
  struct STR_LaterEvent* STR_next;
} STR_LaterEvent;

typedef struct STR_NodeSearchLocationZ {										// Verbund aus
	TNatural u_z;										// zWert : unterer Grenzwert der Location
	struct STR_NodeSearchLocationZ* STR_min;										// min : zeigt auf Knoten mit kleineren z-Koord.
  struct STR_NodeSearchLocationZ* STR_max;										// max : zeigt auf Knoten mit grössern z-Koordinaten
	TNatural u_locationID;									// Location über Koordinaten eindeutig bestimmt
} STR_NodeSearchLocationZ;												// zeigt auf solchen Verbund


typedef struct STR_NodeSearchLocationX {										// Verbund aus
	TNatural u_x;										// xWert : linker Grenzwert der Location
	struct STR_NodeSearchLocationX* STR_min;										// min : zeigt auf Knoten mit kleineren x-Koord.
  struct STR_NodeSearchLocationX* STR_max;  									// max : zeigt auf Knoten mit grössern x-Koordinaten
	STR_NodeSearchLocationZ* STR_z;										// aufZ : zeigt auf Startknoten eines Z-Suchbaums
} STR_NodeSearchLocationX;												// zeigt auf solchen Verbund

typedef struct STR_NodeSearchTerrainZ {										// analog zu tKnotenSuchLocationZ
	TNatural u_z;										// untere Grenze des Terrains
	struct STR_NodeSearchTerrainZ* STR_min;
  struct STR_NodeSearchTerrainZ* STR_max;
	unsigned short int u_terrainID;									// Terrain über Koordinaten eindeutig bestimmt
} STR_NodeSearchTerrainZ;												// zeigt auf solchen Verbund

typedef struct STR_NodeSearchTerrainX {										// analog zu tKnotenSuchLocationX
	TNatural u_x;										// linker Grenzwert des Terrains
	struct STR_NodeSearchTerrainX* STR_min;
  struct STR_NodeSearchTerrainX* STR_max;
	STR_NodeSearchTerrainZ* STR_z;
} STR_NodeSearchTerrainX;												// zeigt auf solchen Verbund

typedef struct STR_WorldMap {
     STR_NodeSearchLocationX* STR_location_x;
     STR_NodeSearchLocationZ* STR_location_z;
     STR_NodeSearchTerrainX* STR_terrain_x;
     STR_NodeSearchTerrainZ* STR_terrain_z;
} STR_WorldMap;

typedef struct STR_Term_AttributeCalculator {
     TUChar u_functor;
     float f_constant;
     struct STR_IF_Expression* STR_if_expression;
} STR_Term_AttributeCalculator;

typedef struct STR_IF_Expression {
    unsigned short int u_operation;
    STR_Term_AttributeCalculator* STR_term_true;
    STR_Term_AttributeCalculator* STR_term_false;
} STR_IF_Expression;

typedef struct STR_AttributeCalculatorMatrix {
    float f_share;
    TPositive u_function;
    int i_offset;
} STR_AttributeCalculatorMatrix;
#endif
