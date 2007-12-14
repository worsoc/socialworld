/***************************************************************************
                          psbws_aktionscode.h  -  description
                             -------------------
    begin                : Sun May 18 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#ifndef PSBWS_AKTIONSCODE_H
#define PSBWS_AKTIONSCODE_H


/**
  *@author Mathias Sikos
  */

#include "psbws_types.h"

class PSBWS_ActionCoder {
public:
  PSBWS_ActionCoder();
  ~PSBWS_ActionCoder();
 
};


class PSBWS_ActionCoder_Animal : public PSBWS_ActionCoder {
public:
  PSBWS_ActionCoder_Animal();
  ~PSBWS_ActionCoder_Animal();
};


class PSBWS_ActionCoder_Mammal : public PSBWS_ActionCoder_Animal {
public:
  PSBWS_ActionCoder_Mammal();
  ~PSBWS_ActionCoder_Mammal();
};

class PSBWS_ActionCoder_Reptile : public PSBWS_ActionCoder_Animal {
public:
  PSBWS_ActionCoder_Reptile();
  ~PSBWS_ActionCoder_Reptile();
};

class PSBWS_ActionCoder_Bird : public PSBWS_ActionCoder_Animal {
public:
  PSBWS_ActionCoder_Bird();
  ~PSBWS_ActionCoder_Bird();
};

class PSBWS_ActionCoder_Fish : public PSBWS_ActionCoder_Animal {
public:
  PSBWS_ActionCoder_Fish();
  ~PSBWS_ActionCoder_Fish();
};

class PSBWS_ActionCoder_Human : public PSBWS_ActionCoder_Mammal {
public: 
	PSBWS_ActionCoder_Human();
	~PSBWS_ActionCoder_Human();

  TNatural calculate_actionCode(TUChar, long int i = 0, int i = 0,
												int i = 0, int i = 0, int i = 0);

	TNatural calculate_action31(TPNR, int, int);
	TNatural calculate_action30(TGNR,	int);
	TNatural calculate_action29(unsigned long int, int, int);
	TNatural calculate_action28(int, int, int);
	TNatural calculate_action27(int, int, int, int, int);
	TNatural calculate_action26(int, int, int, int, int);
	TNatural calculate_action25(TNatural, int);
	TNatural calculate_action24(TPNR, long int);
	TNatural calculate_action23( int,  int,  int, int);

};


#endif
