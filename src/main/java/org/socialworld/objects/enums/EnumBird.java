/*
* Social World
* Copyright (C) 2023  Mathias Sikos
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
package org.socialworld.objects.enums;


public enum EnumBird {
	
	ignore("ignore"),
	Aequorlithornithes("org.socialworld.objects.concrete.animals.birds.Aequorlithornithes"),
	Columbaves("org.socialworld.objects.concrete.animals.birds.Columbaves"),
	Coraciimorphae("org.socialworld.objects.concrete.animals.birds.Coraciimorphae"),

	Galloanserae("org.socialworld.objects.concrete.animals.birds.Galloanserae"),
	Palaeognathae("org.socialworld.objects.concrete.animals.birds.Palaeognathae"),
	Passeriformes("org.socialworld.objects.concrete.animals.birds.Passeriformes"),
	Raptor("org.socialworld.objects.concrete.animals.birds.Raptor"),
	Strisores("org.socialworld.objects.concrete.animals.birds.Strisores"),
	
	/* Aequorlithornithes */
	
	Stork("org.socialworld.objects.concrete.animals.birds.aequorlithornithes.Stork"),

	/* Columbaves */
	
	Columbimorphae("org.socialworld.objects.concrete.animals.birds.columbaves.Columbimorphae"),
	Otidimorphae("org.socialworld.objects.concrete.animals.birds.columbaves.Otidimorphae"),

	/* Coraciimorphae */

	Psittaciformes("org.socialworld.objects.concrete.animals.birds.coraciimorphae.Psittaciformes"),

	/* Galloanserae */

	Gruiformes("org.socialworld.objects.concrete.animals.birds.galloanserae.Gruiformes"),

	/* Palaeognathae */

	Ostrich("org.socialworld.objects.concrete.animals.birds.palaeognathae.Ostrich"),

	/* Passeriformes */

	Oscines("org.socialworld.objects.concrete.animals.birds.passeriformes.Oscines"),
	Suboscines("org.socialworld.objects.concrete.animals.birds.passeriformes.Suboscines"),

	/* Raptor */

	Accipitriformes("org.socialworld.objects.concrete.animals.birds.raptors.Accipitriformes"),
	Falconiformes("org.socialworld.objects.concrete.animals.birds.raptors.Falconiformes"),
	Strigiformes("org.socialworld.objects.concrete.animals.birds.raptors.Strigiformes");

	private String className;

	private EnumBird(String className) {
		this.className = className;
	}
	
	/**
	 * The method returns the enum name which belongs to the parameter className
	 * 
	 * @param className
	 * @return EnumBird name
	 */
	public static EnumBird getName(String className) {
		for (EnumBird value : EnumBird.values())
			if (value.className == className)
				return value;
		return ignore;
	}
}
