public class Euler {
    Functions functions = new Functions();

    public double[][] startCalculations(double a, double b, double y0, double h, int functionNumber) {
        int n = (int) ((b - a) / h + 1);
        double[][] result = new double[n][3];

        result[0][0] = a;
        result[0][1] = y0;

        for (int i = 1; i < n; i++) {
            result[i][0] = result[i - 1][0] + h;
            result[i][1] = result[i - 1][1] + h * (functions.f(result[i][0], + result[i - 1][1], functionNumber));
            result[i][2] = functions.f(result[i][0], result[i][1], functionNumber);
        }

        return result;
    }
}
