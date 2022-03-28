int: R;
int: C;
set of int: row= 1..R;
set of int: column= 1..C;

set of int: internalRow= 2..R-1;

set of int: internalColumn= 2..C-1;
int: M=-1;
set of int : accetableValue = -1..0;
array[row,column] of var accetableValue : bomb;

array[row,column] of var int : solution;

constraint assert(R >= 1,"Wrong Row Data");
constraint assert(C >= 1,"Wrong Column Data");

% First type didn't work
%constraint assert(forall(i in row,j in column) (fix(bomb[i,j] !=M \/ bomb[i,j]!=0)),"Errore");
%This type block it and but not in a beautiful way
%constraint forall(i in row,j in column) (bomb[i,j] ==-1 \/ bomb[i,j]==0); 
%constraint forall(i in row,j in column) (assert(fix(bomb[i,j] !=-1 \/ bomb[i,j]!=0),"Wrong Data Input"));
%constraint forall(i in row,j in column) (assert(fix(bomb[i,j] ==-1 \/ bomb[i,j]==0),"Wrong Data Input"));

% Case single cell
constraint if R==1 /\ C==1 then solution[1,1]=bomb[1,1] endif;

% Case if is a single row
% Corner Elements 
constraint if R==1  /\ C>1 then if bomb[1,1] == -1 then solution[1,1]=-1 else solution[1,1]=abs(solution[1,2]) endif endif;
constraint if R==1  /\ C>1 then if bomb[1,C] == -1 then solution[1,C]=-1 else solution[1,C]=abs(solution[1,C-1]) endif endif;
% Internal Element 
constraint if R==1 /\ C>=2 then forall(j in internalColumn) ( 
  if bomb[1,j] != -1 then solution[1,j]= abs(bomb[1,j-1]+bomb[1,j+1]) else solution[1,j]=-1 endif
) endif;

% Case if is a single column
% Corner Elements
constraint if R>=2  /\ C==1 then if bomb[1,1] == -1 then solution[1,1]=-1 else solution[1,1]=abs(solution[2,1]) endif endif;
constraint if R>=2  /\ C==1 then if bomb[R,1] == -1 then solution[R,1]=-1 else solution[R,1]=abs(solution[R-1,1]) endif endif;
% Internal Elements
constraint if R>=2 /\ C==1 then forall(i in internalRow) ( 
  if bomb[i,1] != -1  then solution[i,1]= abs(bomb[i-1,1]+bomb[i+1,1]) else solution[i,1]=-1 endif
) endif;



%internal Matrix
constraint forall(i in internalRow, j in internalColumn)(
  if bomb[i,j]!=-1 then solution[i,j]=abs(bomb[i-1,j-1]+bomb[i-1,j]+bomb[i-1,j+1]+bomb[i,j-1]+bomb[i,j]+bomb[i,j+1]+bomb[i+1,j-1]+bomb[i+1,j]+bomb[i+1,j+1]) else solution[i,j]=-1 endif
);

%Border Element
constraint if C>=2 /\ R>=2 then if bomb[1,1]==-1 then solution[1,1]=-1 else solution[1,1] = abs(bomb[1,2]+bomb[2,2]+bomb[2,1])  endif endif;

constraint if C>=2 /\ R>=2 then if bomb[1,C]==-1 then solution[1,C]=-1 else solution[1,C] = abs(bomb[1,C-1]+bomb[2,C]+bomb[2,C-1])  endif endif;

constraint if C>=2 /\ R>=2 then if bomb[R,1]==-1 then solution[R,1]=-1 else solution[R,1] = abs(bomb[R-1,2]+bomb[R-1,1]+bomb[R,2]) endif endif ;

constraint if C>=2 /\ R>=2 then if bomb[R,C]==-1 then solution[R,C]=-1 else solution[R,C] = abs(bomb[R-1,C-1]+bomb[R-1,C]+bomb[R,C-1]) endif endif;





% First Row
constraint if C>=2 /\ R>=2 then  forall(j in internalColumn) ( 
  if (bomb[1,j] != -1 /\ R>=2) then solution[1,j]= abs(bomb[1,j-1]+bomb[1,j+1]+bomb[2,j-1]+bomb[2,j+1]+bomb[2,j]) else solution[1,j]=-1 endif
) endif;

% Last Row 
constraint if C>=2 /\ R>=2 then forall(j in internalColumn) ( 
  if (bomb[R,j] != -1 /\ R>=2) then solution[R,j]= abs(bomb[R,j-1]+bomb[R,j+1]+bomb[R-1,j-1]+bomb[R-1,j+1]+bomb[R-1,j]) else solution[R,j]=-1 endif
) endif;

% First column
constraint if C>=2 /\ R>=2 then forall(i in internalRow) ( 
  if (bomb[i,1] != -1 /\ C>=2) then solution[i,1]= abs(bomb[i-1,1]+bomb[i+1,1]+bomb[i-1,2]+bomb[i,2]+bomb[i+1,2]) else solution[i,1]=-1 endif
) endif;
% Last Column
constraint if C>=2 /\ R>=2 then forall(i in internalRow) ( 
  if (bomb[i,C] != -1 /\ C>=2) then solution[i,C]= abs(bomb[i-1,C]+bomb[i+1,C]+bomb[i-1,C-1]+bomb[i,C-1]+bomb[i+1,C-1]) else solution[i,C]=-1 endif
) endif;
solve satisfy;

output [ if fix(solution[i,j]) !=-1 then show(solution[i,j]) else "M"  endif ++ 
         if j == C then "\n" else " " endif |
         i in row, j in column
];
