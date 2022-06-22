package com.sample;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import org.kie.api.runtime.KieSession;

public class Sala {
	private ArrayList<Machine> machines;
	private ArrayList<Player> players;
	private Owner boss;
	private Random rand;
	private Scanner sc;
	private int counter;
	private int length=10;
	public Sala(int startMachine) {
		this.players= new ArrayList<Player>();
		this.machines= new ArrayList<Machine>();
		while(startMachine-- !=0) {
			Machine m = new Machine();
			machines.add(m);
		}
		this.boss= new Owner();
		this.rand = new Random();
		this.sc= new Scanner(System.in);
		
	}
	public void playerSit(KieSession kSession) {
		if(players.size()< machines.size()) {
			Player e = new Player(15,0,machines.get((players.size())));
			players.add(e);
			kSession.insert(e);
		}else System.out.println("He can't sit");
	}
	public void playerLeave() {
		boolean flag=true;
		int pos=0;
		System.out.print("Choice Position of player:");
		while(flag) {
			pos= sc.nextInt();
			if(pos<0 || pos> players.size()) System.out.println("Wrong interval");
			else flag=false;
		}
		Player p = players.get(pos);
		players.remove(pos);
		Machine a= machines.get(pos);
		machines.remove(pos);
		machines.add(a);
	}
	public void playerLeave(Player p) {
		int pos=players.indexOf(p);
		players.remove(pos);
		Machine a= machines.get(pos);
		machines.remove(pos);
		machines.add(a);
	}
	public void showPlayers() {
		for(Player p : players) {
			System.out.println("Player in position "+ players.indexOf(p)+" has "+ p.getCash() + " and he is sitting from " + p.getStep());
		}
	}
	
	public void playerAddAmount() {
		boolean flag=false;
		for(Player p : players) {
			System.out.println("Player number"+ players.indexOf(p) +" Total amount " +p.getCash());
		}
		System.out.print("Choice Position of player:");
		while(flag) {
			int pos= sc.nextInt();
			if(pos<0 || pos> players.size()) System.out.println("Wrong interval");
			else flag=false;
		}
		players.get(0).addAmount(Math.floor(Math.random()*(1000-500+1)+500));
	}
	
	public void showMachine() {
		int j=0;
		int pSize= players.size();
		//System.out.println(players.size());
		aCapo();
		for(int i=0; i<machines.size(); i++) {
			//if(i%5==0 && i!=0) System.out.println();
			System.out.print(fixedLengthString(Double.toString(machines.get(i).getAmount())));
			System.out.print("|||");
			if((i+1)%5==0 && i!=0  ) {
				System.out.println();
				for(j=i-4;j<=i;j++) {
					if(pSize> j) System.out.print(fixedLengthString(Double.toString(players.get(j
							).getCash())));
					else System.out.print(fixedLengthString("FREE"));
					 System.out.print("|||");
				}
				System.out.println();
				aCapo();
			}
		}
		if(machines.size() <5) { //caso machines <5
			System.out.println();
			for(j=0;j<machines.size();j++) {
				if(pSize> j) System.out.print(fixedLengthString(Double.toString(players.get(j
						).getCash())));
				else System.out.print(fixedLengthString("FREE"));
				 System.out.print("|||");
			}
			System.out.println();
			aCapo();
		}
		
	}
	private String fixedLengthString(String string) {
	    return String.format("%1$"+length+ "s", string);
	}
	
	
	private void aCapo() {
		for(int y=0;y<5*(length+3);y++) {
			System.out.print("X");
		}
		System.out.println();
	}
	
	
	public void fillMachine(KieSession kSession) {
		int n= machines.size()-players.size();
		for(int i=0;i<n;i++) this.playerSit(kSession);

	}
	
	public void removeMachine() {
		int pos=0;
		boolean flag=true;
		while(flag) {
			pos= sc.nextInt();
			if(pos<0 || pos>= machines.size()) System.out.println("Wrong interval");
			else flag=false;
		}
		if(players.size()==machines.size()-1 && players.size()!=0) {
			System.out.println("Player cacciato");
			players.remove(pos);
		}
		else if(players.size()!=0){
			if(players.get(pos).getHumor()) players.get(pos).setHumor();
			else players.remove(pos);
		}
		removePosMachine(pos);
		System.out.println("Machine was removed!");
	}

	public void addMachine() {
		Machine m = new Machine();
		machines.add(m);
		System.out.println("Machine was added!");
	}
	
	public void emptyMachine() {
		int pos=(int) Math.floor(Math.random()*(machines.size()-0)+0);
		if(pos<players.size()) {
			players.get(pos).setHumor();
			boss.DecreaseReputation(Math.floor(Math.random()*(5-1)+1));
		}
	}
	
	public void addCashToMachine() {
		Machine minimum=machines.get(0);
		for(Machine m : machines) {
			if(m.getAmount()<minimum.getAmount()) {
				//m.increaseCash(Math.floor(Math.random()*(1000-500)+500));
				//break;
				minimum=m;
			}
		}
		minimum.increaseCash(Math.floor(Math.random()*(1000-500)+500));
		// It may have been seen by client that sponsored him
		if(Math.floor(Math.random()*(10-0+1)+0) > 8) boss.IncreaseReputation(5);
		
	
	}
	
	public ArrayList<Machine> getMachines(){
		return machines;
	}
	public ArrayList<Player> getPlayers(){
		return players;
	}
	
	
	/**
	 * Action executed when boss fail the activity.
	 */
	public void changeBoss() {
		this.boss=new Owner();
		this.counter=0;
	}
	public Owner getBoss() {
		return this.boss;
	}
	/**
	 * Method used to remove first machine
	 */
	public void removePosMachine(int pos) {
		machines.remove(pos);
	}
	
	public void addCounter() {
		this.counter++;
	}
	public void playerSit(Player p) {
		this.players.add(p);
	}
	
}