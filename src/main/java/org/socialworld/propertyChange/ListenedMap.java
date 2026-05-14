/*
* Social World
* Copyright (C) 2014  Mathias Sikos
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
package org.socialworld.propertyChange;

import org.eclipse.collections.api.map.primitive.MutableIntObjectMap;
import org.eclipse.collections.impl.factory.primitive.IntObjectMaps;
 
public class ListenedMap<E> extends ListenedBase  {

	public static final String KEY_LIST_CHANGE_PROPERTY = "list_change_property";

	private final MutableIntObjectMap<E> map = 
		IntObjectMaps.mutable.<E>empty().asSynchronized();

	/**
	 * Fügt ein Element mit einer spezifischen primitiven ID hinzu.
	 * Ersetzt das alte add(index, element).
	 */
	public void put(int id, E element) {
		map.put(id, element);
		firePropertyChange(KEY_LIST_CHANGE_PROPERTY, null, map);
	}

	/**
	 * Aktualisiert oder setzt ein Element an einer ID.
	 * Ersetzt das alte set(index, element).
	 */
	public E set(int id, E element) {
		E old = map.get(id);
		map.put(id, element);
		firePropertyChange(KEY_LIST_CHANGE_PROPERTY, null, map);
		return old;
	}

	/**
	 * Holt ein Element direkt über seine primitive ID (O(1)-Zeit).
	 */
	public E get(int id) {
		return map.get(id);
	}

	/**
	 * Entfernt ein Element über seine primitive ID.
	 */
	public E remove(int id) {
		E obj = map.remove(id);
		if (obj != null) {
			firePropertyChange(KEY_LIST_CHANGE_PROPERTY, null, map);
		}
		return obj;
	}

	/**
	 * Leert die gesamte Map sperrenfrei.
	 */
	public void clear() {
		map.clear();
		firePropertyChange(KEY_LIST_CHANGE_PROPERTY, null, map);
	}

	/**
	 * Prüft, ob eine bestimmte primitive ID existiert (Sehr schnell).
	 */
	public boolean containsKey(int id) {
		return map.containsKey(id);
	}

	/**
	 * Prüft, ob ein bestimmtes Objekt als Value existiert.
	 */
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	/**
	 * Gibt den Füllgrad der Map zurück.
	 */
	public int size() {
		return map.size();
	}

	/**
	 * Prüft auf Leere.
	 */
	public boolean isEmpty() {
		return map.isEmpty();
	}
	
	/**
	 * Bietet eine allokationsfreie Iterationsmöglichkeit für die Engine-Pipeline,
	 * um Lambda-Ausdrücke ohne GC-Druck auszuführen.
	 */
	public void forEach(org.eclipse.collections.api.block.procedure.primitive.IntObjectProcedure<? super E> procedure) {
		map.forEachKeyValue(procedure);
	}

	@Override
	public String toString() {
		return map.toString();
	}
}
