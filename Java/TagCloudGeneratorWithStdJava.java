import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Read .txt file and count the word. After that the result shows some number of
 * words which high frequency with alphabetical order through the .html file.
 * Its words' size is proportional to the frequency.
 *
 * @author Chaeun Hong，Xidan Kou, Jackie Z. Cheng
 *
 */
public final class TagCloudGeneratorWithStdJava {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private TagCloudGeneratorWithStdJava() {
    }

    /**
     * the list of separators.
     */
    static final String SEPARATORS = " ,.;:-_`!?~@#$%^&*()<>/|+=\\\"\'\t\n[] {}";

    /**
     * The smallest word frequency in the .txt file.
     */
    private static int min = 1;
    /**
     * The largest word frequency in the .txt file.
     */
    private static int max = 1;
    /**
     * The largest font size for the most frequently word.
     */
    private static final int MAX_FONT_SIZE = 48;
    /**
     * The smallest font size for the least frequently word.
     */
    private static final int MIN_FONT_SIZE = 11;

    /**
     * Set up an alphabetical order comparator.
     */
    private static Comparator<Map.Entry<String, Integer>> //
    alphabeticalOrder = new Comparator<Map.Entry<String, Integer>>() {
        @Override
        public int compare(Map.Entry<String, Integer> o1,
                Map.Entry<String, Integer> o2) {
            return o1.getKey().compareToIgnoreCase(o2.getKey());
        }
    };

    /**
     * Set up an numerical order comparator.
     */
    private static Comparator<Map.Entry<String, Integer>> //
    numericalOrder = new Comparator<Map.Entry<String, Integer>>() {
        @Override
        public int compare(Map.Entry<String, Integer> o1,
                Map.Entry<String, Integer> o2) {
            return o2.getValue().compareTo(o1.getValue());
        }
    };

    /**
     * Generates the set of characters in the given {@code String} into the
     * given {@code Set}.
     *
     * @param str
     *            the given {@code String}
     * @param strSet
     *            the {@code Set} to be replaced
     * @replaces strSet
     * @ensures strSet = entries(str)
     */
    private static void generateElements(String str, Set<Character> strSet) {
        assert str != null : "Violation of: str is not null";
        assert strSet != null : "Violation of: strSet is not null";

        int i = 0;
        char c = '.';
        while (i < str.length()) {
            if (!strSet.contains(str.charAt(i))) {
                c = str.charAt(i);
                strSet.add(c);
            }
            i++;
        }
    }

    /**
     * Returns the first "word" (maximal length string of characters not in
     * {@code separators}) or "separator string" (maximal length string of
     * characters in {@code separators}) in the given {@code text} starting at
     * the given {@code position}.
     *
     * @param text
     *            the {@code String} from which to get the word or separator
     *            string
     * @param position
     *            the starting index
     * @param separators
     *            the {@code Set} of separator characters
     * @return the first word or separator string found in {@code text} starting
     *         at index {@code position}
     * @requires 0 <= position < |text|
     * @ensures <pre>
     * nextWordOrSeparator =
     *   text[position, position + |nextWordOrSeparator|)  and
     * if entries(text[position, position + 1)) intersection separators = {}
     * then
     *   entries(nextWordOrSeparator) intersection separators = {}  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      intersection separators /= {})
     * else
     *   entries(nextWordOrSeparator) is subset of separators  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      is not subset of separators)
     * </pre>
     */
    private static String nextWordOrSeparator(String text, int position,
            Set<Character> separators) {
        int i = 0;
        char c = '.';
        StringBuilder result = new StringBuilder();

        if (separators.contains(text.charAt(position))) {
            while (i < text.substring(position, text.length()).length()) {
                c = text.charAt(position + i);
                if (separators.contains(c)) {
                    result.append(c);
                    i++;
                } else {
                    i = text.substring(position, text.length()).length();
                }
            }
            i = 0;
        } else {
            while (i < text.substring(position, text.length()).length()) {
                c = text.charAt(position + i);
                if (!separators.contains(c)) {
                    result.append(c);
                    i++;
                } else {
                    i = text.substring(position, text.length()).length();
                }
            }
        }
        return result.toString();
    }

