public class Adams {
    Functions functions = new Functions();

    public double[][] startCalculations(double a, double b, double y0, double h, double eps, int functionNumber) {
        int n = (int) ((b - a) / h + 1);

        double[][] result = new double[n][7];

        result[0][0] = a;
        result[0][1] = y0;
        result[0][2] = functions.f(result[0][0], result[0][1], functionNumber);

        result[0][3] = h * functions.f(result[0][0], result[0][1], functionNumber);
        result[0][4] = h * functions.f(result[0][0] + h / 2, result[0][1] + result[0][3] / 2, functionNumber);
        result[0][5] = h * functions.f(result[0][0] + h / 2, result[0][1] + result[0][4] / 2, functionNumber);
        result[0][6] = h * functions.f(result[0][0] + h, result[0][1] + result[0][5], functionNumber);

        for (int i = 1; i < 4; i++) {
            result[i][0] = result[i - 1][0] + h;
            result[i][1] = result[i - 1][1] + (result[i - 1][3] + 2 * result[i - 1][4] + 2 * result[i - 1][5] + result[i - 1][6]) / 6;
            result[i][2] = functions.f(result[i][0], result[i][1], functionNumber);

            result[i][3] = h * functions.f(result[i][0], result[i][1], functionNumber);
            result[i][4] = h * functions.f(result[i][0] + h / 2, result[i][1] + result[i][3] / 2, functionNumber);
            result[i][5] = h * functions.f(result[i][0] + h / 2, result[i][1] + result[i][4] / 2, functionNumber);
            result[i][6] = h * functions.f(result[i][0] + h, result[i][1] + result[i][5], functionNumber);
        }

        for (int i = 4; i < n; i++) {
            result[i][0] = result[i - 1][0] + h;

            double y = result[i - 1][1] + h / 24 * (55 * result[i - 1][3] - 59 * result[i - 2][3] + 37 * result[i - 3][3] - 9 * result[i - 4][3]);
            double f = 0;
            double tmp = 0;
            while (Math.abs(y - tmp) > eps) {
                tmp = y;
                f = functions.f(result[i][0], tmp, functionNumber);
                y = result[i - 1][1] + h / 24 * (9 * f + 19 * result[i - 1][3] - 5 * result[i - 2][3] + result[i - 3][3]);
            }
            result[i][1] = y;
            result[i][2] = f;
        }

        return result;
    }
}
