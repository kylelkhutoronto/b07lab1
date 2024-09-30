import java.io.*;

public class Driver {
    public static void main(String [] args) {
        //constructor & evaluation
        Polynomial p = new Polynomial();
        System.out.println("empty polynomial p(3): expect 0, got: " + p.evaluate(3));
        //expect 0

        double [] c1 = {6,0,0,5};
        int [] e1 = {0,6,2,6};
        Polynomial p1 = new Polynomial(c1,e1);
        System.out.println("polynomial coefficients and exponents p1(1): expect 11, got: " + p1.evaluate(1));
        //expect 11

        double [] c2 = {0,-2,0,0,-9};
        int [] e2 = {1,2,0,2,4};
        Polynomial p2 = new Polynomial(c2,e2);
        System.out.println("polynomial coefficients and exponents p2(2): expect -152, got: " + p2.evaluate(2));
        //expect -152

        double [] c3 = {3,0,5};
        int [] e3 = {2,3,1};
        Polynomial p3 = new Polynomial(c3,e3);
        System.out.println("polynomial coefficients and exponents p3(1): expect 8, got: " + p3.evaluate(1));
        //expect 8

        double [] c4 = {-1,4,0};
        int [] e4 = {1,3,0};
        Polynomial p4 = new Polynomial(c4,e4);
        System.out.println("polynomial coefficients and exponents p4(2): expect 30, got: " + p4.evaluate(2));
        //expect 30

        //edge cases - 0 coefficients
        double [] c5 = {0,0,0};
        int [] e5 = {0,1,2};
        Polynomial p5 = new Polynomial(c5,e5);
        System.out.println("polynomial coefficients and exponents p5(3): expect 0, got: " + p5.evaluate(3));
        //big values and big exponents
        double [] c6 = {1000,-5000,2000};
        int [] e6 = {100,50,10};
        Polynomial p6 = new Polynomial(c6,e6);
        System.out.println("polynomial coefficients and exponents p6(2): expect ~1.2676506E33, got: " + p6.evaluate(2));


        double [] c7 = {6,-2,5};
        int [] e7 = {0,1,3};
        Polynomial p7 = new Polynomial(c7,e7);

        double [] c8 = {3,0,4};
        int [] e8 = {3,2,8};
        Polynomial p8 = new Polynomial(c8,e8);


        //addition
        Polynomial s = p1.add(p2);
        System.out.println("addition s=p1+p2, then s(0.1): expect ~5.98, got: " + s.evaluate(0.1));

        Polynomial s2 = p3.add(p4);
        System.out.println("addition s2=p3+p4, then s2(0.1): expect ~0.434, got: " + s2.evaluate(0.1));

        Polynomial s3 = p7.add(p8);
        System.out.print("\naddition s3=p7+p8: expect poly values: 6,-2,8,4, got: ");
        for (int i = 0; i < s3.poly.length; i++) {
            System.out.print(s3.poly[i]+",");
        }
        System.out.print("\naddition s3=p7+p8: expect expo values: 0,1,3,8, got:");
        for (int i = 0; i < s3.expo.length; i++) {
            System.out.print(s3.expo[i]+",");
        }

        //multiplication
        Polynomial m = p1.multiply(p2);
        System.out.println("\n\nmultiplication m=p1*p2, then m(2): expect -49552, got: " + m.evaluate(2));

        Polynomial m2 = p3.multiply(p4);
        System.out.println("multiplication m2=p3*p4, then m2(2): expect 660, got: " + m2.evaluate(2));

        //root
        System.out.println("\nroot test 1: expect '1 is a root of s', got: ");
        if(s.hasRoot(1))
            System.out.println("1 is a root of s");
        else
            System.out.println("1 is not a root of s");

        System.out.println("\nroot test 2: expect '1 is not a root of s2', got: ");
        if(s2.hasRoot(1))
            System.out.println("1 is a root of s2");
        else
            System.out.println("1 is not a root of s2");

        //file reading and saving
        System.out.println("\nfile reading and saving, reading from 'polynomial.txt', outputs s2 to 'result.txt'");
        try {
            Polynomial filePoly = new Polynomial(new File("polynomial.txt"));
            System.out.println("polynomial from file filePoly(2): expect 30, got: " + filePoly.evaluate(2));

            s2.saveToFile("result.txt");
            System.out.println("Saved polynomial s2 to 'result.txt', expect '4x+3x2+4x3' in file");
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        catch (IOException e) {
            System.out.println("IO Exception");
        }
    }
}