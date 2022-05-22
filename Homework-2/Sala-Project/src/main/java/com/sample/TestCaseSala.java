package com.sample;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.After;
import org.junit.Before;
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
		for(int i=0;i<24;i++) s.playerSit();
    	FactHandle fh = kSession.insert(s);
		Owner boss= s.getBoss();
		//s.fillMachine();
		for(int i=0;i<30;i++)s.emptyMachine();
		//kSession.update(fh, s);
		kSession.getAgenda().getAgendaGroup( "bossOwn" ).setFocus();
		kSession.fireAllRules();
		assertFalse(boss.equals(s.getBoss()));
	}
	
	@Test
	public void testSalaFull() {
		System.out.println("TITLE:Sala full");

		Sala s =new Sala(25);
		s.fillMachine();
    	FactHandle fh = kSession.insert(s);
		kSession.fireAllRules();
	}
	
	@Test
	public void testJackpot() {
		System.out.println("TITLE:Jackpot");

		Sala s =new Sala(25);
		s.playerSit();
		s.getMachines().get(0).jackpot();
    	FactHandle fh = kSession.insert(s);
		kSession.fireAllRules();
		assertTrue(s.getMachines().get(0).getAmount() ==0.0);
	}
	
	@Test
	public void testAmountPlayers() {
		System.out.println("TITLE:Amout Players");

		Sala s =new Sala(25);
		for(int i=0;i<24;i++) s.playerSit();
		s.step();
		double max=Integer.MIN_VALUE,min=Integer.MAX_VALUE,sum=0;
		for(int i=0;i<24;i++) {
			if(max<s.getPlayers().get(i).getCash()) max=s.getPlayers().get(i).getCash();
			if(min>s.getPlayers().get(i).getCash()) min=s.getPlayers().get(i).getCash();
			sum+=s.getPlayers().get(i).getCash();

		}
    	FactHandle fh = kSession.insert(s);
    	System.out.println("Maximum calculated:"+max+" Minimum calculated:"+ min+" Averange"+sum/24 );
		kSession.fireAllRules();
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
		s.playerSit();
		s.getPlayers().get(0).losaAll();;
    	FactHandle fh = kSession.insert(s);
		kSession.fireAllRules();
	}
	
	@Test
	public void testBossGoodReputation() {
		System.out.println("TITLE:Boss good reputation");
		Sala s =new Sala(25);
		for(int i=0;i<24;i++) s.playerSit();

		for(int i=0;i<30;i++)	s.addCashToMachine();
    	FactHandle fh = kSession.insert(s);
		//kSession.update(fh, s);
		kSession.fireAllRules();
	}
}
