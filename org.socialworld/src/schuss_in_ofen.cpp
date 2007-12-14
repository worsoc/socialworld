/***************************************************************************
                          schuss_in_ofen.cpp  -  description
                             -------------------
    begin                : Son Mär 4 2007
    copyright            : (C) 2007 by Mathias Sikos
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

 ////////////////////////////////////////////////////////////////////////////

 /*
float  PSBWS_AttributeCalculator::evaluate_matrix_element(
        STR_Term_AttributeCalculator astr_matrix_term,
        float af_attribute_target, float af_attribute_source)
{
  bool lb_true_term = false;

  switch (astr_matrix_term.u_functor)
  {
     case 0: // if-expression
          switch (astr_matrix_term.STR_if_expression->u_operation)
          {
             case 11:  // target not equal to constant
                  if (af_attribute_target != astr_matrix_term.f_constant)
                      lb_true_term  = true;   break;
             case 12:  // target equal to constant
                  if (af_attribute_target == astr_matrix_term.f_constant)
                      lb_true_term  = true;   break;
             case 13:  // target less equal than constant
                  if (af_attribute_target <= astr_matrix_term.f_constant)
                      lb_true_term  = true;   break;
             case 14:  // target less than constant
                  if (af_attribute_target < astr_matrix_term.f_constant)
                      lb_true_term  = true;   break;
             case 15:  // target greater than constant
                  if (af_attribute_target > astr_matrix_term.f_constant)
                      lb_true_term  = true;   break;
             case 16:  // target greater equal than constant
                  if (af_attribute_target >= astr_matrix_term.f_constant)
                      lb_true_term  = true;   break;
             case 21:  // target not equal to source
                  if (af_attribute_target != af_attribute_source)
                      lb_true_term  = true;   break;
             case 22:  // target equal to source
                  if (af_attribute_target == af_attribute_source)
                      lb_true_term  = true;   break;
             case 23:  // target less equal than source
                  if (af_attribute_target <= af_attribute_source)
                      lb_true_term  = true;   break;
             case 24:  // target less than source
                  if (af_attribute_target < af_attribute_source)
                      lb_true_term  = true;   break;
             case 25:  // target greater than source
                  if (af_attribute_target > af_attribute_source)
                      lb_true_term  = true;   break;
             case 26:  // target greater equal than source
                  if (af_attribute_target >= af_attribute_source)
                      lb_true_term  = true;   break;
             case 31:  // source not equal to constant
                  if (af_attribute_source != astr_matrix_term.f_constant)
                      lb_true_term  = true;   break;
             case 32:  // source equal to constant
                  if (af_attribute_source == astr_matrix_term.f_constant)
                      lb_true_term  = true;   break;
             case 33:  // source less equal than constant
                  if (af_attribute_source >= astr_matrix_term.f_constant)
                      lb_true_term  = true;   break;
             case 34:  // source less than constant
                  if (af_attribute_source < astr_matrix_term.f_constant)
                      lb_true_term  = true;   break;
             case 35:  // source greater than constant
                  if (af_attribute_source > astr_matrix_term.f_constant)
                      lb_true_term  = true;   break;
             case 36:  // source greater equal than constant
                  if (af_attribute_source >= astr_matrix_term.f_constant)
                      lb_true_term  = true;   break;         }
         if (lb_true_term)
                return evaluate_matrix_element(
                          astr_matrix_term.STR_if_expression->STR_term_true[0] ,
                          af_attribute_target, af_attribute_source);
         else
                return evaluate_matrix_element(
                          astr_matrix_term.STR_if_expression->STR_term_false[0] ,
                          af_attribute_target, af_attribute_source);

     case 1: // addition
          return af_attribute_target + astr_matrix_term.f_constant;
     case 2: // multiplication
          return af_attribute_target * astr_matrix_term.f_constant;
  }
}
 */