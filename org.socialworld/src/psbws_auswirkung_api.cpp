/***************************************************************************
                          psbws_auswirkung_api.cpp  -  description
                             -------------------
    begin                : Tue Jul 29 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#include "psbws_auswirkung_api.h"

PSBWS_Effect_API::PSBWS_Effect_API()
{
}

PSBWS_Effect_API::~PSBWS_Effect_API()
{
}


/////////////////////////////////////////////////////////////////////////////

void PSBWS_Effect_API::calculate(PSBWS_SimulationObject* pObjekt, TUChar uTyp)
{
		switch(uTyp)
		{
				case 1: calculate_type_1(pObjekt); break;
				case 2: calculate_type_2(pObjekt); break;
				case 3: calculate_type_3(pObjekt); break;
				case 4: calculate_type_4(pObjekt); break;
				case 5: calculate_type_5(pObjekt); break;
				case 6: calculate_type_6(pObjekt); break;
				case 7: calculate_type_7(pObjekt); break;
				case 8: calculate_type_8(pObjekt); break;
				case 9: calculate_type_9(pObjekt); break;
				case 10: calculate_type_10(pObjekt); break;
				case 11: calculate_type_11(pObjekt); break;
				case 12: calculate_type_12(pObjekt); break;
    }
}
