package com.sample;

public class Owner {
	private double reputation = 10.0;
	private boolean FAILURE =false;
	public Owner() {
		
	}
	
	public void IncreaseReputation(double value) {
		reputation+=value;
	}
	
	public void DecreaseReputation(double value) {
		reputation-=value;
		if(this.reputation <=0) FAILURE=true;
	}
	public double getReputation() {
		return reputation;
	}
	
	public boolean isFailed() {
		return FAILURE;
	}
}