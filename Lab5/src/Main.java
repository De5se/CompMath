public class Main {
    public static void main(String[] args) {
        InputConsole inputConsole = new InputConsole();
        OutputConsole outputConsole = new OutputConsole();
        Lagrange lagrangeMethod = new Lagrange();
        Newton newtonMethod = new Newton();
        Functions functions = new Functions();

        int inputType = inputConsole.methodInput();
        switch (inputType) {
            case (1) -> {
                double[][] points = inputConsole.inputPoints();
                points = functions.sortPoints(points);
                double x = inputConsole.inPointX("Введите координату X для поиска приближённого значения: ");

                System.out.println("\nЛагранжа");
                outputConsole.outSection(points);
                outputConsole.count(lagrangeMethod.getCountValue(points, x));
                outputConsole.drawChart(points);
                System.out.println();

                System.out.println("Ньютона");
                outputConsole.outSection(points);
                outputConsole.count(newtonMethod.getCountValue(points, x));
                outputConsole.drawChartNewton(points);
                System.out.println();
            }
            case (2) -> {
                int functionNumber = inputConsole.functionSelection();
                double[][] points = inputConsole.inputPointX(functionNumber);
                points = functions.sortPoints(points);
                double x = inputConsole.inPointX("Введите координату X для поиска приближённого значения: ");

                System.out.println("\nМногочлен Лагранжа");
                outputConsole.outSection(points);
                outputConsole.real(functions.f(x, functionNumber), functionNumber);
                outputConsole.count(lagrangeMethod.getCountValue(points, x));
                outputConsole.countError(lagrangeMethod.calculationError(points, functionNumber, x));
                outputConsole.drawChart(points, functionNumber);
                System.out.println();

                System.out.println("Многочлен Ньютона");
                outputConsole.outSection(points);
                outputConsole.real(functions.f(x, functionNumber), functionNumber);
                outputConsole.countNewton(newtonMethod.getCountValue(points, x));
                outputConsole.countError(newtonMethod.calculationError(points, functionNumber, x));
                outputConsole.drawChartNewton(points, functionNumber);
                System.out.println();
            }
        }

    }
}