/***************************************************************************
                          sm_vektormathematik.h  -  description
                             -------------------
    begin                : Sun May 18 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#ifndef SM_VEKTORMATHEMATIK_H
#define SM_VEKTORMATHEMATIK_H


/**
  *@author Mathias Sikos
  */

#include "psbws_types.h"

class SM_VektorMathematik {
public: 
	SM_VektorMathematik();
	~SM_VektorMathematik();

	double vectorLength(double x, double y, double z);	  // Berechnet Betrag des Vektors (x,y,z)
  double angleTangens(double x,double y,double z,double m_fEvDirX, double m_fEvDirY, double m_fEvDirZ) ;
};

#endif
