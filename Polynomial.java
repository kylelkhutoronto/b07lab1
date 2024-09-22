//CSCB07 Lab 1 - Kyle Lam (1008177090), September 22, 2024

public class Polynomial {
    //field to represent coefficients of the polynomial using an array of double
    double[] poly;

    //initialize Polynomial without args - sets corresponding array to [0]
    public Polynomial() {
        poly = new double[]{0};
    }
    //initialize Polynomial with args - sets corresponding array according to the inputs array
    public Polynomial(double[] inputs) {
        poly = inputs;
    }
    //add two polynomials - finds max polynomial length, returns new Polynomial with sum of coefficients at each degree
    public Polynomial add(Polynomial addPoly) {
        int maxPolyLen = Math.max(this.poly.length, addPoly.poly.length);
        double[] newPoly = new double[maxPolyLen];

        for (int i = 0; i < maxPolyLen; i++) {
            double coeff;
            double coeffAdd;

            if (i < this.poly.length) coeff = this.poly[i];
            else coeff = 0;

            if (i < addPoly.poly.length) coeffAdd = addPoly.poly[i];
            else coeffAdd = 0;

            newPoly[i] = coeff + coeffAdd;
        }
        return new Polynomial(newPoly);
    }
    //evaluate polynomial - returns the result of polynomial according to argument value x and increasing exponent
    public double evaluate(double x) {
        double result = 0;
        for (int i = 0; i < poly.length; i++) {
            result += poly[i] * Math.pow(x, i);
        }
        return result;
    }
    //check root - returns true/false if argument value x is a root of the polynomial (equals 0 when evaluated with x)
    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }
}