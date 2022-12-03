import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.statement.Statement1;
import components.utilities.Reporter;
import components.utilities.Tokenizer;

/**
 * Layered implementation of secondary methods {@code parse} and
 * {@code parseBlock} for {@code Statement}.
 *
 * @author Xidan Kou
 *
 */
public final class Statement1Parse1 extends Statement1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Converts {@code c} into the corresponding {@code Condition}.
     *
     * @param c
     *            the condition to convert
     * @return the {@code Condition} corresponding to {@code c}
     * @requires [c is a condition string]
     * @ensures parseCondition = [Condition corresponding to c]
     */
    private static Condition parseCondition(String c) {
        assert c != null : "Violation of: c is not null";
        assert Tokenizer
                .isCondition(c) : "Violation of: c is a condition string";
        return Condition.valueOf(c.replace('-', '_').toUpperCase());
    }

    /**
     * Parses an IF or IF_ELSE statement from {@code tokens} into {@code s}.
     *
     * @param tokens
     *            the input tokens
     * @param s
     *            the parsed statement
     * @replaces s
     * @updates tokens
     * @requires <pre>
     * [<"IF"> is a prefix of tokens]  and
     *  [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
     * </pre>
     * @ensures <pre>
     * if [an if string is a proper prefix of #tokens] then
     *  s = [IF or IF_ELSE Statement corresponding to if string at start of #tokens]  and
     *  #tokens = [if string at start of #tokens] * tokens
     * else
     *  [reports an appropriate error message to the console and terminates client]
     * </pre>
     */
    private static void parseIf(Queue<String> tokens, Statement s) {
        assert tokens != null : "Violation of: tokens is not null";
        assert s != null : "Violation of: s is not null";
        assert tokens.length() > 0 && tokens.front().equals("IF") : ""
                + "Violation of: <\"IF\"> is proper prefix of tokens";

        tokens.dequeue();
        // delete the String "IF"

        Reporter.assertElseFatalError(Tokenizer.isCondition(tokens.front()),
                "Violation of : Valid Condition in If(-Else).");
        Condition c = parseCondition(tokens.dequeue());
        Reporter.assertElseFatalError(tokens.front().equals("THEN"),
                "Violation of: The condition followed by the String \"THEN\" in IF ");
        tokens.dequeue();
        // delete "THEN"
        Statement body1 = s.newInstance();
        body1.parseBlock(tokens);

        s.assembleIf(c, body1);
        if (!tokens.front().equals("END")) {
            // if it is the if-else statement
            Reporter.assertElseFatalError(tokens.front().equals("ELSE"),
                    "Violation of: The String \"ELSE\" in IF-ELSE ");
            tokens.dequeue();
            Statement body2 = s.newInstance();
            body2.parseBlock(tokens);
            s.disassembleIf(body1);
            s.assembleIfElse(c, body1, body2);
        }
        Reporter.assertElseFatalError(tokens.front().equals("END"),
                "Violation of: The String \"END\" in ending IF(-ELSE) ");
        tokens.dequeue();
        Reporter.assertElseFatalError(tokens.front().equals("IF"),
                "Violation of: The String \"IF\" in ending IF(-ELSE) ");
        tokens.dequeue();

    }

    /**
     * Parses a WHILE statement from {@code tokens} into {@code s}.
     *
     * @param tokens
     *            the input tokens
     * @param s
     *            the parsed statement
     * @replaces s
     * @updates tokens
     * @requires <pre>
     * [<"WHILE"> is a prefix of tokens]  and
     *  [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
     * </pre>
     * @ensures <pre>
     * if [a while string is a proper prefix of #tokens] then
     *  s = [WHILE Statement corresponding to while string at start of #tokens]  and
     *  #tokens = [while string at start of #tokens] * tokens
     * else
     *  [reports an appropriate error message to the console and terminates client]
     * </pre>
     */
    private static void parseWhile(Queue<String> tokens, Statement s) {
        assert tokens != null : "Violation of: tokens is not null";
        assert s != null : "Violation of: s is not null";
        assert tokens.length() > 0 && tokens.front().equals("WHILE") : ""
                + "Violation of: <\"WHILE\"> is proper prefix of tokens";

        tokens.dequeue();

        Reporter.assertElseFatalError(Tokenizer.isCondition(tokens.front()),
                "Violation of : Valid Condition in While.");
        Condition c = parseCondition(tokens.dequeue());
        Reporter.assertElseFatalError(tokens.front().equals("DO"),
                "Violation of: The condition followed by the String \"DO\" in WHILE ");
        tokens.dequeue();
        Statement body = s.newInstance();
        body.parseBlock(tokens);
        s.assembleWhile(c, body);
        Reporter.assertElseFatalError(tokens.front().equals("END"),
                "Violation: Ending with the String \"END\"");
        tokens.dequeue();
        Reporter.assertElseFatalError(tokens.front().equals("WHILE"),
                "Violation: Ending While Statement with the String \"WHILE\"");
        tokens.dequeue();
    }

    /**
     * Parses a CALL statement from {@code tokens} into {@code s}.
     *
     * @param tokens
     *            the input tokens
     * @param s
     *            the parsed statement
     * @replaces s
     * @updates tokens
     * @requires [identifier string is a proper prefix of tokens]
     * @ensures <pre>
     * s =
     *   [CALL Statement corresponding to identifier string at start of #tokens]  and
     *  #tokens = [identifier string at start of #tokens] * tokens
     * </pre>
     */
    private static void parseCall(Queue<String> tokens, Statement s) {
        assert tokens != null : "Violation of: tokens is not null";
        assert s != null : "Violation of: s is not null";
        assert tokens.length() > 0
                && Tokenizer.isIdentifier(tokens.front()) : ""
                        + "Violation of: identifier string is proper prefix of tokens";

        String call = tokens.dequeue();
        s.assembleCall(call);
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Statement1Parse1() {
        super();
    }

    /*
     * Public methods ---------------------------------------------------------
     */
    /**
     * Parses a single BL statement from {@code tokens} into {@code this}.
     *
     * @param tokens
     *            the input tokens
     * @replaces this
     * @updates tokens
     * @requires [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
     * @ensures <pre>
     * if [a statement string is a proper prefix of #tokens] then
     *  this =
     *   [Statement corresponding to statement string at start of #tokens]  and
     *  #tokens = [statement string at start of #tokens] * tokens
     * else
     *  [reports an appropriate error message to the console and terminates client]
     * </pre>
     */
    @Override
    public void parse(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0 : ""
                + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

        Reporter.assertElseFatalError(
                tokens.front().equals("IF") || tokens.front().equals("WHILE")
                        || Tokenizer.isIdentifier(tokens.front()),
                "Violation of : Starting with\"IF\" or \" WHILE\" or valid Identifier");
        Statement s = this.newInstance();
        if (tokens.front().equals("IF")) {
            parseIf(tokens, s);

        } else if (tokens.front().equals("WHILE")) {
            parseWhile(tokens, s);
        } else {
            parseCall(tokens, s);
        }
        this.transferFrom(s);

    }

    /**
     * Parses a maximally long sequence of BL statements from {@code tokens}
     * into the BLOCK {@code this}.
     *
     * @param tokens
     *            the input tokens
     * @replaces this
     * @updates tokens
     * @requires [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
     * @ensures <pre>
     * if [there is a block string b that is a prefix of #tokens]  and
     *    [the first token past b in #tokens cannot begin a statement string] then
     *  this =
     *   [BLOCK Statement corresponding to a block string at start of #tokens
     *    that is immediately followed by a token in #tokens that cannot begin
     *    a statement string]  and
     *   #tokens =
     *    [a block string at start of #tokens that is immediately followed
     *     by a token in #tokens that cannot begin a statement string] * tokens
     * else
     *  [reports an appropriate error message to the console and terminates client]
     * </pre>
     */
    @Override
    public void parseBlock(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0 : ""
                + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

        Statement s = this.newInstance();

        while (!tokens.front().equals("END") && !tokens.front().equals("ELSE")
                && !tokens.front().equals(Tokenizer.END_OF_INPUT)) {
            s.parse(tokens);
            this.addToBlock(this.lengthOfBlock(), s);

        }

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
        /*
         * Get input file name
         */
        out.print("Enter valid BL statement(s) file name: ");
        String fileName = in.nextLine();
        /*
         * Parse input file
         */
        out.println("*** Parsing input file ***");
        Statement s = new Statement1Parse1();
        SimpleReader file = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        s.parseBlock(tokens); // replace with parseBlock to test other method
        /*
         * Pretty print the statement(s)
         */
        out.println("*** Pretty print of parsed statement(s) ***");
        s.prettyPrint(out, 0);

        in.close();
        out.close();
    }

}
