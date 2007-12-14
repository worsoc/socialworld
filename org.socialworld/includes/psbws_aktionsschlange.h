/***************************************************************************
                          psbws_aktionsschlange.h  -  description
                             -------------------
    begin                : Sun Jun 22 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/


#ifndef PSBWS_AKTIONSSCHLANGE_H
#define PSBWS_AKTIONSSCHLANGE_H


/**
  *@author Mathias Sikos
  */
#include "psbws_types.h"
#include "sm_kettemitkopf.cpp"
#include "sm_indexliste.cpp"
#include "sm_prioritaetsschlange.cpp"
#include "sm_listenelement.cpp"

class PSBWS_Object_Manager;

struct STR_TimeQueue;

typedef struct STR_TimeChainElement
{
	TNatural u_time;
	struct STR_TimeQueue* STR_timeQueue;
} STR_TimeChainElement;

typedef struct STR_TimeQueue
{
	SM_PrioritaetsSchlange<STR_ActionElement>* SM_priorityQueue;
	TNatural u_time;
	SM_ListenElement<STR_TimeChainElement>* SM_time_in_chain;
} STR_TimeQueue;

class PSBWS_ActionQueue {
public:
	PSBWS_ActionQueue(PSBWS_Object_Manager*, TPositive);
	~PSBWS_ActionQueue();

	int insert(TNatural, TUChar, STR_ActionElement*);
	STR_ActionElement* get_element();

protected:
	SM_KetteMitKopf<STR_TimeChainElement>* 	mSM_timeChain;
  SM_IndexListe<STR_TimeQueue>*			      mSM_priorityQueues;
  PSBWS_Object_Manager*						        mP_objectManager;
};

/*
typedef struct T_ZeitpunktSchlange
{
	SM_PrioritaetsSchlange<STR_ActionElement>* SM_priorityQueue;
	TNatural u_time;
	SM_ListenElement<TZeitpunktKettenElement>* SM_time_in_chain;
} TZeitpunktSchlange;
*/
#endif
