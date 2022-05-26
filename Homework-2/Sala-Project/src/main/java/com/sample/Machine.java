package com.sample;

public class Machine {
	private double totalAmount;
	
	public Machine() {
		totalAmount=1000.0;
	}

	public double jackpot() {
		double t=totalAmount;
		totalAmount=0;
		return t;
	}
	
	public double getAmount() {
		return totalAmount;
	}
	public void addAmount() {
		totalAmount+=5;
	}
	public void decreaseCash(double x) {
		totalAmount-=x;
	}
	public void increaseCash(double x) {
		totalAmount+=x;
	}
}