import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InputOutput {
    public double getFirstForXE() throws Exception {
        double val = Double.parseDouble(ask("Введите начальное приближение X:"));
        return val;
    }

    public double getFirstForYE() throws Exception {
        double val = Double.parseDouble(ask("Введите начальное приближение Y:"));
        return val;
    }

    public double getFirstX(double a, double b) throws Exception {
        double val = Double.parseDouble(ask("Введите начальное приближение:"));
        if (a > val || b < val)
            throw new Exception("Недопустимо!");
        return val;
    }

    public double getA(Task t) throws Exception {
        return Double.parseDouble(ask("Введите левую границу."));
    }

    public double getAGraph() throws Exception {
        return Double.parseDouble(ask("Введите левую границу графика."));
    }

    public double getBGraph(double a) throws Exception {
        double b = Double.parseDouble(ask("Введите правую границу графика."));
        if (b < a)
            throw new Exception("Невозможно!");
        return b;
    }

    public double getB(Task t, double a) throws Exception {
        double b = Double.parseDouble(ask("Введите правую границу."));
        if (b < a)
            throw new Exception("Невозможно!");
        for (Equation e : t.getEquations())
            if (e.f(a) * e.f(b) > 0)
                throw new Exception("На данном промежутке нет корней или их несколько!");
        return b;
    }

    private boolean isFileInput = false;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public void resultOfL(double solution, double val, int iterations) {
        System.out.println("x:" + solution);
        System.out.println("f(x):" + val);
        System.out.println(iterations + " итераций");
    }

    public void resultOfE(double[] solution, double[] accuracies, int iterations) {
        System.out.println("Решение:");
        for (double v : solution) System.out.print(v + " ");
        System.out.println("\nПогрешности:");
        for (double accuracy : accuracies) System.out.print(accuracy + " ");
        System.out.println("\nИтераций: " + iterations);
    }

    public SolveRun setSolveType(Task t, ArrayList<Solve> solves) throws Exception {
        int q = 1;
        for (Solve solve : solves) {
            if (solve.isForE() == t.isForE()) {
                System.out.println(q + ": " + solve);
                q++;
            }
        }
        int n = Integer.parseInt(ask("Напишите индекс метода, которым нужно решить."));
        q = 1;
        for (Solve solve : solves) {
            if (solve.isForE() == t.isForE()) {
                if (n == q)
                    return solve.getSolveRun();
                q++;
            }
        }
        throw new Exception("Ошибка!");
    }

    private String ask(String str) throws Exception {
        if (!isFileInput)
            System.out.println(str);
        return reader.readLine();
    }

    public double getAccuracy() throws Exception {
        return Double.parseDouble(ask("Введите точность!"));
    }

    public void unsuccess(int iterations) {
        System.out.println("Решить за " + iterations + " не удалось!");
    }

    private Task selectE(List<Task> tasks) throws Exception {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ": " + tasks.get(i).toString());
            System.out.println("");
        }
        String s = ask("Напишите индекс системы, которую нужно решить.");
        return tasks.get(Integer.parseInt(s) - 1);
    }

    private Task selectL(List<Task> tasks) throws Exception {
        for (int i = 0; i < tasks.size(); i++)
            System.out.println(Integer.toString(i + 1) + ": " + tasks.get(i).toString());
        String s = ask("Напишите индекс уравнения, которое нужно решить.");
        return tasks.get(Integer.parseInt(s) - 1);
    }

    public Task selectTask(ArrayList<Task> tasks) throws Exception {
        String s = ask("Решить уравнение или систему?\nНапишите '1' для решения уравнения '2' для решения системы.");
        if (s.equals("1")) {
            return selectL(tasks.stream().filter(el -> !el.isForE()).collect(Collectors.toList()));
        } else if (!s.equals("2")) {
            throw new Exception("wrong key!");
        }
        return selectE(tasks.stream().filter(Task::isForE).collect(Collectors.toList()));
    }

    public void selectOutputMode() throws Exception {
        System.out.println("Вывод в файл или консоль?\nВведите '1' если консоль или '2' если файл.");
        String answer = "";
        answer = reader.readLine();
        if (answer.equals("2")) {
            /*System.out.println("Введите имя файла");
            String s = reader.readLine();*/
            System.out.println("Вывод в файле: " + "src/output");
            System.setOut(new PrintStream("src/output"));
            return;
        } else if (!answer.equals("1")) {
            throw new Exception("Неверный ввод!");
        }
    }

    public void selectInputMode() throws Exception {
        System.out.println("Ввод из файла или консоли?\nВведите '1' для консоли или '2' для файла.");
        String answer;
        answer = reader.readLine();
        if (answer.equals("2")) {
            isFileInput = true;
            System.out.println("Ввод в файле src/main/java/input");
            reader = new BufferedReader(new FileReader("src/main/java/input"));
        } else if (!answer.equals("1")) {
            throw new Exception("Неверный ввод!");
        }
    }
}
