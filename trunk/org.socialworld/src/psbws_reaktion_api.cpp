/***************************************************************************
                          psbws_reaktion_api.cpp  -  description
                             -------------------
    begin                : Tue Jul 29 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#include "psbws_reaktion_api.h"


PSBWS_Reaction_API::PSBWS_Reaction_API()
{
}
PSBWS_Reaction_API::~PSBWS_Reaction_API()
{
}

/////////////////////////////////////////////////////////////////////////////

void PSBWS_Reaction_API::calculate(PSBWS_SimulationObject* aP_object, TUChar au_type)
{
    mP_actioner = aP_object->get_actioner();
    mP_actionCoder = mP_actioner->get_actionCoder();
    
    
    switch(au_type)
		{
				case 1: calculate_type_1(aP_object); break;
				case 2: calculate_type_2(aP_object); break;
				case 3: calculate_type_3(aP_object); break;
				case 4: calculate_type_4(aP_object); break;
				case 5: calculate_type_5(aP_object); break;
				case 6: calculate_type_6(aP_object); break;
				case 7: calculate_type_7(aP_object); break;
				case 8: calculate_type_8(aP_object); break;
				case 9: calculate_type_9(aP_object); break;
				case 10: calculate_type_10(aP_object); break;
				case 11: calculate_type_11(aP_object); break;
				case 12: calculate_type_12(aP_object); break;
    }
}
