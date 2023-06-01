public class Solve {
    public SolveRun getSolveRun() {
        return solveRun;
    }

    private SolveRun solveRun;
    private String str;
    private boolean forE;

    public boolean isForE() {
        return forE;
    }

    public Solve(String str, SolveRun solveRun, boolean forE) {
        this.solveRun = solveRun;
        this.str = str;
        this.forE = forE;
    }

    @Override
    public String toString() {
        return str;
    }
}
