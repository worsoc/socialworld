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
package org.socialworld.calculation.geometry;

import org.socialworld.calculation.IObjectReceiver;
import org.socialworld.calculation.IObjectSender;
import org.socialworld.core.IAccessToken;

/**
 * The class is the base for all simulation
 *         position and directions. The vector has 3 dimensions.
 * @author Mathias Sikos (MatWorsoc) 
 */
public class Vector implements IObjectSender{

	// Echte, unantastbare Singletons im System (isMutable = false)
    private static final Vector objectNothing = new Vector(0, 0, 0, false);
    private static final Vector VECTOR_0 = new Vector(0, 0, 0, false);
	
	public static Vector getObjectNothing() {
		return objectNothing;
	}
	
	public boolean checkIsObjectNothing() {
		return (this == objectNothing);
	}
	
	protected float x = 0;
	protected float y = 0;
	protected float z = 0;

	boolean normalized = false;
	
    private boolean isMutable = false; 
	
  
    // 1. Privater Hauptkonstruktor, der die Veränderbarkeit explizit setzt
    private Vector(float x, float y, float z, boolean isMutable) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.isMutable = isMutable;
    }

    // 2. Der parameterlose Konstruktor: Offen für Modifikationen (mutable = true)
    // Er sagt: "Ich bin leer und warte darauf, befüllt zu werden."
    public Vector() {
        this(0, 0, 0, true);
    }

    public Vector(float x, float y, float z) {
        this(x, y, z, false);
    }

    public Vector(Vector original) {
        this(
            original != null ? original.getX() : 0,
            original != null ? original.getY() : 0,
            original != null ? original.getZ() : 0,
            true // Kopien sind veränderbar (das ist der Grund ihrer Erzeugung)
        );
        if (original != null) {
            this.normalized = original.isNormalized();
        }
    }


    public Vector(float tripel[]) {
        this(tripel[0], tripel[1], tripel[2], false);
    }
	
	public Vector(String vector) {
		
		String tmp;
		String values[];
		
		tmp = vector;
		tmp = tmp.replace(" ", "");
		tmp = tmp.substring(1);
		tmp = tmp.replace(")", "");
		
		values = tmp.split(",");
		
		if (values.length == 3) {
			this.x = Float.parseFloat(values[0]);
			this.y = Float.parseFloat(values[1]);
			this.z = Float.parseFloat(values[2]);
		}
        this.isMutable = false;
	}
	
	public void set(Vector original) {
		// 1. Barriere: Das globale "Nichts" darf nicht überschrieben werden
		if (this == objectNothing) return;

		// 2. Wächter: Nur im Rechen-Labor (Mutable) erlaubt
		if (!this.isMutable) {
			throw new UnsupportedOperationException("Set auf unveränderlichem Vektor nicht erlaubt.");
		}

		if (original != null) {
			this.x = original.getX();
			this.y = original.getY();
			this.z = original.getZ();
			this.normalized = original.isNormalized();
		}
	}
	
    /**
     * Fabrikmethode für einen offenen, veränderbaren Arbeitscontainer (0,0,0).
     * Perfekt für ThreadLocal- oder Wiederverwendungs-Strukturen.
     */
    public static Vector getMutable() {
        return new Vector(0, 0, 0, true);
    }
    
	// Die Fabrikmethode für das Rechen-Labor (Mutable)
    public static Vector getMutable(float x, float y, float z) {
        return new Vector(x, y, z, true);
    }

	public static Vector get0Vector() {
		 return VECTOR_0; 
	}
	
	public void normalize() {
	     // Barriere: Das Nichts darf nicht normalisiert werden
        if (this == objectNothing) return; 

        if (!isMutable) {
            throw new UnsupportedOperationException("Normalisierung auf unveränderlichem Vektor nicht erlaubt.");
        }

        float length = length();
	    
	    // Defensiver Schutz gegen Division durch 0 
	    if (length > 0.00001f) {
	        this.x = this.x / length;
	        this.y = this.y / length;
	        this.z = this.z / length;
	    } else {
	        // Ein Nullvektor bleibt ein sauberer Nullvektor und ist mathematisch "fertig"
	        this.x = 0.0f;
	        this.y = 0.0f;
	        this.z = 0.0f;
	    }
        this.normalized = true; 

	}	
	
    public boolean isMutable() {
        return this.isMutable;
    }

    // 4. Die Wächter-Methode für In-Place-Änderungen
    public void setValues(float x, float y, float z) {
        if (!isMutable) {
            throw new UnsupportedOperationException("Dieser Vektor ist unveränderlich!");
        }
        this.x = x;
        this.y = y;
        this.z = z;
        this.normalized = false;
    }

	public boolean isNormalized() {
		return normalized;
	}

	/**
	 * @return the x
	 */
	public float getX() {
		return x;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(float x) {
       if (!isMutable) throw new UnsupportedOperationException("Vektor ist unmodifizierbar.");
       this.x = x;
		normalized = false;
	}

	/**
	 * @return the y
	 */
	public float getY() {
		return y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(float y) {
       if (!isMutable) throw new UnsupportedOperationException("Vektor ist unmodifizierbar.");
		this.y = y;
		normalized = false;
	}

	/**
	 * @return the z
	 */
	public float getZ() {
		return z;
	}

	/**
	 * @param z
	 *            the z to set
	 */
	public void setZ(float z) {
       if (!isMutable) throw new UnsupportedOperationException("Vektor ist unmodifizierbar.");
		this.z = z;
		normalized = false;
	}

	/**
	 * The method calculates the vector's length.
	 * 
	 * @return the length
	 */
	public float length() {
		if 	(	normalized) return 1.0F;

		
		float length;

		float xQuad = x * x;
		float yQuad = y * y;
		float zQuad = z * z;

		length = (float) Math.sqrt(xQuad + yQuad + zQuad);

		return length;
	}

	/**
	 * The method calculates the subtraction of two vectors. The result vector
	 * has the direction from parameter vector to this vector.
	 * 
	 * @param position
	 * @return the direction
	 */
	public Vector getDirectionFrom(Vector vector) {
		float deltaX;
		float deltaY;
		float deltaZ;

		Vector direction;

		deltaX = this.x - vector.getX();
		deltaY = this.y - vector.getY();
		deltaZ = this.z - vector.getZ();

		direction = new Vector(deltaX, deltaY, deltaZ);
		return direction;
	}

	
	/**
	 * The method calculates the addition by a second vector.
	 * 
	 * @param xyz
	 */
	public void add (Vector xyz) {
	    if (this == objectNothing) return; 
	    
       if (this.isMutable) {
            this.x += xyz.getX();
            this.y += xyz.getY();
            this.z += xyz.getZ();
            this.normalized = false;
        } else {
            // Aus Sicherheitsgründen im Alt-Code abgefangen: Wenn jemand versucht, 
            // einen immutable Vektor zu manipulieren, verweigern wir die In-Place-Änderung.
            throw new UnsupportedOperationException("Direkte Addition auf unmodifizierbarem Vektor verboten.");
        }
	}
	
	/**
	 * The method calculates the scalar multiplication.
	 * 
	 * @param scalar
	 */
	public void mul (float scalar) {
		// Barriere: Das "Nichts" multipliziert mit irgendetwas bleibt "Nichts"
		if (this == objectNothing) return;

		if (this.isMutable) {
			this.x = this.x * scalar;
			this.y = this.y * scalar;
			this.z = this.z * scalar;
			this.normalized = false;
		} else {
			// Schutz vor unerlaubter Modifikation auf unveränderlichen Instanzen
			throw new UnsupportedOperationException("Direkte Multiplikation auf unmodifizierbarem Vektor verboten.");
		}
	}
	
	/**
	 * The method calculates the angle's cosine.
	 * The formula is: cos = a * b   /   |a| * |b|
	 * 
	 * 
	 * @param b
	 */
	public float getCosPhi (Vector b) {
		
		Vector a = this;
		
		float divisor = (a.length() * b.length());
		if (divisor == 0)			return Float.NaN;
		
		float divident = a.x * b.x + a.y * b.y + a.z * b.z;
	
		return divident / divisor;
	}
	
	/**
	 * The method calculates the tangent of the angle between two vectors. 
	 * The formula is: tan = (|a| - |b|) / ( |a - b| )
	 * 
	 * @param direction
	 * @return the tangent
	 */
	public float getTanPhi(Vector b) {
		Vector a = this;

		float divisor = a.getDirectionFrom(b).length();
		if (divisor == 0)			return Float.NaN;

		float divident = a.length() - b.length();
		
		return divident / divisor;
	}

	public boolean equals(Vector b) {
		return (x == b.getX() & y == b.getY() & z == b.getZ() );
	}
	
	public boolean is000() {
		return (x == 0 & y == 0 & z == 0);
	}
	
	
	@Override
	public String toString() {
		return "( " + this.x + ", " + this.y + ", " + this.z + " )";
	}

	public void reset() {
		// 1. Barriere: Das globale "Nichts" darf nicht zurückgesetzt werden
		if (this == objectNothing) return; 

		// 2. Wächter: Nur im Rechen-Labor (Mutable) erlaubt
		if (!this.isMutable) {
			throw new UnsupportedOperationException("Reset auf unveränderlichem Vektor nicht erlaubt.");
		}

		// Zustand zurücksetzen
		this.x = 0;
		this.y = 0;
		this.z = 0;
		this.normalized = false;
	}
	
	public static Vector crossProduct(Vector vect_A, Vector vect_B) {
		// 1. Barriere für das "Nichts"
		if (vect_A == objectNothing || vect_B == objectNothing) {
			return objectNothing;
		}

		// Erst die mathematischen Komponenten berechnen
		float pX = vect_A.y * vect_B.z - vect_A.z * vect_B.y;
		float pY = vect_A.z * vect_B.x - vect_A.x * vect_B.z;
		float pZ = vect_A.x * vect_B.y - vect_A.y * vect_B.x;
		
		// Direkt als fertiges, geschütztes Daten-Unikat zurückgeben
		return new Vector(pX, pY, pZ);
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
//////////////////////implementing IObjectSender ///////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public int sendYourselfTo(IObjectReceiver receiver, int requestID) {
		return receiver.receiveObject(requestID, this);
	}
	public int sendYourselfTo(IAccessToken token, IObjectReceiver receiver, int requestID) {
		return receiver.receiveObject(requestID, this);
	}
	
	public IObjectSender copy() {
		return this;
	}
	
	
}
