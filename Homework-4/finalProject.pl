/*
# state(MonkeyPosition, BananaPosition, ChainPosition)
# state(MonkeyPosition, MonkeyPosition2, BananaPosition, ChainPosition)

# Action for a single monkey 

#action(state(Pos,Pos,held),drop,state(Pos,Pos,in_basket)).
#action(state(Pos1,Y,floor(Pos1)),pickup,state(Pos1,Y,held)).
#action(state(Pos1,Pos1,X),push(Pos1,Pos2),state(Pos2,Pos2,X)).
#action(state(Pos1,X,Y),go(Pos1,Pos2),state(Pos2,X,Y)).
#S0=state(corner2,celieng,corner3),S1=state(_,eaten(banana),_),
le banane potrebbero essere in basso?
*/
/* Plan with one monkey*/

openable(window).
action(state(X,opened(banana),floor(Pos1)),eat,state(under(X),eaten(banana),floor(Pos1))).
action(state(X,grasped(banana),floor(Pos1)),open,state(under(X),opened(banana),floor(Pos1))).
action(state(Pos1,Pos1,floor(Pos1)),grasp,state(under(Pos1),grasped(banana),floor(Pos1))). /* grasp*/
action(state(floor(Pos1),Pos1,floor(Pos1)),climb,state(Pos1,Pos1,floor(Pos1))). /* climb*/

action(state(Pos1,X,Pos1),push(Pos1,floor(X)),state(floor(X),X,floor(X))). /* we need push chair*/
action(state(Pos1,X,Y),go(Pos1,Pos2),state(Pos2,X,Y)).


/*#S0=state(corner2,out(X),celieng,corner3),S1=state(_,_,eaten(banana),_),plan(S0,S1,L)*/

/*#S0=state(corner2,out(X),celieng,corner3),S1=state(_,_,eaten(X),_),plan(S0,S1,L).*/
/*Plan with two monkey*/
action(state(under(X),floor(X),opened(banana),floor(X)),eat,state(under(X),floor(X),eaten(banana),floor(X))).
action(state(under(X),floor(X),divided(banana),floor(X)),open,state(under(X),floor(X),opened(banana),floor(X))).
action(state(under(X),floor(X),grasped(banana),floor(X)),separate,state(under(X),floor(X),divided(banana),floor(X))).
action(state(Pos1,floor(Pos1),Pos1,floor(Pos1)),grasp,state(under(Pos1),floor(Pos1),grasped(banana),floor(Pos1))). /* grasp*/
action(state(floor(Pos1),floor(Pos1),Pos1,floor(Pos1)),climb,state(Pos1,floor(Pos1),Pos1,floor(Pos1))). /* climb*/ 

/*#S0=state(corner2,onwindow(X),celieng,corner3),S1=state(_,floor(X),_,_),plan(S0,S1,L).*/
action(state(floor(X),floor(Pos1),X,floor(X)),goM2underBanana(floor(Pos1),floor(X)),state(floor(X),floor(X),X,floor(X))). /*monkey go under the banana */ 

action(state(floor(X),opened(X),Pos2,Y),enterM2,state(floor(X),onwindow(X),Pos2,Y)).  /* monkey 2 enter from the window*/
action(state(floor(X),onwindow(X),Pos2,Y),gotofloorM2,state(floor(X),floor(X),Pos2,Y)). /* the monkey scende from the window */

action(state(climbed(X),openable(X),Pos2,Y),openM1,state(floor(X),opened(X),Pos2,Y)). /* monkey 1 open window*/
action(state(floor(X),openable(X),Pos2,Y),climbWindowM1,state(climbed(X),openable(X),Pos2,Y)). /* monkey 1 open window*/


action(state(Pos1,floor(X),X,Pos1),push(Pos1,floor(X)),state(floor(X),floor(X),X,floor(X))). /* we need push chair*/
action(state(floor(X),Pos1,X,floor(X)),goM2underBanana(Pos1,floor(X)),state(floor(X),floor(X),X,floor(X))). /*monkey go under the banana */ 


action(state(opened(X),floor(X),Pos2,Y),enterM1,state(onwindow(X),floor(X),Pos2,Y)).  /* monkey 2 enter from the window*/
action(state(onwindow(X),floor(X),Pos2,Y),gotofloorM1,state(floor(X),floor(X),Pos2,Y)). /* the monkey scende from the window */

action(state(openable(X),climbed(X),Pos2,Y),openM2,state(opened(X),floor(X),Pos2,Y)). /* monkey 1 open window*/
action(state(openable(X),X,Pos2,Y),climbWindowM2,state(openable(X),climbed(X),Pos2,Y)). /* monkey 1 open window*/


action(state(X,out(X),Pos2,Y),goM2(out(X),out(openable(X))),state(X,openable(X),Pos2,Y)). /* si sposta da una posizione esterna a una finesta */
action(state(out(X),X,Pos2,Y),goM1(out(X),out(openable(X))),state(openable(X),X,Pos2,Y)). /* scimmia uno si sposta davanti a una finestra apribile*/
action(state(Pos1,X,Z,Y),go(Pos1,Pos2),state(Pos2,X,Z,Y)).


plan(State,State,[]).


plan(StartState,GoalState,[Action1|Rest]):-
	action(StartState,Action1,NextState),
	plan(NextState,GoalState,Rest).

conc([],L,L).
conc([X|L1],L2,[X|L3]):-
	conc(L1,L2,L3).