/***************************************************************************
                          psbws_auswirkung_api.h  -  description
                             -------------------
    begin                : Tue Jul 29 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#ifndef PSBWS_AUSWIRKUNG_API_H
#define PSBWS_AUSWIRKUNG_API_H

#include "psbws_api.h"

/**
  *@author Mathias Sikos
  */

class PSBWS_Effect_API : public PSBWS_API  {
public: 
	PSBWS_Effect_API();
	virtual ~PSBWS_Effect_API();

	virtual void calculate(PSBWS_SimulationObject* pObjekt, TUChar uTyp);

protected:
  virtual void create_pipeline() = 0;
  virtual void destroy_pipeline() = 0;

	virtual void calculate_type_1(PSBWS_SimulationObject* pObjekt) {};
	virtual void calculate_type_2(PSBWS_SimulationObject* pObjekt) {};
	virtual void calculate_type_3(PSBWS_SimulationObject* pObjekt) {};
	virtual void calculate_type_4(PSBWS_SimulationObject* pObjekt) {};
	virtual void calculate_type_5(PSBWS_SimulationObject* pObjekt) {};
	virtual void calculate_type_6(PSBWS_SimulationObject* pObjekt) {};
	virtual void calculate_type_7(PSBWS_SimulationObject* pObjekt) {};
	virtual void calculate_type_8(PSBWS_SimulationObject* pObjekt) {};
	virtual void calculate_type_9(PSBWS_SimulationObject* pObjekt) {};
	virtual void calculate_type_10(PSBWS_SimulationObject* pObjekt) {};
	virtual void calculate_type_11(PSBWS_SimulationObject* pObjekt) {};


	virtual void calculate_type_12(PSBWS_SimulationObject* pObjekt) {};
	virtual void calculate_type_13(PSBWS_SimulationObject* pObjekt) {};
	
};

#endif
