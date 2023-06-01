public class Main {
    public static void main(String[] args) {
        InputFunctions inputFunctions = new InputFunctions();
        Simpson simpsonMethod = new Simpson();
        Trapezoid trapezoidMethod = new Trapezoid();
        Rectangle rectangleMethod = new Rectangle();

        while (true) {
            int functionNumber = inputFunctions.inputFunction();
            if (functionNumber != 0) {
                int methodNumber = inputFunctions.inputMethod();

                double a = inputFunctions.inputA();
                double b = inputFunctions.inputB();
                double e = inputFunctions.inputE();

                switch (methodNumber) {
                    case (1) -> trapezoidMethod.startMethod(a, b, e, functionNumber);
                    case (2) -> simpsonMethod.startMethod(a, b, e, functionNumber);
                    case (3) -> {
                        int methodOfRectangles = inputFunctions.inputMethodOfRectangles();

                        DrawChart draw = new DrawChart();
                        draw.draw(a, b, functionNumber);

                        switch (methodOfRectangles) {
                            case (1) -> rectangleMethod.startMethodRight(a, b, e, functionNumber);
                            case (2) -> rectangleMethod.startMethodLeft(a, b, e, functionNumber);
                            case (3) -> rectangleMethod.startMethodMid(a, b, e, functionNumber);
                        }
                    }
                }
            }
        }

    }
}