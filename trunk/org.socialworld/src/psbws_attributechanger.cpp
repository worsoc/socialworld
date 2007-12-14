/***************************************************************************
                          psbws_attributechanger.cpp  -  description
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

#include "psbws_attributechanger.h"
#include "psbws_simulationsobjekt.h"

#include "sm_hashtabelle.cpp"

////////////////////////////////////////////////////////////////////////////
//            PSBWS_AttributeChanger
////////////////////////////////////////////////////////////////////////////

PSBWS_AttributeChanger::PSBWS_AttributeChanger
  (PSBWS_AttributeChanger* aP_next)
{
  mP_next = aP_next;
}

////////////////////////////////////////////////////////////////////////////

PSBWS_AttributeChanger::~PSBWS_AttributeChanger(){
}

////////////////////////////////////////////////////////////////////////////

void PSBWS_AttributeChanger::change_attribute(PSBWS_SimulationObject* aP_object)
{
  mP_object = aP_object;
  change_attribute();
  if  (mP_next != 0)
      mP_next->change_attribute(mP_object);  
}

////////////////////////////////////////////////////////////////////////////
//            PSBWS_IllnessChanger
////////////////////////////////////////////////////////////////////////////

PSBWS_IllnessChanger::PSBWS_IllnessChanger(PSBWS_AttributeChanger* aP_next)
  :PSBWS_AttributeChanger(aP_next)
{
}

////////////////////////////////////////////////////////////////////////////

PSBWS_IllnessChanger::~PSBWS_IllnessChanger(){
}
  
////////////////////////////////////////////////////////////////////////////

void PSBWS_IllnessChanger::change_attribute()
{
  mP_object->set_illness(calculate_illness());
}  

////////////////////////////////////////////////////////////////////////////
//            PSBWS_IllnessChanger_Type1
////////////////////////////////////////////////////////////////////////////

PSBWS_IllnessChanger_Type1::PSBWS_IllnessChanger_Type1(PSBWS_AttributeChanger* aP_next)
  :PSBWS_IllnessChanger(aP_next)
{
}

////////////////////////////////////////////////////////////////////////////

PSBWS_IllnessChanger_Type1::~PSBWS_IllnessChanger_Type1(){
}

////////////////////////////////////////////////////////////////////////////

TPositive PSBWS_IllnessChanger_Type1::calculate_illness()
{
  TPositive lu_result;
  return lu_result;
}

////////////////////////////////////////////////////////////////////////////
//            PSBWS_AttributeCalculator
////////////////////////////////////////////////////////////////////////////

PSBWS_AttributeCalculator::PSBWS_AttributeCalculator(PSBWS_AttributeChanger* aP_next)
  :PSBWS_AttributeChanger(aP_next)
{
    mSM_attribute_calculator_functions = new SM_HashTabelle<TPositive>(10);
}

////////////////////////////////////////////////////////////////////////////

PSBWS_AttributeCalculator::~PSBWS_AttributeCalculator()
{
  delete  mSM_attribute_calculator_functions;
}

////////////////////////////////////////////////////////////////////////////

void PSBWS_AttributeCalculator::change_attribute()
{
  mSTRa_matrix = mP_object->get_attribute_calculator_matrix();
  mua_attributes = mP_object->get_attribute_array();
  bool lb_repeat_calculation;
  
  do
  {
    lb_repeat_calculation = calculate_attributes();
  }
  while  (lb_repeat_calculation);
}

////////////////////////////////////////////////////////////////////////////

bool PSBWS_AttributeCalculator::calculate_attributes()
{
  bool lb_return;

  unsigned short int lu_i, lu_j, lu_matrix_index;

  TPositive lu_a_j;
  TPositive lu_function_nr;
  int li_offset;
  float lf_share;
  
  float lfa_attributes_new[12];
  float lf_change;

  for (lu_j = 0; lu_j < 12; lu_j++)
  {
    lu_a_j = mua_attributes[lu_j];
    lu_matrix_index = lu_a_j * 12;
    for (lu_i = 0; lu_i < 12; lu_i++)
    {
      lu_function_nr = mSTRa_matrix[lu_matrix_index].u_function;
      lf_share = mSTRa_matrix[lu_matrix_index].f_share;
      li_offset = mSTRa_matrix[lu_matrix_index].i_offset;
      
      lf_change = lf_share * get_function_value(lu_function_nr, li_offset, lu_i, lu_j, lu_a_j) ;
      if (lf_change != 0) lfa_attributes_new[lu_i] += lf_change;
      
      lu_matrix_index++;
    }
  }
 
  lb_return = check_epsilon(lfa_attributes_new);

  for (lu_j = 0; lu_j < 12; lu_j++)
  {
    mua_attributes[lu_j] = (TPositive) (lfa_attributes_new[lu_j] + 0.5);
  }

  return lb_return;
}

////////////////////////////////////////////////////////////////////////////

bool PSBWS_AttributeCalculator::check_epsilon(float* afa_attributes_new)
{
  unsigned short int lu_i = 0;

  while
     ( (afa_attributes_new[lu_i] + 1) > (float) mua_attributes[lu_i] ||
       (afa_attributes_new[lu_i] - 1) < (float) mua_attributes[lu_i] )
  {
    lu_i++;
    if (lu_i > 12) return false;
  }
  return true;
}

////////////////////////////////////////////////////////////////////////////

TPositive PSBWS_AttributeCalculator::get_function_value(
       TPositive au_function_nr, int ai_offset,
       unsigned short int au_i, unsigned short int au_j, TPositive au_attrib_value)
{
   int li_result;
   
   switch (au_function_nr)
   {
        case 1:  // f(au_attrib_value) :=  au_attrib_value
            li_result = au_attrib_value + ai_offset; break;
        case 22222:  // example for table access
            li_result =  get_table_value(au_i, au_j, au_attrib_value) + ai_offset; break;  
   }
   if (li_result < 0) return 0;
   if (li_result > 100) return 100;
   return (TPositive) li_result;
}

////////////////////////////////////////////////////////////////////////////

TPositive PSBWS_AttributeCalculator::get_table_value(
       unsigned short int au_i, unsigned short int au_j, TPositive au_attrib_value)
{
    unsigned int lu_table_nr;
    TPositive* lu_table;
    
    lu_table_nr = au_i * 12 + au_j;

    lu_table = mSM_attribute_calculator_functions->getElement(lu_table_nr);
    return lu_table[au_attrib_value];
}
