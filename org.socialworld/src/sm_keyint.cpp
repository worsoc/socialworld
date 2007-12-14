/***************************************************************************
                          sm_keyint.cpp  -  description
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

#include "sm_keyint.h"

SM_KeyInt::SM_KeyInt(int key)
{
		m_Key = key;
}

SM_KeyInt::~SM_KeyInt()
{
}

bool 	SM_KeyInt::isLessThan(int operand2)
{
			return (m_Key < operand2);
}

bool  SM_KeyInt::isEqual(int operand2)
{
			return (m_Key == operand2);
}

int 	SM_KeyInt::getValue()
{
			return m_Key;
}
