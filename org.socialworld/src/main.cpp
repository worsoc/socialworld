/***************************************************************************
                          main.cpp  -  description
                             -------------------
    begin                : Die Mai 13 21:16:21 CEST 2003
    copyright            : (C) 2003 by Mathias Sikos
    email                : sikosmathias@aol.com
 ***************************************************************************/

#include <stdlib.h>
#include <stdio.h>
#include "psbws_objekt_manager.h"


// zum Testen
#include "psbws_simulation.h"
#include "psbws_gott.h"

int main(int argc, char *argv[])
{

    PSBWS_Object_Manager* lP_objects = new PSBWS_Object_Manager();

    int li_time = lP_objects->get_god()->get_time();
		printf("aktuelle Zeit: %d \n",li_time);
    lP_objects->get_simulation()->start_simulation();
		return 0;
}


