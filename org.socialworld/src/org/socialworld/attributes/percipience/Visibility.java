package org.socialworld.attributes.percipience;

import org.socialworld.attributes.Position;
import org.socialworld.attributes.PropertyName;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.ValueProperty;
import org.socialworld.calculation.geometry.Rectangle;
import org.socialworld.calculation.geometry.Vector;
import org.socialworld.calculation.geometry.VectorMapper;
import org.socialworld.objects.Animal;
import org.socialworld.objects.concrete.animals.ISeer;
import org.socialworld.objects.concrete.animals.StateSeer;

public class Visibility {

	private Rectangle visibilityRectangles[];
	
	public Visibility() {
		
		visibilityRectangles = new Rectangle[VectorMapper.COUNT_STANDARD_VISIBLE_AREA_PERPENDICULARS];
		
		
	}

	public Visibility(Rectangle rectangles[]) {
		
		if (rectangles.length == VectorMapper.COUNT_STANDARD_VISIBLE_AREA_PERPENDICULARS) {
			visibilityRectangles = rectangles;
		}
		else {
			visibilityRectangles = new Rectangle[VectorMapper.COUNT_STANDARD_VISIBLE_AREA_PERPENDICULARS];
		}
		
		
	}

	public Visibility(Position middlePointTotal, Vector abcCuboidWithStandardPerpendiculars) {
		
		visibilityRectangles = new Rectangle[VectorMapper.COUNT_STANDARD_VISIBLE_AREA_PERPENDICULARS];
		
		
			
		float a;  // --> x
		float b;  // --> y
		float c;  // --> z
		
		a = abcCuboidWithStandardPerpendiculars.getX();
		b = abcCuboidWithStandardPerpendiculars.getY();
		c = abcCuboidWithStandardPerpendiculars.getZ();
		
		float g;
		float h;

		float hypotenuseAB = hypotenuse(a, b);
		float hypotenuseAC = hypotenuse(a, c);
		float hypotenuseBC = hypotenuse(b, c);
		float cuboidDiagonal = lengthVector(a, b, c);
		
		for (int nrPerpendicular = 0; nrPerpendicular < VectorMapper.COUNT_STANDARD_VISIBLE_AREA_PERPENDICULARS; nrPerpendicular++) {
			
			switch (nrPerpendicular) {
			case 0:
				g = b; h = c; break;
			case 1:
				g = b; h = c; break;   // g = -b 
			case 2:
				g = a; h = c; break;   // g = -a 
			case 3:
				g = a; h = c; break;
			case 4:
				g = a; h = b; break;
			case 5:
				g = a; h = b; break; 	// g = -a 
			case 6:
				g = hypotenuseAB; h = c; break; // g = hypotenuse(-a, b)
			case 7:
				g = hypotenuseAB; h = c; break; // g = hypotenuse(a, -b)
			case 8:
				g = hypotenuseAB; h = c; break; // g = hypotenuse(a, b)
			case 9:
				g = hypotenuseAB; h = c; break; // g = hypotenuse(-a, -b)
			case 10:
				g = b; h = hypotenuseAC; break; // h = hypotenuse(-a, c)
			case 11:
				g = b; h = hypotenuseAC; break; // g = -b, h = hypotenuse(a, c)
			case 12:
				g = a; h = hypotenuseBC; break; // g = -a;  h = hypotenuse(-b, c)
			case 13:
				g = a; h = hypotenuseBC; break; // h = hypotenuse(b, c)
			case 14:
				g = hypotenuseAB; h = cuboidDiagonal; break;  // g = hypotenuse(-a, b), h = lengthVector(-a, -b, c)
			case 15:
				g = hypotenuseAB; h = cuboidDiagonal; break;  // g = hypotenuse(a, b), h = lengthVector(-a, b, c)
			case 16:
				g = hypotenuseAB; h = cuboidDiagonal; break;  // g = hypotenuse(a, -b), h = lengthVector(a, b, c)
			case 17:
				g = hypotenuseAB; h = cuboidDiagonal; break;  // g = hypotenuse(-a, -b), h = lengthVector(a, -b, c)
			case 18:
				g = b; h = hypotenuseAC; break; // h = hypotenuse(a, c)
			case 19:
				g = b; h = hypotenuseAC; break; // g = -b; h = hypotenuse(-a, c)
			case 20:
				g = a; h = hypotenuseBC; break; // g = -a; h = hypotenuse(b, c)
			case 21:
				g = a; h = hypotenuseBC; break; // h = hypotenuse(-b, c)
			case 22:
				g = hypotenuseAB; h = cuboidDiagonal; break;  // g = hypotenuse(-a, b), h = lengthVector(a, b, c)
			case 23:
				g = hypotenuseAB; h = cuboidDiagonal; break;  // g = hypotenuse(a, b), h = lengthVector(a, -b, c)
			case 24:
				g = hypotenuseAB; h = cuboidDiagonal; break;  // g = hypotenuse(a, -b), h = lengthVector(-a, -b, c)
			case 25:
				g = hypotenuseAB; h = cuboidDiagonal; break;  // g = hypotenuse(-a, -b), h = lengthVector(-a, b, c)
			default:
				g = 0; h = 0; break;
			}
			visibilityRectangles[nrPerpendicular] = new Rectangle(middlePointTotal, nrPerpendicular, g, h);
		}
			
			
		
	}
	