    /**
     * Inputs .txt file which includes words and stores each word and the
     * frequency of each word in the given {@code Map}. Saves each words in a
     * sequence.
     *
     * @param fileName
     *            the name of the input file
     * @param wordCounterMap
     *            the word -> wordCounter map
     *
     * @replaces wordCounterMap
     * @replaces index
     * @requires <pre>
     * [file named fileName exists but is not open, and has the
     *  format of single term-one or more lines about the definition
     *  / terminated by empty line]
     * </pre>
     * @ensures [wordCounterMap contains words -> the frequency of the words
     *          mapping from file fileName]
     */
    private static void getWordCounterMap(String fileName,
            SortedMap<String, Integer> wordCounterMap) {
        assert fileName != null : "Violation of: fileName is not null";
        assert wordCounterMap != null : "Violation of: glossaryMap is not null";

        BufferedReader contents;
        try {
            contents = new BufferedReader(new FileReader(fileName));
        } catch (IOException e) {
            System.out.println("Error opening file");
            return;
        }

        Set<Character> separatorSet = new HashSet<Character>();
        generateElements(SEPARATORS, separatorSet);

        String sentence = null;
        try {
            sentence = contents.readLine();
            while (sentence != null) {

                int position = 0;
                while (position < sentence.length()) {
                    String tempToken = nextWordOrSeparator(sentence, position,
                            separatorSet);
                    String token = tempToken.toLowerCase();
                    if (!separatorSet.contains(token.charAt(0))) {
                        if (!wordCounterMap.containsKey(token)) {
                            wordCounterMap.put(token, 1);

                        } else {
                            int count = wordCounterMap.get(token);
                            wordCounterMap.replace(token, count, count + 1);
                            if (wordCounterMap.get(token) > max) {
                                max = wordCounterMap.get(token);
                            } else if (wordCounterMap.get(token) < min) {
                                min = wordCounterMap.get(token);
                            }
                        }
                    }
                    position += token.length();
                }
                sentence = contents.readLine();
            }

        } catch (IOException e) {
            System.err.println("Error from reading file");
        }

        try {
            contents.close();
        } catch (IOException e) {
            System.err.println("Error from closing file");
        }

    }

    /**
     * this method is organizing map in alphabetical order.
     *
     * @param wordCounterMap
     *            unsorted map paired with words and its frequency
     * @param number
     *            the number of words to be presented
     * @return sorted by the alphabetical order only for top frequently words
     * @replaces index
     * @requires <pre>
     * [Sequence must not empty and there is no same words]
     * </pre>
     * @ensures returned list organized alphabetical
     */
    private static List<Entry<String, Integer>> organizedWordCounter(
            SortedMap<String, Integer> wordCounterMap, int number) {
        assert number > 0 : "Violation of: number is greater than 0";

        List<Map.Entry<String, Integer>> numericalSortedTopRanked = //
                new ArrayList<Map.Entry<String, Integer>>();
        numericalSortedTopRanked.addAll(wordCounterMap.entrySet());
        Collections.sort(numericalSortedTopRanked, numericalOrder);

        List<Map.Entry<String, Integer>> sortedTopRanked = //
                new ArrayList<Map.Entry<String, Integer>>(number);
        for (int i = 0; i < number; i++) {
            sortedTopRanked.add(numericalSortedTopRanked.remove(0));
        }
        Collections.sort(sortedTopRanked, alphabeticalOrder);

        return sortedTopRanked;

    }

    /**
     * this method calculates the font size by frequency of each words.
     *
     * @param numberOfWords
     *            the number of frequency for each words
     * @return the font size for each words
     * @requires <pre>
     * input will greater than 0
     * </pre>
     * @ensures the word will get font size proportionally to its frequency
     */
    private static int getFontSize(int numberOfWords) {
        assert numberOfWords > 0 : "Violation of: numberOfWords is greater than 0";

        int fontSize = MIN_FONT_SIZE;
        if (numberOfWords == min) {
            fontSize = MIN_FONT_SIZE;
        } else if (numberOfWords == max) {
            fontSize = MAX_FONT_SIZE;
        } else {
            try {
                fontSize = MIN_FONT_SIZE + (numberOfWords - min)
                        * (MAX_FONT_SIZE - MIN_FONT_SIZE) / (max - min);
            } catch (ArithmeticException e) {
                System.err.println("Integer could not be divided by 0");
            }
        }
        return fontSize;
    }

