/***************************************************************************
                          sm_keyint.h  -  description
                             -------------------
    begin                : Thu May 29 2003
    copyright            : (C) 2003 by Mathias Sikos
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

#ifndef SM_KEYINT_H
#define SM_KEYINT_H

/**
  *@author Mathias Sikos
  */

class SM_KeyInt
{
public: 
	SM_KeyInt							(int key);
	~SM_KeyInt();

  bool 			isLessThan	(int operand2);
  bool      isEqual			(int operand2);
	int 			getValue		(); 	
protected:
  int 			m_Key;
};

#endif