	public void setRectangle(int i, Rectangle rect) {
		
		if (i >= 0 && i < VectorMapper.COUNT_STANDARD_VISIBLE_AREA_PERPENDICULARS) {
			visibilityRectangles[i] = rect;
		}
		
	}
	
	public void changeMiddlePoint(Position middlePointTotal) {
		
		for (int nrPerpendicular = 0; nrPerpendicular < VectorMapper.COUNT_STANDARD_VISIBLE_AREA_PERPENDICULARS; nrPerpendicular++) {
			visibilityRectangles[nrPerpendicular].changeMiddlePoint(middlePointTotal);
		}
		
	}
	
	protected boolean checkIsPossibleSeer(Animal possibleSeer, double distance) {
		
		int nrPerpendicular;
		double sizeDistanceRelation;
		double sizeDistanceRelationThreshold;
		
		if (possibleSeer instanceof ISeer) {

			
			ISeer seer = (ISeer) possibleSeer;
			StateSeer stateSeer;
			
			stateSeer = seer.getSavedStateSeer(SimulationCluster.todo);
			nrPerpendicular = stateSeer.getBestPercipiencePerpendicular();

			ValueProperty vp;
			vp = possibleSeer.getStatePropertyFromMethod(SimulationCluster.todo, PropertyName.simobj_stateSeer, ISeer.METHODNAME_SIZEDISTANCERELATIONTHRESHOLD, "sizeDistanceRelThreashold");
			sizeDistanceRelationThreshold = (double) vp.getValue(); 
			
			/*
			nrPerpendicular = possibleSeer.getBestPercipiencePerpendicular();
			sizeDistanceRelationThreshold = possibleSeer.getSizeDistanceRelationThreshold();
			*/
			
			sizeDistanceRelation = getSizeDistanceRelation(nrPerpendicular, distance);
			
			if (sizeDistanceRelation > sizeDistanceRelationThreshold) {
				return true;
			}
			else {
				return false;
			}
		}
		
		return false;
		
	}

	private double getSizeDistanceRelation(int nrStandardPerpendicular, double distance) {
		
		double result = 0;
		if (nrStandardPerpendicular >= 0 && nrStandardPerpendicular < VectorMapper.COUNT_STANDARD_VISIBLE_AREA_PERPENDICULARS) {
			Rectangle rect = visibilityRectangles[nrStandardPerpendicular];
			double area = rect.getArea();
			result = area / distance;
		}
		return result;
	}
	
	private float hypotenuse(float a, float b) {
		
		double result;
		
		result = a*a + b*b;
		result = Math.sqrt(result);
		
		return (float) result;
		
	}
	
	private float lengthVector(float a, float b, float c) {
		
		double result;
		
		result = a*a + b*b + c*c;
		result = Math.sqrt(result);
		
		return (float) result;
		
	}
	
}
