/***************************************************************************
                          psbws_reaktion_api_aktionstyp_32.h  -  description
                             -------------------
    begin                : Tue Jul 29 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#ifndef PSBWS_REAKTION_API_EVENT_32_H
#define PSBWS_REAKTION_API_EVENT_32_H

#include <psbws_reaktion_api.h>

/**
  *@author Mathias Sikos
  */

class PSBWS_Reaction_API_Event_32 : public PSBWS_Reaction_API  {
public: 
	PSBWS_Reaction_API_Event_32();
	virtual ~PSBWS_Reaction_API_Event_32();

protected:
	virtual void calculate_type_1(PSBWS_SimulationObject*);
	virtual void calculate_type_2(PSBWS_SimulationObject*);
	virtual void calculate_type_3(PSBWS_SimulationObject*);
	virtual void calculate_type_4(PSBWS_SimulationObject*);
	virtual void calculate_type_5(PSBWS_SimulationObject*);
	virtual void calculate_type_6(PSBWS_SimulationObject*);
	virtual void calculate_type_7(PSBWS_SimulationObject*);
	virtual void calculate_type_8(PSBWS_SimulationObject*);
	virtual void calculate_type_9(PSBWS_SimulationObject*);
	virtual void calculate_type_10(PSBWS_SimulationObject*);
	virtual void calculate_type_11(PSBWS_SimulationObject*);
	virtual void calculate_type_12(PSBWS_SimulationObject*);
	virtual void calculate_type_13(PSBWS_SimulationObject*);

};

#endif
