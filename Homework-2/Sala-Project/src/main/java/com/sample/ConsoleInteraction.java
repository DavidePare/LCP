package com.sample;

public class ConsoleInteraction {
	public void initialMenu() {
		System.out.println("Choice your option:");
		System.out.println("1 - Player");
		System.out.println("2 - Owner");
		System.out.println("3 - Machine");
		System.out.println("4 - Execute a step");
	}
	public void menuMachine() {
		// TODO Auto-generated method stub
		System.out.println("What operation player would like to execute:");
		System.out.println("1 - Show Machine");
		System.out.println("2 - organize event [all machines will be occupied by players]");
		System.out.println("0 - Go back ");
	}
	public void menuOwner() {
		// TODO Auto-generated method stub
		System.out.println("What operation player would like to execute:");
		System.out.println("1 - Remove a machine");
		System.out.println("2 - Add a machine");
		System.out.println("3 - Empty machine [Randomically]");
		System.out.println("0 - Go back");
	}
	public void menuPlayer() {
		// TODO Auto-generated method stub
		System.out.println("What operation player would like to execute:");
		System.out.println("1 - Show all players with their money and the number of played");
		System.out.println("2 - Sit in random position");
		System.out.println("3 - Leave from a machine");
		System.out.println("4 - Add amount");
		System.out.println("0 - Go back");
	}
}