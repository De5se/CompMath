public class Diverg {
    public static double getVal(Equation e, double where) {
        return (e.f(where + 0.0000001) - e.f(where)) / 0.0000001;
    }

    public static double getDoubleVal(Equation e, double where) {
        return (getVal(e, where + 0.0000001) - getVal(e, where)) / 0.0000001;
    }

    public static DoubleEquation diffX(DoubleEquation doubleEquation) {
        return (x, y) -> ((doubleEquation.f(x + 0.0000001, y) - doubleEquation.f(x, y)) / 0.0000001);
    }

    public static DoubleEquation diffY(DoubleEquation doubleEquation) {
        return (x, y) -> ((doubleEquation.f(x, y + 0.0000001) - doubleEquation.f(x, y)) / 0.0000001);
    }
}
