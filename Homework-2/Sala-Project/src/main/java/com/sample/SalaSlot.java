package com.sample;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;


public class SalaSlot {
	public static final String TEXT_RESET = "\u001B[0m";
	public static final String TEXT_BLACK = "\u001B[30m";
	public static final String TEXT_RED = "\u001B[31m";
	public static final String TEXT_GREEN = "\u001B[32m";
	public static final String TEXT_YELLOW = "\u001B[33m";
	public static final String TEXT_BLUE = "\u001B[34m";
	public static final String TEXT_PURPLE = "\u001B[35m";
	public static final String TEXT_CYAN = "\u001B[36m";
	public static final String TEXT_WHITE = "\u001B[37m";
	public static KieSession kSession;
	public static void main(String[] args) {
		try {
            // load up the knowledge base
			KieServices ks = KieServices.Factory.get();
    	    KieContainer kContainer = ks.getKieClasspathContainer();
        	kSession = kContainer.newKieSession("ksession-rules");
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
            			//s.getPlayers().stream().forEach(t -> kSession.insert(t));
                    	kSession.update(fh, s);
                        kSession.fireAllRules();  
                        int size=kSession.getQueryResults("palyer-are-going-toLoseAll").toList().size();
                        if(size > 0) System.out.println(TEXT_YELLOW+size +" are going to lose everything"+TEXT_RESET);
                        /*List<Map<String,Object>> results=kSession.getQueryResults("palyer-are-going-toLoseAll").toList(); //.forEach(t -> System.out.println("\u001B[33m This player is going to lose everything" + t.getClass() + "\u001B[0m" ));
            			results.forEach(t ->{
	            			for (String key: t.keySet()) {
	            			    System.out.println("key : " + key);
	            			    System.out.println("value : " + t.get(key));
	            			}});*/
            			//s.step();
            			break;
            	}
            	//FactHandle fc=kSession.insert(s);
            	//kSession.setGlobal( "globalSala", s );
                //kSession.update(null, args);              
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
						s.playerSit(kSession);
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
						s.fillMachine(kSession);
						break;
				}
				break;
		}
	}
	

}