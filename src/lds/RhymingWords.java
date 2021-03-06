package lds;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Map.Entry;
import java.util.StringTokenizer;

public class RhymingWords {

    public static void main(String[] args) throws IOException {


        FileReader wordFile = new FileReader("/home/enxhi/Desktop/prob");

        Reader rhymingwords = reverseOrder(sort(reverseOrder(wordFile)));

        BufferedReader inp = new BufferedReader(rhymingwords);

        String input;

        while ((input = inp.readLine()) != null) {

            System.out.println(input);

        }



        inp.close();
    }

    public static Reader reverseOrder(Reader src) throws IOException {


        BufferedReader input = new BufferedReader(src);


        PipedWriter pipeOut = new PipedWriter();

        PipedReader pipeIn = new PipedReader(pipeOut);

        PrintWriter out = new PrintWriter(pipeOut);


        new ReverseThread(out, input).start();


        return pipeIn;
    }

    public static Reader sort(Reader src) throws IOException {


        BufferedReader input = new BufferedReader(src);


        PipedWriter pipeOut = new PipedWriter();

        PipedReader pipeIn = new PipedReader(pipeOut);

        PrintWriter out = new PrintWriter(pipeOut);


        new SortThread(out, input).start();


        return pipeIn;
    }

    static Pair find(String s, File directory) throws IOException {
        BufferedReader wod = new BufferedReader(new FileReader(directory));
        int l = 0, p = 0;
        String wline;
        while ((wline = wod.readLine()) != null) {
            StringTokenizer token = new StringTokenizer(wline, " ,.;'\n");
            while (token.hasMoreElements()) {
                if (token.nextToken().equals(s)) {
                    // System.out.println("hhhelloo");
                    return new Pair(l, p);
                }
                p++;
            }
            l++;
        }
        return new Pair(l, p);
    }
}

class SortThread extends Thread {

    private PrintWriter output = null;
    private BufferedReader input = null;

    public SortThread(PrintWriter output, BufferedReader input) {

        this.output = output;

        this.input = input;
    }

    @Override
    public void run() {

        int MAXWORDS = 1000;


        if (output != null && input != null) {


            try {

                String[] words = new String[MAXWORDS];
                int numwords = 0;

                while ((words[numwords] = input.readLine()) != null) {

                    numwords++;

                }

                quicksort(words, 0, numwords - 1);

                for (int i = 0; i < numwords; i++) {
                    output.println(words[i]);

                }

                output.close();

            } catch (IOException e) {

                System.err.println("Sort: " + e);


            }

        }
    }

    private static void quicksort(String[] str, int low0, int high0) {

        int loPart = low0;

        int hiPart = high0;


        if (loPart >= hiPart) {


            return;

        }

        String middle = str[(loPart + hiPart) / 2];

        while (loPart < hiPart) {

            while (loPart < hiPart && str[loPart].compareTo(middle) < 0) {

                loPart++;

            }

            while (loPart < hiPart && str[hiPart].compareTo(middle) > 0) {

                hiPart--;

            }

            if (loPart < hiPart) {

                String T = str[loPart];
                str[loPart] = str[hiPart];
                str[hiPart] = T;
                loPart++;
                hiPart--;
            }

        }

        if (hiPart < loPart) {
            int T = hiPart;
            hiPart = loPart;
            loPart = T;

        }

        quicksort(str, low0, loPart);

        quicksort(str, loPart == low0 ? loPart + 1 : loPart, high0);
    }
}

class ReverseThread extends Thread {

    private PrintWriter output = null;
    private BufferedReader inputre = null;

    public ReverseThread(PrintWriter out, BufferedReader in) {

        this.output = out;

        this.inputre = in;
    }

    @Override
    public void run() {

        if (output != null && inputre != null) {


            try {
                String input;
                while ((input = inputre.readLine()) != null) {
                    output.println(reverseIt(input));
                    output.flush();
                }

                output.close();

            } catch (IOException e) {


                System.err.println("Reverse: " + e);


            }

        }
    }

    private String reverseIt(String source) {

        int it, len = source.length();
        StringBuffer target = new StringBuffer(len);

        for (it = (len - 1); it >= 0; it--) {

            target.append(source.charAt(it));

        }

        return target.toString();
    }
}
