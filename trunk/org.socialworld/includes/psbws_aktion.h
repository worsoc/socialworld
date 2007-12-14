/***************************************************************************
                          psbws_aktion.h  -  description
                             -------------------
    begin                : Thu May 15 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#ifndef PSBWS_AKTION_H
#define PSBWS_AKTION_H


/**
  *@author Mathias Sikos
  */

#include "psbws_types.h"

class PSBWS_ActionQueue;
class PSBWS_ActionCoder;
class PSBWS_Human;
class PSBWS_Objekt_Manager;

/****************************************************************************
*           CLASS DECLARATION - PSBWS_Actioner                              *
****************************************************************************/
class PSBWS_Actioner
{
public:

/************* CONSTRUCTOR / DESTRUCTOR ************************************/
	PSBWS_Actioner(PSBWS_Object_Manager*, TONR, PSBWS_ActionCoder*
      aP_actionCoder = 0);
  virtual ~PSBWS_Actioner();

/************* ACTION HANDLING *********************************************/
  virtual void do_action() = 0;
  void do_actualAction();
  void insert_action(STR_ActionElement*);	
  void new_action(TNatural, TNatural, TUChar, unsigned short, TNatural);
  void change_action(STR_ActionElement*, TNatural,TNatural,
                     TUChar, unsigned short, TNatural);
  PSBWS_ActionCoder* get_actionCoder();
  
protected:

/************* INITIALIZATION *********************************************/
  PSBWS_ActionQueue*  mP_actions;
  TONR mu_ONr;
  PSBWS_Object_Manager* mP_objectManager;
  STR_ActionElement*  mT_actualAction;
  PSBWS_ActionCoder* mP_actionCoder;
};


/****************************************************************************
*           CLASS DECLARATION - PSBWS_Actioner_Animal                       *
****************************************************************************/
class PSBWS_Actioner_Animal : public PSBWS_Actioner
{
public:

/************* CONSTRUCTOR / DESTRUCTOR ************************************/
	PSBWS_Actioner_Animal(PSBWS_Object_Manager*, TONR,
    PSBWS_ActionCoder* aP_actionCoder = 0);
  virtual ~PSBWS_Actioner_Animal();

/************* ACTION HANDLING *********************************************/
  void do_action() {};
};


/****************************************************************************
*           CLASS DECLARATION - PSBWS_Actioner_Mammal                       *
****************************************************************************/
class PSBWS_Actioner_Mammal : public PSBWS_Actioner_Animal
{
public:

/************* CONSTRUCTOR / DESTRUCTOR ************************************/
	PSBWS_Actioner_Mammal(PSBWS_Object_Manager*, TONR,
    PSBWS_ActionCoder* aP_actionCoder = 0);
  virtual ~PSBWS_Actioner_Mammal();

/************* ACTION HANDLING *********************************************/
  void do_action() {};
};


/****************************************************************************
*           CLASS DECLARATION - PSBWS_Actioner_Reptile                      *
****************************************************************************/
class PSBWS_Actioner_Reptile : public PSBWS_Actioner_Animal
{
public:

/************* CONSTRUCTOR / DESTRUCTOR ************************************/
	PSBWS_Actioner_Reptile(PSBWS_Object_Manager*, TONR,
    PSBWS_ActionCoder* aP_actionCoder = 0);
  virtual ~PSBWS_Actioner_Reptile();

/************* ACTION HANDLING *********************************************/
  void do_action() {};
};


/****************************************************************************
*           CLASS DECLARATION - PSBWS_Actioner_Bird                         *
****************************************************************************/
class PSBWS_Actioner_Bird : public PSBWS_Actioner_Animal
{
public:

/************* CONSTRUCTOR / DESTRUCTOR ************************************/
PSBWS_Actioner_Bird(PSBWS_Object_Manager* , TONR,
    PSBWS_ActionCoder* aP_actionCoder = 0);
  virtual ~PSBWS_Actioner_Bird();

/************* ACTION HANDLING *********************************************/
  void do_action() {};
};


/****************************************************************************
*           CLASS DECLARATION - PSBWS_Actioner_Fish                         *
****************************************************************************/
class PSBWS_Actioner_Fish : public PSBWS_Actioner_Animal
{
public:

/************* CONSTRUCTOR / DESTRUCTOR ************************************/
  PSBWS_Actioner_Fish(PSBWS_Object_Manager* , TONR,
    PSBWS_ActionCoder* aP_actionCoder = 0);
  virtual ~PSBWS_Actioner_Fish();

/************* ACTION HANDLING *********************************************/
  void do_action() {};
};


/****************************************************************************
*           CLASS DECLARATION - PSBWS_Actioner_Human                        *
****************************************************************************/
class PSBWS_Actioner_Human : public PSBWS_Actioner_Mammal
{
public: 

/************* CONSTRUCTOR / DESTRUCTOR ************************************/
	PSBWS_Actioner_Human(PSBWS_Object_Manager* pObjekte, TONR uONr,
    PSBWS_ActionCoder* pAktionsCode = 0, TPNR uPersnr = 0);
  virtual ~PSBWS_Actioner_Human();

/************* ACTION HANDLING *********************************************/
	void do_action();

private:

/************* INITIALIZATION **********************************************/
  TPNR mu_PNr;
	PSBWS_Human* mP_person;

/************* KINDS OF ACTION *********************************************/
  void move(TNatural);
	void use_fastItem(TNatural);
	void sleep(TUChar);
	void kick(TNatural);
	void control_hand_manually(TNatural);
	void touch(TNatural);
	void say(TNatural);
	void spell(TNatural);
	void use_weapon(TUChar uRechts, TNatural uCode);
	void use_item(TNatural);
	void change_moveType(TNatural);	
};

#endif
