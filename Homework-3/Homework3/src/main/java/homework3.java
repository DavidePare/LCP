import com.google.ortools.Loader;
import com.google.ortools.constraintsolver.DecisionBuilder;
import com.google.ortools.constraintsolver.IntVar;
import com.google.ortools.constraintsolver.Solver;
import java.util.Scanner;
    public final class homework3 {
        public static void main(String[] args) {
            Loader.loadNativeLibraries();
            Solver solver = new Solver("Queens-Problem");
            Scanner sc = new Scanner(System.in);
            int boardSize=-1;
            // [START variables]
            try{
                System.out.println("Insert the value of boardSize (more the value is high more time could be required):");
                while(boardSize<1)
                    boardSize = sc.nextInt();
            }catch (Exception e){
                System.out.println("Something went wrong. The value of board will be setted to 8");
                boardSize=8;
            }
            IntVar[] queens = new IntVar[boardSize];
            for (int i = 0; i < boardSize; ++i) {
                queens[i] = solver.makeIntVar(0, boardSize - 1, "x" + i);

            }
            // Constraint: All rows must be different.
            solver.addConstraint(solver.makeAllDifferent(queens));

            // All columns must be different because the indices of queens are all different.
            // Constraint:No two queens can be on the same diagonal.
            IntVar[] diag1 = new IntVar[boardSize];
            IntVar[] diag2 = new IntVar[boardSize];
            for (int i = 0; i < boardSize; ++i) {
                diag1[i] = solver.makeSum(queens[i], i).var(); //right diagonal
                diag2[i] = solver.makeSum(queens[i], -i).var(); //left diagonal
            }
            solver.addConstraint(solver.makeAllDifferent(diag1));
            solver.addConstraint(solver.makeAllDifferent(diag2));

            // Create the decision builder to search for solutions.
            final DecisionBuilder db =
                    solver.makePhase(queens, Solver.CHOOSE_FIRST_UNBOUND, Solver.ASSIGN_MIN_VALUE);


            int solutionCount = 0;
            solver.newSearch(db); //Start to find solution
            while (solver.nextSolution()) { //While exists a solution
                System.out.println("Solution " + solutionCount);
                for (int i = 0; i < boardSize; ++i) {
                    for (int j = 0; j < boardSize; ++j) {
                        if (queens[j].value() == i) {
                            System.out.print("Q");
                        } else {
                            System.out.print("_");
                        }
                        if (j != boardSize - 1) {
                            System.out.print(" ");
                        }
                    }
                    System.out.println();
                }
                solutionCount++;
            }
            solver.endSearch();

            // Statistics.
            System.out.println("Statistics");
            System.out.println("  failures: " + solver.failures());
            System.out.println("  branches: " + solver.branches());
            System.out.println("  wall time: " + solver.wallTime() + "ms");
            System.out.println("  Solutions found: " + solutionCount);
        }

        private homework3() {}
    }


