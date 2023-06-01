import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class Main {
    private static Approximator approximator;
    private static BufferedReader reader;
    private static double[][] functionTable;
    private static DrawChart frame;

    public static void main(String[] arg) throws IOException {
        approximator = new Approximator();
        frame = new DrawChart("График");
        getInputType();
        coordinatesCountInit();

        Result linear = approximator.linear(functionTable);
        System.out.println(linear);
        Result quadratic = approximator.square(functionTable);
        System.out.println(quadratic);
        Result exponential = approximator.exponential(functionTable);
        System.out.println(exponential);
        Result logarithmic = approximator.logarithmic(functionTable);
        System.out.println(logarithmic);
        Result power = approximator.power(functionTable);
        System.out.println(power);
        Result cubic = approximator.cubic(functionTable);
        System.out.println(cubic);

        List<Result> list = new ArrayList<>(List.of(linear, quadratic, exponential, logarithmic, power, cubic));
        list.sort(Comparator.comparingDouble(Result::getDeviation));

        System.out.println("Лучшая аппроксимация " + list.get(0).getTypeString()
        + "\n" + list.get(0).functionToString);
        frame.graph(functionTable[0][0] - 2,
                functionTable[functionTable.length - 1][0] + 2,
                list.get(0).getFunction());
    }

    private static void coordinatesCountInit() throws IOException {
        int amount;
        while (true) {
            System.out.println("Введите количество координат >= 8");
            try {
                amount = Integer.parseInt(reader.readLine());
                if (amount < 8) {
                    continue;
                }
                functionTable = new double[amount][2];
                break;
            } catch (NumberFormatException e) {
                System.err.println("Ошипка ввода");
            }
        }
        initCoordinates(amount);
    }

    private static void initCoordinates(int pairs) throws IOException {
        int i = 0;
        while (i < pairs) {
            try {
                double[] pair = Stream.of(reader.readLine().split(" ")).mapToDouble(Double::parseDouble).toArray();
                if (pair.length != 2) {
                    System.err.println("Введите 2 числа в строке X Y:");
                    continue;
                }
                functionTable[i] = pair;
                i++;
            } catch (NumberFormatException e) {
                System.err.println("Ошипка ввода");
            }
        }
    }

    private static void getInputType() throws IOException {
        reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Ввод с консоли? 1 - да, 2 - нет");
        String input = reader.readLine();
        if (!input.equals("1")) {
            while (true) {
                try {
                    reader = new BufferedReader(new FileReader("src/input"));
                    break;
                } catch (FileNotFoundException e) {
                    System.out.println("Ошипка");
                }
            }
        }
    }
}