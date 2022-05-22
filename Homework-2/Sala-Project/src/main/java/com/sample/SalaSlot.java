package com.sample;

import java.util.Scanner;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;


public class SalaSlot {

	public static void main(String[] args) {
		try {
            // load up the knowledge base
			KieServices ks = KieServices.Factory.get();
    	    KieContainer kContainer = ks.getKieClasspathContainer();
        	KieSession kSession = kContainer.newKieSession("ksession-rules");
			int x=-1,y=0;
        	System.out.println("Enter number of initial machine:");
        	Scanner sc = new Scanner(System.in);
        	int numberOfMachine=sc.nextInt();
        	while(numberOfMachine<1 || numberOfMachine>30) {
        		numberOfMachine=sc.nextInt();
        	}
        	Sala s=new Sala(numberOfMachine);
        	FactHandle fh = kSession.insert(s);

        	//FactHandle salaFact= session.insert( fact_a );
        	ConsoleInteraction console = new ConsoleInteraction();
            while(x!=0) {
            	console.initialMenu();
            	y=0;
            	x=sc.nextInt();
            	if(x<0 || x>4) {
            		System.out.println("Wrong select!");
            	}
            	switch(x) {
            		case 1:
            			console.menuPlayer();
            			y=sc.nextInt();
            			operation(s,1,y);
            			break;
            		case 2:
            			console.menuOwner();
            			y=sc.nextInt();
            			operation(s,2,y);
            			break;
            		case 3:
            			console.menuMachine();
            			y=sc.nextInt();
            			operation(s,3,y);
            			break;
            		case 4:
            			//s.step();
            			break;
            	}
            	//FactHandle fc=kSession.insert(s);
            	//kSession.setGlobal( "globalSala", s );
                //kSession.update(null, args);     
            	kSession.update(fh, s);
                kSession.fireAllRules();            
                }
            
        	// go !
        } catch (Throwable t) {
            t.printStackTrace();
        }
	}
	public static void operation(Sala s,int type ,int op) {
		switch(type) {
			case 1: // Player Case
				switch(op) {
					case 1:
						s.showPlayers();
						break;
					case 2:
						s.playerSit();
						break;
					case 3:
						s.playerLeave();
						break;
					case 4:
						s.playerAddAmount();
						break;
				}
				break;
			case 2:
				switch(op) {
				case 1:
					s.removeMachine();
					break;
				case 2:
					s.addMachine();
					break;
				case 3:
					s.emptyMachine();
					break;
				case 4:
					s.addCashToMachine();
					break;
				}
				break;
		
			case 3:
				switch(op) {
					case 1:
						s.showMachine();
						break;
					case 2:
						s.fillMachine();
						break;
				}
				break;
		}
	}
	

}