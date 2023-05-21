public class Main {

    public static void main(String[] args) {
        InputManager inputConsole = new InputManager();
        OutputManager outputConsole = new OutputManager();
        Euler eulerMethod = new Euler();
        EulerModified eulerModifiedMethod = new EulerModified();
        Adams adamsMethod = new Adams();

        DrawChart drawChart = new DrawChart();
        int inputType;
        while (true) {
            inputType = inputConsole.methodInput();

            if (inputType == 1 || inputType == 2){
                int functionNumber = inputConsole.functionSelection();
                double a = inputConsole.inputA();
                double b = inputConsole.inputB();
                double h = inputConsole.inputH();
                double y0 = inputConsole.inputY();
                int e = inputConsole.inputE();
                double[][] result = (inputType == 1 )
                                ? eulerMethod.startCalculations(a, b, y0, h, functionNumber)
                                : eulerModifiedMethod.startCalculations(a, b, y0, h, functionNumber);
                outputConsole.printInterval(a, b, h);
                outputConsole.printTable(result, e);
                drawChart.draw(result, (inputType == 1 )
                        ? "Метод Эйлера"
                        : "Модифицированный метод Эйлера");
            }
            else if (inputType == 3){
                int functionNumber = inputConsole.functionSelection();
                double a = inputConsole.inputA();
                double b = inputConsole.inputB();
                double h = inputConsole.inputH();
                double y0 = inputConsole.inputY();
                double eps = inputConsole.inputEps();
                int e = inputConsole.inputE();
                double[][] result = adamsMethod.startCalculations(a, b, y0, h, eps, functionNumber);
                outputConsole.printInterval(a, b, h);
                outputConsole.printTable(result, e);
                drawChart.draw(result, "Метод Адамса");
            }
        }
    }
}
