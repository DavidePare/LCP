package com.sample;

public class Player {
	private double cash=0.0;
	private int step=0;
	private boolean humor=true;

	private boolean sitted=true;
	
	public Player(double cash, int step) {
		this.cash=cash;
		this.step=step;
	}
	
	public double getCash() {
		return cash;
	}
	
	public double getStep() {
		return step;
	}
	
	public void losaAll() {
		this.cash=0;
	}
	
	
	public boolean decreaseCash() {
		this.cash-=5;
		if(cash<5)	sitted=false; 
		return cash>=5;
	}
	public void increaseCash(double win) {
		this.cash+=win;
	}
	public void addStep() {
		step++;
	}
	
	public void addAmount(double amount) {
		this.cash+=amount;
	}
	public void setHumor() {
		humor=false;
	}
	public boolean getHumor() {
		return humor;
	}
}