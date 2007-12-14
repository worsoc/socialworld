/***************************************************************************
                          sm_vektormathematik.cpp  -  description
                             -------------------
    begin                : Sun May 18 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#include "sm_vektormathematik.h"
#include <math.h>

SM_VektorMathematik::SM_VektorMathematik()
{
}
SM_VektorMathematik::~SM_VektorMathematik()
{
}

double SM_VektorMathematik::vectorLength(double x, double y, double z)
{
//	unsigned long uLaenge;
	double dXQuad = x * x;										  // Wurzel aus ( x^2+y^2+z^2)
	double dYQuad = y * y;
	double dZQuad = z * z;
	double dLaenge = dXQuad + dYQuad + dZQuad;
	dLaenge = sqrt(dLaenge);
//	uLaenge = floor(dLaenge);										  // rundet ggz, die <= (abrunden)  in Math.h
//	TNatural uErgebnis =  uLaenge;
//	return(uErgebnis);
  return dLaenge;
}

double SM_VektorMathematik::angleTangens(double x1,double y1,double z1,double x2, double y2, double z2)
{
    double dDivident = vectorLength(x1,y1,z1) - vectorLength(x2,y2,z2);
    double dDivisor = vectorLength(x1-x2 , y1-y2 , z1-z2);
    if (dDivisor == 0) return 0;
    return (dDivident/dDivisor);
}