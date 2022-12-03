import java.util.Comparator;

import components.map.Map;
import components.map.Map.Pair;
import components.map.Map3;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.sortingmachine.SortingMachine;
import components.sortingmachine.SortingMachine1L;
import components.utilities.FormatChecker;

/**
 * Read .txt file and count the word. After that the result shows some number of
 * words which high frequency with alphabetical order through the .html file.
 * Its words' size is proportional to the frequency.
 *
 * @author Chaeun Hong，Xidan Kou, Jackie Z. Cheng
 *
 */
public final class TagCloudGenerator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private TagCloudGenerator() {
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
    private static Comparator<Map.Pair<String, Integer>> //
    alphabeticalOrder = new Comparator<Map.Pair<String, Integer>>() {
        @Override
        public int compare(Pair<String, Integer> o1, Pair<String, Integer> o2) {
            return o1.key().compareTo(o2.key());
        }
    };

    /**
     * Set up an numerical order comparator.
     */
    private static Comparator<Map.Pair<String, Integer>> //
    numericalOrder = new Comparator<Map.Pair<String, Integer>>() {
        @Override
        public int compare(Pair<String, Integer> o1, Pair<String, Integer> o2) {
            return o2.value().compareTo(o1.value());
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
     * @ensures [glossaryeMap contains term -> definition mapping from file
     *          fileName]
     */
    private static void getWordCounterMap(String fileName,
            Map<String, Integer> wordCounterMap) {
        assert fileName != null : "Violation of: fileName is not null";
        assert wordCounterMap != null : "Violation of: glossaryMap is not null";

        SimpleReader contents = new SimpleReader1L(fileName);

        Set<Character> separatorSet = new Set1L<Character>();
        generateElements(SEPARATORS, separatorSet);

        while (!contents.atEOS()) {

            String oneLine = contents.nextLine();
            int position = 0;

            while (position < oneLine.length()) {
                String tempToken = nextWordOrSeparator(oneLine, position,
                        separatorSet);
                String token = tempToken.toLowerCase();
                if (!separatorSet.contains(token.charAt(0))) {
                    if (!wordCounterMap.hasKey(token)) {
                        wordCounterMap.add(token, 1);

                    } else {
                        int count = wordCounterMap.value(token);
                        wordCounterMap.replaceValue(token, count + 1);
                        if (wordCounterMap.value(token) > max) {
                            max = wordCounterMap.value(token);
                        } else if (wordCounterMap.value(token) < min) {
                            min = wordCounterMap.value(token);
                        }
                    }
                }
                position += token.length();
            }
        }

        contents.close();
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
     * @ensures wordCounterMap organized alphabetical
     */
    private static SortingMachine<Map.Pair<String, Integer>> organizedWordCounter(
            Map<String, Integer> wordCounterMap, int number) {
        assert number > 0 : "Violation of: number is greater than 0";

        SortingMachine<Map.Pair<String, Integer>> topRanked = new SortingMachine1L<>(
                numericalOrder);
        for (Map.Pair<String, Integer> pair : wordCounterMap) {
            topRanked.add(pair);
        }
        topRanked.changeToExtractionMode();

        SortingMachine<Map.Pair<String, Integer>> sorted = topRanked
                .newInstance();
        for (int i = 0; i < number; i++) {
            sorted.add(topRanked.removeFirst());
        }
        sorted.changeToExtractionMode();

        SortingMachine<Map.Pair<String, Integer>> sorted2 = new SortingMachine1L<>(
                alphabeticalOrder);
        for (Map.Pair<String, Integer> pair : sorted) {
            sorted2.add(pair);
        }
        sorted2.changeToExtractionMode();

        return sorted2;

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
            fontSize = MIN_FONT_SIZE + (numberOfWords - min)
                    * (MAX_FONT_SIZE - MIN_FONT_SIZE) / (max - min);
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
    private static void outputIndex(
            SortingMachine<Map.Pair<String, Integer>> sorted, String fileName,
            SimpleWriter out) {
        assert out != null : "Violation of: out is not null";
        assert out.isOpen() : "Violation of: out.is_open";

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
            Map.Pair<String, Integer> pair = sorted.removeFirst();

            out.println("<span style=\"cursor:default\" class=\"f"
                    + getFontSize(pair.value()) + "\" title=\"count: "
                    + pair.value() + "\">" + pair.key() + "</span>");

        }
        out.println("</p>");
        out.println("</div>");
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
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        out.print("Enter the file name to organizing : ");
        String fileName = in.nextLine();

        Map<String, Integer> wordCounterMap = new Map3<>();

        getWordCounterMap(fileName, wordCounterMap);

        out.print("Enter the file name to show the result : ");
        String result = in.nextLine();

        out.print("Enter the number of words you want to be shown: ");
        String numberS = in.nextLine();
        while (!FormatChecker.canParseInt(numberS)
                || !(Integer.parseInt(numberS) >= 0)
                || Integer.parseInt(numberS) > wordCounterMap.size()) {
            if (FormatChecker.canParseInt(numberS)
                    && Integer.parseInt(numberS) >= 0
                    && Integer.parseInt(numberS) > wordCounterMap.size()) {
                out.print("Error - Number too large, maximum is "
                        + wordCounterMap.size() + ", enter a smaller number: ");
                numberS = in.nextLine();

            } else {
                out.print("Error - please enter a positive number: ");
                numberS = in.nextLine();
            }

        }

        SimpleWriter out2 = new SimpleWriter1L(result);
        outputIndex(
                organizedWordCounter(wordCounterMap, Integer.parseInt(numberS)),
                fileName, out2);
        out2.close();

        in.close();
        out.close();
    }

}
