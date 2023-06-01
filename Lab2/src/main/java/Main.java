import java.util.ArrayList;

public class Main {
    private static final int maxIterations = 1000000;

    public static void main(String[] args) throws Exception {
        ArrayList<Task> tasks = new ArrayList<>();
        ArrayList<Solve> solves = new ArrayList<>();

        tasks.add(new Task("x^3 - 4.5*x^2 - 9.21x - 0.383 = 0", (double x) -> x * x * x - 4.5 * x * x - 9.21 * x - 0.383));
        tasks.add(new Task("cos(x) - 100x^2 + 0.0001 = 0", x -> Math.cos(x) - 100 * Math.pow(x, 2) + 0.0001));
        tasks.add(new Task("x^3 + 2.28x^2 - 1.934x - 3.907 = 0", x -> Math.pow(x, 3) + 2.28 * x * x - 1.934 * x - 3.907));

        tasks.add(new Task(new String[]{"2x + 3y = 7", "4x - 2y = 6"},
                new double[][]{{2, 3, 7}, {4, -2, 6}},
                new Equation[]{(x) -> -0.66666 * x + 2.33333, (x) -> 2 * x - 3},
                new DoubleEquation[]{(x, y) -> 2 * x + 3 * y - 7, (x, y) -> 4 * x - 2 * y - 6}));
        tasks.add(new Task(new String[]{"x^2 - y = 30", "3x + 5y = 15"},
                new double[][]{{6, -1, 30}, {3, 5, 15}},
                new Equation[]{(x) -> 6 * x - 30, (x) -> 3 - 3 * x / 5},
                new DoubleEquation[]{(x, y) -> x * x - y - 30, (x, y) -> 3 * x + 5 * y - 15}));

        solves.add(new Solve("Метод хорд", (Task t, InputOutput inputOutput, double accuracy) -> {
            double a = inputOutput.getA(t);
            double b = inputOutput.getB(t, a);

            double x = 0;
            double xOld = x;
            int i = 1;
            while (Math.abs(t.getEquation().f(x)) > accuracy || Math.abs(x - xOld) > accuracy) {
                xOld = x;
                x = (a * t.getEquation().f(b) - b * t.getEquation().f(a)) / (t.getEquation().f(b) - t.getEquation().f(a));
                if (t.getEquation().f(x) * t.getEquation().f(a) < 0) {
                    b = x;
                } else {
                    a = x;
                }
                i++;
            }
            new Graph(t, inputOutput);
            if (i != 100000) {
                inputOutput.resultOfL(x, t.getEquation().f(x), i);
            } else {
                System.out.println("Не хватает итераций!");
            }
        }, false));
        solves.add(new Solve("Метод Ньютона", (Task t, InputOutput inputOutput, double accuracy) -> {
            double a = inputOutput.getA(t);
            double b = inputOutput.getB(t, a);
            if (t.getEquation().f(a) * t.getEquation().f(b) >= 0)
                throw new Exception("Концы отрезка неправильных знаков.");
            int i = 1;
            double xn = t.getEquation().f(a) * Diverg.getDoubleVal(t.getEquation(), a) > 0 ? a : b;
            while (i < 100000) {

                double next = xn - t.getEquation().f(xn) / Diverg.getVal(t.getEquation(), xn);
                if (Math.abs(next - xn) < accuracy) {
                    inputOutput.resultOfL(next, t.getEquation().f(next), i);
                    break;
                }
                xn = next;
                i++;
            }
            if (i == 100000)
                System.out.println("Не хватает итераций!");
            new Graph(t, inputOutput);

        }, false));
        solves.add(new Solve("Метод простой итерации", (Task t, InputOutput inputOutput, double accuracy) -> {
            double a = inputOutput.getA(t);
            double b = inputOutput.getB(t, a);

            double x = Diverg.getVal(t.getEquation(), a) > Diverg.getVal(t.getEquation(), b) ? a : b;
            Equation lamb = (double x1) -> -1 / (Math.max(Diverg.getVal(t.getEquation(), a), Diverg.getVal(t.getEquation(), b)));
            double valDefA = 1 + Diverg.getVal(t.getEquation(), a) * lamb.f(0);
            double valDefB = 1 + Diverg.getVal(t.getEquation(), b) * lamb.f(0);
            System.out.println("a:" + Math.abs(valDefA));
            System.out.println("b:" + Math.abs(valDefB));

            if (Math.abs(valDefA) < 1) {
                System.out.println("В точке a сходимость выполняется!");
                x = a;
            } else {
                System.out.println("В точке a сходимость НЕ выполняется!");
            }

            if (Math.abs(valDefB) < 1) {
                System.out.println("В точке b сходимость выполняется!");
                x = b;
            } else {
                System.out.println("В точке b сходимость НЕ выполняется!");
            }

            int i = 1;
            while (i < maxIterations) {
                double xn = x + t.getEquation().f(x) * (-1 / Math.max(Diverg.getVal(t.getEquation(), a), Diverg.getVal(t.getEquation(), b)));

                if (Math.abs(xn - x) < accuracy && Math.abs(t.getEquation().f(x) - t.getEquation().f(xn)) < accuracy) {
                    inputOutput.resultOfL(xn, t.getEquation().f(xn), i);
                    break;
                }
                i++;
                x = xn;
            }
            if (i == maxIterations) {
                inputOutput.unsuccess(i);
            }
            new Graph(t, inputOutput);

        }, false));

        solves.add(new Solve("Метод Ньютона", (Task t, InputOutput inputOutput, double accuracy) -> {
            double firstX = inputOutput.getFirstForXE();
            double firstY = inputOutput.getFirstForYE();
            DoubleEquation f1d_x = Diverg.diffX(t.getDoubleEquations()[0]);
            DoubleEquation f1d_y = Diverg.diffY(t.getDoubleEquations()[0]);
            DoubleEquation f2d_x = Diverg.diffX(t.getDoubleEquations()[1]);
            DoubleEquation f2d_y = Diverg.diffY(t.getDoubleEquations()[1]);
            double xn = firstX;
            double yn = firstY;
            int i = 1;
            while (i < 1000000) {
                double[][] matrix = new double[][]{
                        {f1d_x.f(xn, yn), f1d_y.f(xn, yn), -t.getDoubleEquations()[0].f(xn, yn)},
                        {f2d_x.f(xn, yn), f2d_y.f(xn, yn), -t.getDoubleEquations()[1].f(xn, yn)}
                };

                double det = matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
                double x = (matrix[0][2] * matrix[1][1] - matrix[1][2] * matrix[0][1]) / det;
                double y = (matrix[0][0] * matrix[1][2] - matrix[1][0] * matrix[0][2]) / det;
                double xNext = xn + x;
                double yNext = yn + y;
                if ((Math.abs(x) < accuracy) && (Math.abs(y) < accuracy)) {
                    inputOutput.resultOfE(new double[]{xNext, yNext}, new double[]{x, y}, i);
                    new Graph(t, inputOutput);
                    return;
                }
                i++;
                xn = xNext;
                yn = yNext;
            }
            inputOutput.unsuccess(i);
            new Graph(t, inputOutput);
        }, true));

        InputOutput inputOutput = new InputOutput();
        inputOutput.selectInputMode();
        inputOutput.selectOutputMode();

        Task targetTask = inputOutput.selectTask(tasks);
        SolveRun solveRun = inputOutput.setSolveType(targetTask, solves);

        double accuracy = inputOutput.getAccuracy();
        solveRun.f(targetTask, inputOutput, accuracy);
    }
}
