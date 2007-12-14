/***************************************************************************
                          sm_keystring.h  -  description
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

#ifndef SM_KEYSTRING_H
#define SM_KEYSTRING_H

//#include <qstring.h>

/**
  *@author Mathias Sikos
  */

class SM_KeyString
{
public: 
	SM_KeyString		(char key);			//(const QString& key);
	~SM_KeyString();

  bool 			isLessThan	(char operand2);//(const QString& operand2);
  bool 			isEqual			(char operand2);
	char 			getValue		(); 	
protected:
  char 			m_Key;
};

#endif

