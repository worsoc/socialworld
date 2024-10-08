/*
* Social World
* Copyright (C) 2024  Mathias Sikos
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.  
*
* or see http://www.gnu.org/licenses/gpl-2.0.html
*
*/
package org.socialworld.calculation;

import org.socialworld.core.IAccessToken;

public interface IObjectSender {
	public static final int NO_OBJECT_SENDER = -1;
	public static final int OBJECT_SENDED = 0;
	public static final int OBJECT_NOT_SENDED = 1;
	public static final int OBJECT_NOT_SENDED_BECAUSE_PROTECTED = 2;
	public static final int OBJECT_NOT_SENDED_BECAUSE_NO_PERMISSION = 3;
	public static final int OBJECT_NOT_SENDED_BECAUSE_WRONG_TYPE = 4;
	
	public int sendYourselfTo(IObjectReceiver receiver, int requestID);
	public int sendYourselfTo(IAccessToken token, IObjectReceiver receiver, int requestID);

	public IObjectSender copy();

}
