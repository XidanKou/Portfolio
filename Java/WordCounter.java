import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Put a short phrase describing the program here.
 *
 * @author Put your name here
 *
 */
public final class WordCounter {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private WordCounter() {
    }

    /**
     * Compare {@code String}s in lexicographic order.
     */
    private static class StringLT implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareToIgnoreCase(o2);
        }
    }

    /**
     * Generates the set of characters in the given {@code String} into the
     * given {@code Set}.
     *
     * @param str
     *            the given {@code String}
     * @param charSet
     *            the {@code Set} to be replaced
     * @replaces charSet
     * @ensures charSet = entries(str)
     */
    private static void generateElements(Set<Character> charSet) {
        //assert str != null : "Violation of: str is not null";
        assert charSet != null : "Violation of: charSet is not null";

        charSet.add('?');
        charSet.add('.');
        charSet.add(':');
        charSet.add(',');
        charSet.add(' ');
        charSet.add('-');
        charSet.add(';');
        charSet.add('!');
        charSet.add('/');
        charSet.add('\'');
        charSet.add('\t');
        charSet.add('\n');

    }

    /*
     * private static void generateElements(String str, Set<Character> charSet)
     * { assert str != null : "Violation of: str is not null"; assert charSet !=
     * null : "Violation of: charSet is not null";
     *
     * char elements; for (int i = 0; i < str.length(); i++) { elements =
     * str.charAt(i); charSet.add(elements);
     *
     * } }
     */
    /**
     * Get words in file and store words in {@code q}.
     *
     * @param in
     * @param q
     *            The queue to store every words
     * @param separatorSet
     *            set contains separators
     *
     * @ensures every single word in the file is stored in q
     */
    private static void getWord(SimpleReader in, Queue<String> q,
            Set<Character> separatorSet) {

        // while did not reach the end of the file
        while (!in.atEOS()) {
            String sentence = in.nextLine();
            // if next line is empty line, abstract sentence which is not empty
            while (sentence.equals("\n")) {
                sentence = in.nextLine();
            }
            // check for every single characters in sentence
            int start = 0; // start represents the first index of the word
            for (int i = 0; i < sentence.length(); i++) {

                // if the character is the separator
                int length = i + 1;
                if (separatorSet.contains(sentence.charAt(i))) {

                    //System.out.println(sentence.charAt(i - 1));
                    String word = sentence.substring(start, i);
                    if (word.length() > 0) {
                        if (!separatorSet.contains(word.charAt(0))) {
                            q.enqueue(word);
                            start = i + 1;
                            // store word in q
                        }

                    } else {
                        start++;
                    }

                } else if ((length == sentence.length())) {
                    String word = sentence.substring(start, i + 1);
                    q.enqueue(word);
                }
            }

        }

    }

    /**
     * count the frequency of each words. Stores the word and its frequency in
     * {@code frequency} without duplicate words.Return the queue which stores
     * words in alphabetical order without duplicate
     *
     * @param q
     *            the alphabetically sorted queue with duplicate words.
     * @param frequency
     *            the Map that will store the words and its frequency
     * @updates updates the {@code frequency}
     * @ensures |frequency| = |q|
     * @return return the queue which stores words in alphabetical order without
     *         duplicates
     */
    private static Queue<String> countFrequency(Queue<String> q,
            Map<String, Integer> frequency) {
        Queue<String> newQue = new Queue1L<String>();
        // frequency.clear();
        for (String word : q) {
            // check whether word is already in frequency
            if (!frequency.hasKey(word)) {
                frequency.add(word, 1);
                newQue.enqueue(word);
            } else {
                // get the old value first
                int n = frequency.value(word);
                frequency.replaceValue(word, n + 1);
            }
        }
        return newQue;

    }

    /**
     * Design the header and table border of the HTML file.
     *
     * @param out
     * @param fileName
     *            name of the output file
     * @ensures ensures the output HTML file contains the well-formed header and
     *          title
     */

    private static void outputHeader(SimpleWriter out, String fileName) {
        // output header
        out.println("<html>");
        out.println("<head>");
        out.println("<title> Words Counted in " + fileName);
        out.println("</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1> Words Counted in " + fileName);
        out.println("</h1>");
        out.println("<hr>"); // horizontal line
        // end of the header
        /*
         * design the table border
         */
        out.println("<style>");
        out.println("table, th, td {");
        out.println("border: 1px solid black;");
        out.println("}");
        out.println("</style>");
        // title row of the table
        out.println("<table style=\"width:10%\">");
        out.println("<tr>");
        out.println("<th> Words </th>");
        out.println("<th> Counts </th> ");
        out.println("</tr>");

    }

    /**
     * Creates the well-formed table with words and its occurrence.
     *
     * @param out
     *
     * @param frequency
     *            The Map which stores the words and its frequency
     * @param q
     *            alphabetically sorted words
     *
     */

    private static void mainHTML(SimpleWriter out,
            Map<String, Integer> frequency, Queue<String> q) {

        int n = q.length();
        for (int i = 0; i < n; i++) {
            String word = q.dequeue();
            // Map.Pair<String, Integer> pair = frequency.remove(word);
            int counts = frequency.value(word);
            q.enqueue(word);
            out.println("<tr>");
            out.println("<td>");
            out.println(word);
            out.println("</td>");
            out.println("<td>");
            out.println(counts);
            out.println("</td>");
            out.println("</tr>");
        }

        // out put footer
        out.println("</body>");
        out.println("</html>");

    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {

        SimpleWriter out = new SimpleWriter1L();
        SimpleReader in = new SimpleReader1L();
        out.print("Enter the name of the input file: ");
        String fileNameIn = in.nextLine();
        SimpleReader fileIn = new SimpleReader1L(fileNameIn);
        out.print("Enter the name of the file that you want to "
                + "saved the outputs in it: ");
        String fileNameOut = in.nextLine();
        SimpleWriter fileOut = new SimpleWriter1L(fileNameOut);
        /*
         * ask the user for the name of an input file and for the name of an
         * output file.
         */
        //final String separatorStr = ",.-! ?:;\t";
        // creates the separator string
        Set<Character> separatorSet = new Set1L<Character>();
        //creates the Set called separatorSet
        generateElements(separatorSet);
        // store separators in separatorSet

        Queue<String> q = new Queue1L<String>();
        // Creates the to store words
        getWord(fileIn, q, separatorSet);
        // get words and store them in q
        Comparator<String> cs = new StringLT();
        q.sort(cs);
        // sort words in alphabetical order
        Map<String, Integer> frequency = new Map1L<String, Integer>();
        Queue<String> newQue = countFrequency(q, frequency);

        outputHeader(fileOut, fileNameIn);
        mainHTML(fileOut, frequency, newQue);

        in.close();
        out.close();
        fileIn.close();
        fileOut.close();
    }

}
