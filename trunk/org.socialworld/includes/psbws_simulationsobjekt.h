/***************************************************************************
                          psbws_simulationsobjekt.h  -  description
                             -------------------
    begin                : Tue Jul 29 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#ifndef PSBWS_SIMULATIONSOBJEKT_H
#define PSBWS_SIMULATIONSOBJEKT_H

#include "psbws_tweltkoordinaten.h"
#include "psbws_number_types.h"
#include "psbws_types.h"

/**
  *@author Mathias Sikos
  */

class PSBWS_Object_Manager;
class PSBWS_Event;
class PSBWS_Actioner;
class PSBWS_Position;

/****************************************************************************
*           CLASS DECLARATION - PSBWS_SimulationObject                      *
****************************************************************************/
class PSBWS_SimulationObject                                               
{                                                                          
public:                                                                    

/************* CONSTRUCTOR / DESTRUCTOR ************************************/
	PSBWS_SimulationObject(PSBWS_Object_Manager*, STR_3D);
	virtual ~PSBWS_SimulationObject();

/************* INITIALIZATION **********************************************/
  virtual void init(TONR) = 0;

/************* EVENT HANDLING **********************************************/
  PSBWS_Event* get_event();
  void set_event(PSBWS_Event*);
	void delete_event();
	virtual int determine_influence(PSBWS_Event*) ;

/************* POSITION HANDLING *******************************************/
  PSBWS_Position* get_position();
	void set_positionX(TNatural);
	void set_positionY(TNatural);
	void set_positionZ(TNatural);

/************* ACTION HANDLING *********************************************/
  PSBWS_Actioner* get_actioner();

/************* ATTRIBUTE HANDLING ******************************************/
  STR_AttributeCalculatorMatrix* get_attribute_calculator_matrix();
  TPositive* get_attribute_array();
  void set_attribute_array(float*);  
  void set_illness(TPositive);
  
protected:

/************* INITIALIZATION **********************************************/
  TONR mu_ONr;
	PSBWS_Object_Manager* mP_objectManager;

/************* POSITION HANDLING *******************************************/
  PSBWS_Position* mP_position;

/************* EVENT HANDLING **********************************************/
	PSBWS_Event* mP_releasedEvent;

/************* ACTION HANDLING *********************************************/
  PSBWS_Actioner* mP_actioner;

/************* ATTRIBUTE HANDLING ******************************************/
 	TUChar mu_effectType_event[256];
	TUChar mu_reactionType_event[256];
  STR_AttributeCalculatorMatrix* mSTRa_attributeCalculatorMatrix;
  TPositive* mua_attributes;
	TPositive mu_illness;
	TPositive	mu_magic;
};

#endif
