public class Task {
    private boolean isForE;

    public boolean isForE() {
        return isForE;
    }

    private double[][] system;
    private final String[] str;

    public Equation getEquation() {
        return equation;
    }

    private Equation equation;

    public Equation[] getEquations() {
        return equations;
    }

    private Equation[] equations;

    public DoubleEquation[] getDoubleEquations() {
        return doubleEquations;
    }

    private DoubleEquation[] doubleEquations;


    public Task(String[] str, double[][] system, Equation[] equations, DoubleEquation[] doubleEquations) {
        this.str = str;
        isForE = true;
        this.system = system;
        this.equations = equations;
        this.doubleEquations = doubleEquations;
    }

    public Task(String str, Equation equation) {
        this.str = new String[]{str};
        this.equations = new Equation[]{equation};
        isForE = false;
        this.equation = equation;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String s : str) {
            sb.append(s);
            sb.append("\n");
        }
        return sb.toString();
    }

    public String[] getStr() {
        return str;
    }
}
