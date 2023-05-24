public class Functions {
    public double f(double x, int number) {
        switch (number) {
            case (1) -> {
                return -2 * Math.pow(x, 3) - 4 * Math.pow(x, 2) + 8 * x - 4;
            }
            case (2) -> {
                return 3 * Math.pow(x, 5) + Math.pow(x, 2) + 0.1;
            }
            case (3) -> {
                return Math.sin(x) + Math.cos(x);
            }
            case (4) -> {
                return 1 / x;
            }
            default -> {
                System.out.println("Введите число в диапазоне 1-4");
                return f(x, number);
            }
        }

    }

    private double f_dx(double x, int number) {
        switch (number) {
            case (1) -> {
                return -2 * Math.pow(x, 4)/4 - 4 * Math.pow(x, 3)/3 + 8 * x * x / 2 - x * 4;
            }
            case (2) -> {
                return 3 * Math.pow(x, 6) / 6 + Math.pow(x, 3) / 3 + x / 10;
            }
            case (3) -> {
                return Math.sin(x) - Math.cos(x);
            }
            case (4) -> {
                return Math.log(x);
            }
            default -> {
                System.out.println("Введите число в диапазоне 1-4");
                return f_dx(x, number);
            }
        }
    }

    public double getI(double a, double b, int number) {
        return f_dx(b, number) - f_dx(a, number);
    }
}