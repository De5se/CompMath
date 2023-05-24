public class Main {

    public static void main(String[] args) {
        InputManager inputManager = new InputManager();
        OutputManager outputManager = new OutputManager();
        Euler eulerMethod = new Euler();
        EulerModified eulerModifiedMethod = new EulerModified();
        Adams adamsMethod = new Adams();

        DrawChart drawChart = new DrawChart();
        int inputType;
        while (true) {
            inputType = inputManager.methodInput();

            if (inputType == 1 || inputType == 2){
                int functionNumber = inputManager.functionSelection();
                double a = inputManager.inputA();
                double b = inputManager.inputB();
                double h = inputManager.inputH();
                double y0 = inputManager.inputY();
                int e = inputManager.inputE();

                double[][] result = (inputType == 1 )
                                ? eulerMethod.startCalculations(a, b, y0, h, functionNumber)
                                : eulerModifiedMethod.startCalculations(a, b, y0, h, functionNumber);
                outputManager.printInterval(a, b, h);
                outputManager.printTable(result, e);
                drawChart.draw(result, (inputType == 1 )
                        ? "Метод Эйлера"
                        : "Модифицированный метод Эйлера");
            }
            else if (inputType == 3){
                int functionNumber = inputManager.functionSelection();
                double a = inputManager.inputA();
                double b = inputManager.inputB();
                double h = inputManager.inputH();
                double y0 = inputManager.inputY();
                double eps = inputManager.inputEps();
                int e = inputManager.inputE();

                double[][] result = adamsMethod.startCalculations(a, b, y0, h, eps, functionNumber);
                outputManager.printInterval(a, b, h);
                outputManager.printTable(result, e);
                drawChart.draw(result, "Метод Адамса");
            }
        }
    }
}
