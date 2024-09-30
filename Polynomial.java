//CSCB07 Lab 2 - Kyle Lam (1008177090), September 29, 2024
import java.io.*;
import java.util.Scanner;

public class Polynomial {
    //fields to represent coefficients of the polynomial using an array of double and exponents using an array of int
    double[] poly;
    int[] expo;

    //initialize Polynomial without args - sets corresponding poly and expo arrays to [0]
    public Polynomial() {
        poly = new double[]{0};
        expo = new int[]{0};
    }
    //initialize Polynomial with args - sets corresponding poly and expo arrays according to inputs arrays
    public Polynomial(double[] inputs, int[] expoInputs) {
        poly = inputs;
        expo = expoInputs;
    }
    //initialize Polynomial with input file - reads file, splits into terms and parts of terms, sets poly and expo arrays accordingly
    public Polynomial(File inputFile) throws FileNotFoundException {
        Scanner file = new Scanner(inputFile);
        String content = file.nextLine();
        //converts "-" into "+-" for easier splitting, splits with "\\+" to ensure it is the "+" character and not regex
        String modContent = content.replace("-", "+-");
        //removes redundant "+" from first term if it was negative but got its "-" replaced with "+-"
        if (modContent.startsWith("+")) {
            modContent = modContent.substring(1);
        }
        String[] terms = modContent.split("\\+");
        //number of terms after split is the size of polynomial
        poly = new double[terms.length];
        expo = new int[terms.length];
        //iterates the terms array, if a term has "x" then split the term one more time, parse into poly and expo arrays accordingly
        for (int i = 0; i < terms.length; i++) {
            if (terms[i].contains("x")) {
                String[] termSplit = terms[i].split("x");

                if (termSplit[0].equals("")) {
                    poly[i] = 1;
                }
                else if (termSplit[0].equals("-")) {
                    poly[i] = -1;
                }
                else {
                    poly[i] = Double.parseDouble(termSplit[0]);
                }

                if (termSplit.length > 1 && !termSplit[1].equals("")) {
                    expo[i] = Integer.parseInt(termSplit[1]);
                }
                else {
                    expo[i] = 1;
                }
            }
            else {
                poly[i] = Double.parseDouble(terms[i]);
                expo[i] = 0;
            }
        }
    }
    //helper - sort polynomial to ascending order in exponent degree
    public void sortExpo() {
        for (int i = 0; i < expo.length - 1; i++) {
            for (int j = 0; j < expo.length - i - 1; j++) {
                if (expo[j] > expo[j+1]) {
                    int tempExpo = expo[j+1];
                    double tempPoly = poly[j+1];

                    expo[j+1] = expo[j];
                    poly[j+1] = poly[j];

                    expo[j] = tempExpo;
                    poly[j] = tempPoly;
                }
            }
        }
    }
    //add two polynomials - returns new Polynomial with sum of coefficients at each degree
    public Polynomial add(Polynomial addPoly) {
        //sort polynomials for easier addition
        this.sortExpo();
        addPoly.sortExpo();
        //creates temporary polynomial to hold addition results
        //worst case: addition creates polynomial with no like terms
        int maxPolyLen = this.poly.length + addPoly.poly.length;
        double[] tempPoly = new double[maxPolyLen];
        int[] tempExpo = new int[maxPolyLen];
        //keeps track of the size of temporary polynomial
        int newPolyIndex = 0;

        int i = 0;
        int j = 0;
        //addition of polynomials: traverses both polynomials until it reaches the end of one of them
        //if like term found between two polynomials, add them together and adds into temporary polynomial if coefficient is not zero
        //if exponent in one term is smaller than the exponent in the other term (no like terms), adds the term with smaller exponent if coefficient is not zero
        while (i < this.poly.length && j < addPoly.poly.length) {
            if (this.expo[i] == addPoly.expo[j]) {
                double newCoeff = this.poly[i] + addPoly.poly[j];
                if (newCoeff != 0) {
                    tempPoly[newPolyIndex] = newCoeff;
                    tempExpo[newPolyIndex] = this.expo[i];
                    newPolyIndex++;
                }
                i++;
                j++;
            }
            else if (this.expo[i] < addPoly.expo[j]) {
                if (this.poly[i] != 0) {
                    tempPoly[newPolyIndex] = this.poly[i];
                    tempExpo[newPolyIndex] = this.expo[i];
                    newPolyIndex++;
                }
                i++;
            }
            else {
                if (addPoly.poly[j] != 0) {
                    tempPoly[newPolyIndex] = addPoly.poly[j];
                    tempExpo[newPolyIndex] = addPoly.expo[j];
                    newPolyIndex++;
                }
                j++;
            }
        }
        //adds remainder terms into temporary polynomial
        while (i < this.poly.length) {
            if (this.poly[i] != 0) {
                tempPoly[newPolyIndex] = this.poly[i];
                tempExpo[newPolyIndex] = this.expo[i];
                newPolyIndex++;
            }
            i++;
        }
        while (j < addPoly.poly.length) {
            if (addPoly.poly[j] != 0) {
                tempPoly[newPolyIndex] = addPoly.poly[j];
                tempExpo[newPolyIndex] = addPoly.expo[j];
                newPolyIndex++;
            }
            j++;
        }
        //creates result polynomial with actual size of temporary polynomial
        double[] newPoly = new double[newPolyIndex];
        int[] newExpo = new int[newPolyIndex];
        //parses the values of temporary polynomial into result polynomial
        for (int n = 0; n < newPolyIndex; n++) {
            newPoly[n] = tempPoly[n];
            newExpo[n] = tempExpo[n];
        }
        return new Polynomial(newPoly, newExpo);
    }
    //evaluate polynomial - returns the result of polynomial according to argument value x and corresponding exponent
    public double evaluate(double x) {
        double result = 0;
        for (int i = 0; i < poly.length; i++) {
            result += poly[i] * Math.pow(x, expo[i]);
        }
        return result;
    }
    //check root - returns true/false if argument value x is a root of the polynomial (equals 0 when evaluated with x)
    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }
    //multiply two polynomials - multiplies polynomials into one polynomial, then combine like terms and return new polynomial
    public Polynomial multiply(Polynomial multiplyPoly) {
        //creates temporary polynomial to hold multiplication results
        //worst case: multiplication creates polynomial with no like terms
        int maxPolyLen = this.poly.length * multiplyPoly.poly.length;
        double[] tempPoly = new double[maxPolyLen];
        int[] tempExpo = new int[maxPolyLen];
        //keeps track of the size of temporary polynomial
        int tempPolyIndex = 0;
        //multiplication of polynomial and adds into temporary polynomial if coefficient is not zero
        for (int i = 0; i < this.poly.length; i++) {
            for (int j = 0; j < multiplyPoly.poly.length; j++) {
                double newCoeff = this.poly[i] * multiplyPoly.poly[j];
                int newExpo = this.expo[i] + multiplyPoly.expo[j];

                if (newCoeff != 0) {
                    tempPoly[tempPolyIndex] = newCoeff;
                    tempExpo[tempPolyIndex] = newExpo;
                    tempPolyIndex++;
                }
            }
        }
        //creates another temporary polynomial to hold like terms combination results
        //worst case: same size as previous temporary polynomial
        double[] combPoly = new double[tempPolyIndex];
        int[] combExpo = new int[tempPolyIndex];
        //keeps track of the size of temporary polynomial
        int combPolyIndex = 0;
        //combination of like terms - iterates through itself to find and combine like terms, adds into result polynomial if coefficient is not zero
        for (int i = 0; i < tempPolyIndex; i++) {
            //double totalPoly = tempPoly[i];
            for (int j = i+1; j < tempPolyIndex; j++) {
                //if like term found, combines into original term, resets like term to 0 after combination
                if (tempExpo[i] == tempExpo[j]) {
                    tempPoly[i] += tempPoly[j];
                    tempPoly[j] = 0;
                }
            }
            if (tempPoly[i] != 0) {
                combPoly[combPolyIndex] = tempPoly[i];
                combExpo[combPolyIndex] = tempExpo[i];
                combPolyIndex++;
            }
        }
        //creates result polynomial with actual size of temporary polynomial
        double[] newPoly = new double[combPolyIndex];
        int[] newExpo = new int[combPolyIndex];
        //parses the values of temporary polynomial into result polynomial
        for (int n = 0; n < combPolyIndex; n++) {
            newPoly[n] = combPoly[n];
            newExpo[n] = combExpo[n];
        }
        return new Polynomial(newPoly, newExpo);
    }
    //save to a file - writes polynomial according to polynomial and exponent terms into a file
    public void saveToFile(String fileName) throws IOException {
        FileWriter output = new FileWriter(fileName);
        String outPoly = "";
        //iterates through polynomial, concatenate symbols and values into string accordingly for output
        for (int i = 0; i < poly.length; i++) {
            if (poly[i] != 0) {
                if (outPoly.length() > 0 && poly[i] > 0) {
                    outPoly = outPoly.concat("+");
                }

                if (poly[i] == -1 && expo[i] != 0) {
                    outPoly = outPoly.concat("-");
                }
                else if (poly[i] != 1 || expo[i] == 0) {
                    outPoly = outPoly.concat(Double.toString(poly[i]));
                }

                if (expo[i] != 0) {
                    outPoly = outPoly.concat("x");
                    if (expo[i] != 1) {
                        outPoly = outPoly.concat(Integer.toString(expo[i]));
                    }
                }
            }
        }
        //writes string into output file and saves the file
        output.write(outPoly);
        output.close();
    }
}