import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.FormatChecker;

/**
 * Pseudoscience Lab.
 *
 * @author Elijah Paulman
 *
 */
public final class ABCDGuesser1 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private ABCDGuesser1() {
    }

    /**
     * Repeatedly asks the user for a positive real number until the user enters
     * one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number entered by the user
     */
    private static double getPositiveDouble(SimpleReader in, SimpleWriter out) {
        //intiailizes constant to keep loop repeating
        double constant = -1.0;
        //checks loop conditions
        while (constant <= 0.0) {
            //prompts user for positive constant
            out.println("Enter a positive constant to be approximated: ");
            String userInput = in.nextLine();
            //checks that input meets requirements
            if (FormatChecker.canParseDouble(userInput)) {
                constant = Double.parseDouble(userInput);
            }
        }
        //returns user entered positive constant
        return constant;
    }

    /**
     * Repeatedly asks the user for a positive real number not equal to 1.0
     * until the user enters one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number not equal to 1.0 entered by the user
     */
    private static double getPositiveDoubleNotOne(SimpleReader in,
            SimpleWriter out) {
        //initializes constant to keep loop repeating
        double userNum = -1.0;
        //checks for negative number or equal to one
        while (userNum <= 0.0 || userNum == 1.0) {
            //prompts user for positive message not equal to 1
            out.println("Enter a positive constant not equal to 1.0: ");
            String userInput = in.nextLine();
            //checks that input is a double
            if (FormatChecker.canParseDouble(userInput)) {
                userNum = Double.parseDouble(userInput);
            }
        }
        //returns user entered constants
        return userNum;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        //initialize input and output streams
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        double mu = getPositiveDouble(in, out);
        double w = getPositiveDoubleNotOne(in, out);
        double x = getPositiveDoubleNotOne(in, out);
        double y = getPositiveDoubleNotOne(in, out);
        double z = getPositiveDoubleNotOne(in, out);

        //multiplyer to create large value for initial error
        final int tens = 1000;
        //creates high value for error so lower guesses can override eventually
        double bestError = Math.pow(tens, tens);
        //initializes the best approximation for entered values
        double bestApprox = 0;
        //array for exponent combinations
        final double[] exponents = { -5, -4, -3, -2, -1, -0.5, -1.0 / 3.0, -.25,
                0, .25, 1.0 / 3.0, .5, 1, 2, 3, 4 };

        //initialize variables for the best combination of each exponent
        double bestA = 0, bestB = 0, bestC = 0, bestD = 0;
        int a = 0, b = 0, c = 0, d = 0;
        //runs through every combination of exponents
        while (a < exponents.length) {
            while (b < exponents.length) {
                while (c < exponents.length) {
                    while (d < exponents.length) {
                        //performs calc for approximation based on current a,b,c,d values
                        double approx = Math.pow(w, exponents[a])
                                * Math.pow(x, exponents[b])
                                * Math.pow(y, exponents[c])
                                * Math.pow(z, exponents[d]);
                        /*
                         * calculates error based on current a,b,c,d, values and
                         * approximation
                         */
                        double error = Math.abs((approx - mu) / mu);
                        /*
                         * if error is smaller than last iteration, assigns used
                         * variables as best values
                         */
                        if (error < bestError) {
                            bestA = exponents[a];
                            bestB = exponents[b];
                            bestC = exponents[c];
                            bestD = exponents[d];
                            bestApprox = approx;
                            bestError = error;
                        }
                        d++;
                    }
                    c++;
                    d = 0;
                }
                b++;
                c = 0;
            }
            a++;
            b = 0;
        }

        //displays results of calculations and prints to the user
        out.println("The best exponents are: \na = " + bestA + "\nb = " + bestB
                + "\nc = " + bestC + "\nd = " + bestD);
        out.println("The best approximation is " + bestApprox);
        final int percentMultiplyer = 100;
        out.println("The relative error is " + (bestError * percentMultiplyer)
                + "%");
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
