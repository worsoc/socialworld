package org.socialworld.objects.concrete.animals;

import org.socialworld.calculation.geometry.Vector;
import org.socialworld.calculation.geometry.VectorMapper;
import org.socialworld.objects.State;

public class StateSeer extends State {

	private Vector directionView;

	private float angleView = 60.0F;
	private int bestPercipiencePerpendicular;

	private double sizeDistanceRelationThreshold;
	
	public double getSizeDistanceRelationThreshold() {
		return this.sizeDistanceRelationThreshold;
	}
	
	public Vector getDirectionView() {
		return new Vector(this.directionView);
	}

	public void setDirectionView(Vector directionView) {
		this.directionView = directionView;
		setBestPercipiencePerpendicular();
	}
	
	public float getAngleView() {
		return this.angleView;
	}
	
	public int getBestPercipiencePerpendicular() {
		return this.bestPercipiencePerpendicular;
	}

	private void setBestPercipiencePerpendicular() {
		this.bestPercipiencePerpendicular =  VectorMapper.getInstance().getBestVisibleAreaPerpendicular(this.directionView);

	}
	
}
