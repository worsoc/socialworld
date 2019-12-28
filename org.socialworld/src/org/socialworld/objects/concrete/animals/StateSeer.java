package org.socialworld.objects.concrete.animals;

import org.socialworld.calculation.geometry.Vector;
import org.socialworld.calculation.geometry.VectorMapper;
import org.socialworld.objects.State;

public class StateSeer extends State {

	private Vector directionView ;

	private float angleView;
	private int bestPercipiencePerpendicular;

	private double sizeDistanceRelationThreshold;
	
	public StateSeer() {
		setDirectionView( new Vector(2,1,0));
		setAngleView(60.0F);
	}
	
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
	
	public void setAngleView(float angleView) {
		this.angleView = angleView;
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
