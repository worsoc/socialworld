/***************************************************************************
                          sm_keystring.cpp  -  description
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

#include "sm_keystring.h"

SM_KeyString::SM_KeyString(char key)// QString& key)
{
    	m_Key = key;
}

SM_KeyString::~SM_KeyString()
{
}

bool 	SM_KeyString::isLessThan(char operand2) // const QString& operand2)
{
			return (m_Key < operand2);
}

bool 	SM_KeyString::isEqual(char operand2) // const QString& operand2)
{
			return (m_Key == operand2);
}

char 	SM_KeyString::getValue()
{
			return m_Key;
}