    /**
     * output .html file to show the list of words with its frequency.
     *
     * @param sorted
     *            SortingMachine contained the sorted map
     * @param fileName
     *            name of input file
     * @param out
     *            the output stream
     * @updates out.content
     * @ensures out.content = #out.content * [html tags]
     */
    private static void outputIndex(List<Entry<String, Integer>> sorted,
            String fileName, PrintWriter out) {
        assert out != null : "Violation of: out is not null";

        out.println("<html>");
        out.println("<head>");
        out.println("<title> Top " + sorted.size() + " words in " + fileName
                + " </title>");
        out.println(
                "<link href=\"http://web.cse.ohio-state.edu/software/2231/web-sw2"
                        + "/assignments/projects/tag-cloud-generator/data/tagcloud.css\" "
                        + "rel=\"stylesheet\" type=\"text/css\">");
        out.println("</head>");

        out.println("<body>");
        out.println("<h2> Top " + sorted.size() + " words in " + fileName
                + "</h2>");
        out.println("<hr>");
        out.println("<div class=\"cdiv\">");
        out.println("<p class=\"cbox\">");

        while (sorted.size() > 0) {
            Entry<String, Integer> pair = sorted.remove(0);

            out.println("<span style=\"cursor:default\" class=\"f"
                    + getFontSize(pair.getValue()) + "\" title=\"count: "
                    + pair.getValue() + "\">" + pair.getKey() + "</span>");

        }
        out.println("</p>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");

    }

    /**
     * Checks whether the given {@code String} represents a valid integer value
     * in the range Integer.MIN_VALUE..Integer.MAX_VALUE.
     *
     * @param s
     *            the {@code String} to be checked
     * @return true if the given {@code String} represents a valid integer,
     *         false otherwise
     * @ensures canParseInt = [the given String represents a valid integer]
     */
    private static boolean canParseInt(String s) {
        assert s != null : "Violation of: s is not null";
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Check and return if the entered number is valid. If not, ask one more to
     * the user.
     *
     * @param in
     *            used for enter the number
     * @param wordCounterMap
     *            the map stored words and its counted number
     * @return the valid number for the number of words shown in .html file
     * @requires <pre>
     *  wordCounterMap has at least one entry
     * </pre>
     * @ensures checkValidNumber returns at least 0 to string
     */
    private static String checkValidNumber(BufferedReader in,
            SortedMap<String, Integer> wordCounterMap) {
        assert wordCounterMap
                .size() > 0 : "Violation of : wordCounterMap has at least one entry";

        String numberS = "0";
        try {
            System.out
                    .print("Enter the number of words you want to be shown: ");
            numberS = in.readLine();
            while (!canParseInt(numberS) || !(Integer.parseInt(numberS) >= 0)
                    || Integer.parseInt(numberS) > wordCounterMap.size()) {
                if (canParseInt(numberS) && Integer.parseInt(numberS) >= 0
                        && Integer.parseInt(numberS) > wordCounterMap.size()) {
                    System.out.print("Error - Number too large, maximum is "
                            + wordCounterMap.size()
                            + ", enter a smaller number: ");
                    numberS = in.readLine();

                } else {
                    System.out
                            .print("Error - please enter a positive number: ");
                    numberS = in.readLine();
                }

            }
        } catch (IOException e) {
            System.err.println("This is not a valid number");
        }

        return numberS;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {

        BufferedReader in = new BufferedReader(
                new InputStreamReader(System.in));

        SortedMap<String, Integer> wordCounterMap = new TreeMap<>();

        String s;

        try {
            System.out.print("Enter the file name to organizing : ");
            s = in.readLine();
            getWordCounterMap(s, wordCounterMap);
        } catch (IOException e) {
            System.out.println("Error opening file");
            return;
        }

        String numberS = checkValidNumber(in, wordCounterMap);

        PrintWriter output2 = null;
        try {
            System.out.print("Enter the file name to show the result : ");
            String s2 = in.readLine();
            output2 = new PrintWriter(new BufferedWriter(new FileWriter(s2)));

            outputIndex(organizedWordCounter(wordCounterMap,
                    Integer.parseInt(numberS)), s, output2);

            in.close();
            output2.close();
        } catch (IOException e) {
            System.err.println("Error creating file");
        }

    }

}
