/***************************************************************************
                          psbws_event.cpp  -  description
                             -------------------
    begin                : Tue Jul 29 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/
#ifndef PSBWS_EVENT_CPP
#define PSBWS_EVENT_CPP


#include "psbws_event.h"

PSBWS_Event::PSBWS_Event()
{
}

PSBWS_Event::~PSBWS_Event()
{
}

TUChar PSBWS_Event::get_type()
{
	return mu_eventCode;
}

#endif
