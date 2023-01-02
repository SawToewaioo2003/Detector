package com.two.detect.ui.activity.Detect;
import android.graphics.PointF;

public class Faces {
	float eyedistance;
	float confident;
	int size;
	PointF midpoint;

	public Faces(float eyedistance, float confident, int size, PointF midpoint) {
		this.eyedistance = eyedistance;
		this.confident = confident;
		this.size = size;
		this.midpoint = midpoint;
	}

	public void setEyedistance(float eyedistance) {
		this.eyedistance = eyedistance;
	}

	public float getEyedistance() {
		return eyedistance;
	}

	public void setConfident(float confident) {
		this.confident = confident;
	}

	public float getConfident() {
		return confident;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getSize() {
		return size;
	}

	public void setMidpoint(PointF midpoint) {
		this.midpoint = midpoint;
	}

	public PointF getMidpoint() {
		return midpoint;
	}
}
