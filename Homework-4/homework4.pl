/*# state(MonkeyPosition, BananaPosition, ChainPosition)
# state(MonkeyPosition, MonkeyPosition2, BananaPosition, ChainPosition)
#S0=state(corner2,out(garden),celieng,corner3),S1=state(_,_,eaten(banana),_),plan(S0,S1,L).
#S0=state(corner2,out(garden),celieng,corner3),S1=state(_,_,eaten(X),_),plan(S0,S1,L).
Plan with one monkey*/

openable(window).
action(state(X,opened(banana),floor(Pos1)),eat,state(X,eaten(banana),floor(Pos1))).
action(state(X,grasped(banana),floor(Pos1)),open,state(X,opened(banana),floor(Pos1))).
action(state(Pos1,Pos1,floor(Pos1)),grasp,state(under(Pos1),grasped(banana),floor(Pos1))). /* grasp*/
action(state(floor(Pos1),Pos1,floor(Pos1)),climb,state(Pos1,Pos1,floor(Pos1))). /* climb*/

action(state(Pos1,X,Pos1),push(Pos1,floor(X)),state(floor(X),X,floor(X))). /* we need push chair*/
action(state(Pos1,X,Y),go(Pos1,Pos2),state(Pos2,X,Y)).

/*Plan with two monkeys*/

action(state(under(X),floor(X),opened(banana),floor(X)),eat,state(under(X),floor(X),eaten(banana),floor(X))).
action(state(under(X),floor(X),gifted(banana),floor(X)),open,state(under(X),floor(X),opened(banana),floor(X))).
action(state(under(X),floor(X),divided(banana),floor(X)),give,state(under(X),floor(X),gifted(banana),floor(X))).

action(state(under(X),floor(X),grasped(banana),floor(X)),separate,state(under(X),floor(X),divided(banana),floor(X))).
action(state(Pos1,floor(Pos1),Pos1,floor(Pos1)),grasp,state(under(Pos1),floor(Pos1),grasped(banana),floor(Pos1))). /* monkey 1 grasp banana*/
action(state(floor(Pos1),floor(Pos1),Pos1,floor(Pos1)),climb,state(Pos1,floor(Pos1),Pos1,floor(Pos1))). /* monkey one climb on the chair */

action(state(floor(X),floor(Z),X,floor(X)),m2go(floor(Z),floor(X)),state(floor(X),floor(X),X,floor(X))). /* monkey go under the banana */ 
action(state(X,onwindow(X),Pos2,Y),gotofloor,state(X,floor(X),Pos2,Y)). /* the monkey goes down from the window */
action(state(X,opened(X),Pos2,Y),enter,state(X,onwindow(X),Pos2,Y)).  /* monkey 2 enter from the window*/
action(state(floor(X),openable(X),Pos2,Y),m1openwindowtom2,state(floor(X),opened(X),Pos2,Y)). /* monkey 1 open window for monkey 2*/
action(state(Pos1,out(X),Pos2,Y),go(out(X),out(openable(window))),state(Pos1,openable(window),Pos2,Y)). /* The monkey could be not near a window*/
action(state(Pos1,Z,X,Pos1),push(Pos1,floor(X)),state(floor(X),Z,X,floor(X))). /* we need push chair*/
action(state(out(A),Z,X,Y),changemonkeys,state(Z,out(A),X,Y)).
action(state(Pos1,X,Z,Y),go(Pos1,Pos2),state(Pos2,X,Z,Y)).


plan(State,State,[]).


plan(StartState,GoalState,[Action1|Rest]):-
	action(StartState,Action1,NextState),
	plan(NextState,GoalState,Rest).

conc([],L,L).
conc([X|L1],L2,[X|L3]):-
	conc(L1,L2,L3).