/**
 * Project 1 (approach 2) for CECS 328
 * @author Mark Garcia
 *         Mark.Garcia.8001@gmail.com
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Scanner;
import java.io.PrintWriter;

public class Main {

    /**
     * ----------------------- FROM INSTRUCTION -----------------------
     * It can be proved that every possible fraction eventually appears once and
     * only once in this tree.  Input to your program will be a "large" fraction M/N
     * where M and N are large. (You may use the java.BigInteger package if you want.)
     * Output from your program will be the first fraction a/b in the tree such that
     * a/b ~ sqrt(M/N) in the following sense: a/b must be the first fraction in the tree such that
     * |Na^2 - Mb^2| < b
     * Output will be two lines, a and then b.
     *  ----------------------- FROM INSTRUCTION -----------------------
     */

    public static void main(String[] args){
        BigInteger M; BigInteger N; Fraction solution;
        try {
            long start = System.currentTimeMillis();
            //take inputs from file
            File fin = new File("input1.txt");
            Scanner scan = new Scanner(fin);
            M = new BigInteger(scan.nextLine());
            N = new BigInteger(scan.nextLine());
            System.out.println("Given Fraction : [" + M.toString() + "," + N.toString() + "]");
            //find the fricken fraction
            solution = findFraction(M,N);
            //write output file
            File fout = new File("output5.txt");
            PrintWriter pw = new PrintWriter(fout);
            pw.write(solution.num.toString().stripTrailing() + "\n");
            pw.write(solution.den.toString().stripTrailing());
            pw.close();
            long end = System.currentTimeMillis();
            System.out.println("RUNTIME: " + (end - start) + " millis");
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }

    }

    /**
     * Adds two fractions together until desired target is found.
     * @param M input numerator from file
     * @param N input denominator from file
     * @return the target fraction from the given M and N values
     */
    public static Fraction findFraction(BigInteger M, BigInteger N){
        int counter = 1; // num of iterations
        // at most only two fractions will need to be used.
        // DO NOT TRY TO MAKE AN ACTUAL TREE. (see attempt 1)
        // THE INSTRUCTIONS DONT SAY MAKE A TREE AND KEEP EVERY VALUE FOR REFERENCE LATER
        Fraction[] fractions = new Fraction[2];
        fractions[0] = new Fraction(new BigInteger("0"), new BigInteger("1")); //start with 0,1 on the left
        fractions[1] = new Fraction(new BigInteger("1"), new BigInteger("0")); //start with 1,0 on the right

        while(0==0){
            System.out.println("ATTEMPT: " + counter);
            // (a1+a2) / (b1+b2)
            Fraction newFrac = addFraction(fractions[0], fractions[1]);
            // Na^2
            BigInteger left = N.multiply(newFrac.num.pow(2));
            // Mb^2
            BigInteger right = M.multiply(newFrac.den.pow(2));
            // |Na^2 - Mb^2|
            BigInteger absDif = (left.subtract(right)).abs();
            //print out current fractions at hand
            System.out.println(fractions[0].toString() + fractions[1].toString() + newFrac.toString());
            //MAKE COMPARISONS OF NEW FRAC AND TARGET
            if (absDif.compareTo(newFrac.den) < 0) {
                //means target is exactly/approximately what was found |Na^2 - Mb^2| < b holds true
                return newFrac;
            }  else if (left.compareTo(right) < 0) {
                //means target is going to be less than what ever was tested. keep smaller fraction. move left on "tree"
                fractions[0] = newFrac;
            } else if (left.compareTo(right) > 0){
                //means target is going to be greater than what ever was tested. keep bigger fraction. move right on "tree"
                fractions[1] = newFrac;
            } //end comparisons
            counter++;

        } //end loop
    }

    /**
     * Class that holds 2 BigInts, a numerator and a denominator
     */
    public static class Fraction implements Comparable<Fraction> {

        private BigInteger num;
        private BigInteger den;

        /**
         * Parameterized Constructor
         * Creates Fraction in the form (numerator, denominator)
         * @param numerator
         * @param denominator
         */
        public Fraction(BigInteger numerator, BigInteger denominator){
            this.num = numerator;
            this.den = denominator;
        }

        /**
         *
         * @param o
         * @return 0,1,-1 depending on valid comparison outcome or 2 if something goes wrong somehow
         */
        @Override
        public int compareTo(Fraction o) {
            //numerators the same
            if (this.num.compareTo(o.num) == 0){
                if (this.den.compareTo(o.den) == 0){
                    return 0; // fractions are the same
                } else if (this.den.compareTo(o.den) < 0) {
                    return -1; // this is less than that
                } else if (this.den.compareTo(o.den) > 0) {
                    return 1; // this is greater than that
                }
            //numerator for this is less than that
            } else if (this.num.compareTo(o.num) < 0) {
                return -1;
            //numerator for this is greater than that
            } else if (this.num.compareTo(o.num) > 0) {
                return 1;
            }

            return 2; // this will never happen but i need a return statement out of the if/else
        }

        @Override
        public String toString(){
            return ("[" + this.num.toString() + "," + this.den.toString() + "] ");
        }

    }

    /**
     * Add 2 fractions numerators and denominators to create a new fraction
     * (a1+a2) / (b1+b2) where a's are the fractions numerators and b's are the fractions denominators
     * @param left Fraction
     * @param right Fraction
     * @return sum of fractions
     */
    public static Fraction addFraction(Fraction left, Fraction right){
        Fraction sum = new Fraction((left.num.add(right.num)), left.den.add(right.den));
        return sum;
    }
}
