import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;

public class SLAUSolver {
    float[][] arrayA;
    float[] arrayB;
    float[] arrayX;
    float[] arrayR;
    int n;

    public void run() {
        inputMatrix();
        solve();
        printErrors();
    }

    private void inputMatrix(){
        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
        System.out.println("Ввод с файла(+) или консоли(любой другой символ)?");

        boolean isFileInput = Objects.equals(scanner.next(), "+");

        if (isFileInput){
            System.out.println("Введите название файла: ");
            File file = new File(scanner.next());

            try {
                scanner = new Scanner(file).useLocale(Locale.US);;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        if (!isFileInput)
            System.out.println("Введите n: ");
        n = scanner.nextInt();

        arrayA = new float[n][n];
        arrayB = new float[n];

        if (!isFileInput)
            System.out.println("Введите матрицу: ");

        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                arrayA[i][j] = scanner.nextFloat();
            }
            arrayB[i] = scanner.nextFloat();
        }
    }

    private void solve() {
        //прямой ход
        for (int i = 0; i < n - 1; i++) {
            chooseMainElement(i);
            for (int k = i + 1; k < n; k++) {
                float c = arrayA[k][i] / arrayA[i][i];
                for (int j = i; j < n ; j++) {
                    arrayA[k][j] -= c * arrayA[i][j];
                }
                arrayB[k] -= c * arrayB[i];
            }
        }

        System.out.println("Преобразованная СЛАУ: \n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(arrayA[i][j] + "\t");
            }
            System.out.print("\t|" + arrayB[i] + "\n");
        }
        System.out.println();

        //обратный ход
        arrayX = new float[n];
        for (int i = n - 1; i > -1; i--) {
            float s = 0;
            for (int j = i + 1; j < n; j++) {
                s = s + arrayA[i][j] * arrayX[j];
            }
            arrayX[i] = (arrayB[i] - s) / arrayA[i][i];
        }

        printResult();
    }

    private void chooseMainElement(int currentIndex) {
        for (int i = currentIndex; i < n - 1; i++) {
            int indexOfMax = i;
            for (int j = i + 1; j < n; j++) {
                if (Math.abs(arrayA[j][i]) > Math.abs(arrayA[indexOfMax][i])) {
                    indexOfMax = j;
                }
            }
            if (indexOfMax != i) {
                for (int j = i; j < n; j++) {
                    float a = arrayA[i][j];
                    arrayA[i][j] = arrayA[indexOfMax][j];
                    arrayA[indexOfMax][j] = a;
                }
                float a = arrayB[i];
                arrayB[i] = arrayB[indexOfMax];
                arrayB[indexOfMax] = a;
            }
        }
    }

    private void printResult() {
        for (int i = 0; i < n; i++) {
            System.out.println("x" + (i + 1) + " = " + arrayX[i] + " ~ " + Math.round(arrayX[i]));
        }
        System.out.println();
    }

    //Вычисление вектора невязок
    private void getErrors() {
        arrayR = new float[n];
        for (int i = 0; i < n; i++) {
            float s = 0;
            for (int j = 0; j < n; j++) {
                s = s + arrayA[i][j] * arrayX[j];
            }
            arrayR[i] = s - arrayB[i];
        }
    }

    public void printErrors() {
        getErrors();
        System.out.println("Вектор невязок: ");
        for (int i = 0; i < n; i++) {
            System.out.print(arrayR[i] + "\t");
        }
    }
}
