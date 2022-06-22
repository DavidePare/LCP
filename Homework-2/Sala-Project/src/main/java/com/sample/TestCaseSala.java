package com.sample;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

class TestCaseSala {
	static KieSession kSession =null;
	@BeforeAll
	public static void startkSession() {
		KieServices ks = KieServices.Factory.get();
	    KieContainer kContainer = ks.getKieClasspathContainer();
    	kSession = kContainer.newKieSession("ksession-rules");
	}
	@AfterAll
	public static void destroySession() {
		kSession.dispose();
	}
	
	@BeforeEach
	public void initial() {
		System.out.println("---------START---------");
	}
	@AfterEach
	public void finish() {
		System.out.println("----------END----------");

	}
	@Test
	public void testBoss() {
		System.out.println("TITLE:Boss failed");
		Sala s =new Sala(25);
		for(int i=0;i<24;i++) s.playerSit(kSession);
    	FactHandle fh = kSession.insert(s);
		Owner boss= s.getBoss();
		for(int i=0;i<30;i++)s.emptyMachine();
		kSession.getAgenda().getAgendaGroup( "bossOwn" ).setFocus();
		kSession.fireAllRules();
		assertFalse(boss.equals(s.getBoss()));
	}
	
	@Test
	public void testSalaFull() {
		System.out.println("TITLE:Sala full");

		Sala s =new Sala(25);
		s.fillMachine(kSession);
    	FactHandle fh = kSession.insert(s);
		kSession.fireAllRules();
	}
	
	@Test
	public void testJackpot() {
		System.out.println("TITLE:Jackpot");

		Sala s =new Sala(100);
		for(int i=0;i<100;i++) {
			s.getMachines().get(i).jackpot();
			s.getMachines().get(i).decreaseCash(5);
		}
		for(int i=0;i<99;i++) s.playerSit(kSession);
    	FactHandle fh = kSession.insert(s);
		kSession.fireAllRules();
		
	}
	
	@Test
	public void testAmountPlayers() {
		System.out.println("TITLE:Amout Players | Big Amount");

		Sala s =new Sala(25);
		for(int i=0;i<24;i++) s.playerSit(kSession);
    	FactHandle fh = kSession.insert(s);
    	s.getPlayers().get(0).addAmount(1000);
		kSession.fireAllRules();
		double max=Integer.MIN_VALUE,min=Integer.MAX_VALUE,sum=0;
		for(int i=0;i<24;i++) {
			if(max<s.getPlayers().get(i).getCash()) 
				max=s.getPlayers().get(i).getCash();
			if(min>s.getPlayers().get(i).getCash()) 
				min=s.getPlayers().get(i).getCash();
			sum+=s.getPlayers().get(i).getCash();

		}
    	System.out.println("Maximum calculated:"+max+
    			" Minimum calculated:"+ min+" Averange"+sum/24 );
	}

	@Test
	public void testNotMachine() {
		kSession.destroy();
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.getKieClasspathContainer();
    	kSession = kContainer.newKieSession("ksession-rules");
		System.out.println("TITLE:No machine!");
		Sala s =new Sala(1);
		s.removePosMachine(0);
    	FactHandle fh = kSession.insert(s);
		kSession.fireAllRules();
	}
	
	@Test
	public void TestPlayerLoseAll() {
		System.out.println("TITLE:Player Lose everything");
		Sala s =new Sala(25);
		for(int i=0;i<24;i++) s.playerSit(kSession);
		for(int i=0;i<5;i++) {
			s.getPlayers().get(i).losaAll();
			s.getPlayers().get(i).addAmount(5);
		}
		kSession.insert(s.getPlayers().get(0));
    	FactHandle fh = kSession.insert(s);
		kSession.fireAllRules();
		kSession.fireAllRules();
	}
	
	@Test
	public void testBossGoodReputation() {
		System.out.println("TITLE:Boss good reputation");
		Sala s =new Sala(25);
		for(int i=0;i<100;i++)	s.addCashToMachine();
    	FactHandle fh = kSession.insert(s);
		kSession.fireAllRules();
	}
	
	@Test
	public void step() {
		Sala s =new Sala(4);
		for(int i=0;i<3;i++) s.playerSit(kSession);
		System.out.println("Initial situation");   
		s.showMachine();
		System.out.println("After 3 Rounds..");        	
		FactHandle fh = kSession.insert(s);
		kSession.insert(fh);
		kSession.fireAllRules();
		kSession.update(fh, s);
		kSession.fireAllRules();
		kSession.update(fh, s);
		kSession.fireAllRules();
		s.showMachine();
	}
}
