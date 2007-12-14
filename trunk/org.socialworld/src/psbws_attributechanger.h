/***************************************************************************
                          psbws_attributechanger.h  -  description
                             -------------------
    begin                : Die Aug 31 2004
    copyright            : (C) 2004 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/

/***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/

#ifndef PSBWS_ATTRIBUTECHANGER_H
#define PSBWS_ATTRIBUTECHANGER_H


/**
  *@author Mathias Sikos
  */

#include "psbws_number_types.h"
#include "psbws_types.h"  
#include "sm_hashtabelle.h"
  
class PSBWS_SimulationObject;  
class PSBWS_AttributeChanger;
          
/****************************************************************************
*           CLASS DECLARATION - PSBWS_AttributeChanger                      *
****************************************************************************/
class PSBWS_AttributeChanger
{
public:
	PSBWS_AttributeChanger(PSBWS_AttributeChanger* );
	virtual ~PSBWS_AttributeChanger();

  void change_attribute(PSBWS_SimulationObject*);

protected:
  virtual void change_attribute() = 0;

  PSBWS_SimulationObject* mP_object;
  PSBWS_AttributeChanger* mP_next;
};

/****************************************************************************
*           CLASS DECLARATION - PSBWS_IllnessChanger                        *
****************************************************************************/
class PSBWS_IllnessChanger : public PSBWS_AttributeChanger
{
public:
  PSBWS_IllnessChanger(PSBWS_AttributeChanger*);
  virtual ~PSBWS_IllnessChanger();

protected:
  virtual void change_attribute();

  virtual TPositive calculate_illness() = 0;
};

/****************************************************************************
*           CLASS DECLARATION - PSBWS_IllnessChanger_Type1                  *
****************************************************************************/
class PSBWS_IllnessChanger_Type1 : public PSBWS_IllnessChanger
{
public:
  PSBWS_IllnessChanger_Type1(PSBWS_AttributeChanger*);
  ~PSBWS_IllnessChanger_Type1();

protected:
  virtual TPositive calculate_illness();
};

/****************************************************************************
*           CLASS DECLARATION - PSBWS_AttributeCalculator                   *
****************************************************************************/
class PSBWS_AttributeCalculator : public PSBWS_AttributeChanger
{
public:
  PSBWS_AttributeCalculator(PSBWS_AttributeChanger*);
  virtual ~PSBWS_AttributeCalculator();

protected:
  virtual void change_attribute();
  TPositive get_function_value(TPositive, int,
               unsigned short int, unsigned short int, TPositive);
  TPositive get_table_value(unsigned short int, unsigned short int, TPositive);

  bool calculate_attributes();
  bool check_epsilon(float*);
  
  SM_HashTabelle<TPositive>* mSM_attribute_calculator_functions;
  TPositive* mua_attributes;
  STR_AttributeCalculatorMatrix* mSTRa_matrix;
};
   
#endif
