import components.map.Map;
import components.map.Map1L;
import components.program.Program;
import components.program.Program1;
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
 * Layered implementation of secondary method {@code parse} for {@code Program}.
 *
 * @author Xidan Kou
 *
 */
public final class Program1Parse1 extends Program1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Parses a single BL instruction from {@code tokens} returning the
     * instruction name as the value of the function and the body of the
     * instruction in {@code body}.
     *
     * @param tokens
     *            the input tokens
     * @param body
     *            the instruction body
     * @return the instruction name
     * @replaces body
     * @updates tokens
     * @requires <pre>
     * [<"INSTRUCTION"> is a prefix of tokens]  and
     *  [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
     * </pre>
     * @ensures <pre>
     * if [an instruction string is a proper prefix of #tokens]  and
     *    [the beginning name of this instruction equals its ending name]  and
     *    [the name of this instruction does not equal the name of a primitive
     *     instruction in the BL language] then
     *  parseInstruction = [name of instruction at start of #tokens]  and
     *  body = [Statement corresponding to statement string of body of
     *          instruction at start of #tokens]  and
     *  #tokens = [instruction string at start of #tokens] * tokens
     * else
     *  [report an appropriate error message to the console and terminate client]
     * </pre>
     */
    private static String parseInstruction(Queue<String> tokens,
            Statement body) {
        assert tokens != null : "Violation of: tokens is not null";
        assert body != null : "Violation of: body is not null";
        assert tokens.length() > 0 && tokens.front().equals("INSTRUCTION") : ""
                + "Violation of: <\"INSTRUCTION\"> is proper prefix of tokens";

        Reporter.assertElseFatalError(tokens.front().equals("INSTRUCTION"),
                "Violation of: an instruction string is a proper prefix of #tokens");
        // print out error message an instruction string is not a prefix of tokens
        tokens.dequeue();
        // delete the String "INSTRUCTION"
        Reporter.assertElseFatalError(Tokenizer.isIdentifier(tokens.front()),
                "Violation of:the name of this instruction is an valid Identifier");
        String instructionName = tokens.dequeue();
        Reporter.assertElseFatalError(tokens.front().equals("IS"),
                "Violation of: Identifier(name of Instruction) "
                        + "is followed by String \"IS\"");
        tokens.dequeue();
        // delete the String "IS"
        body.parseBlock(tokens);
        // parse the Instruction body
        Reporter.assertElseFatalError(tokens.front().equals("END"),
                "Violation of: Instruction body end with the String \"END\"");
        tokens.dequeue();
        // delete the String "END"
        Reporter.assertElseFatalError(tokens.front().equals(instructionName),
                "Violation of:the beginning name of this instruction equals ending name");
        tokens.dequeue();
        // delete the Instruction name

        return instructionName;
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Program1Parse1() {
        super();
    }

    /*
     * Public methods ---------------------------------------------------------
     */

    @Override
    public void parse(SimpleReader in) {
        assert in != null : "Violation of: in is not null";
        assert in.isOpen() : "Violation of: in.is_open";
        Queue<String> tokens = Tokenizer.tokens(in);
        this.parse(tokens);
    }

    /**
     * Parses a BL program from tokens into this.
     *
     * @param tokens
     *            the input tokens
     *
     *            Updates: tokens Replaces: this Requires:
     *            [<Tokenizer.END_OF_INPUT> is a suffix of tokens] Ensures: if
     *            #tokens = [a program string] * <Tokenizer.END_OF_INPUT> and
     *            [the penultimate token of #tokens equals the name of the
     *            program supplied near the beginning] and [the beginning name
     *            of each new instruction equals its ending name] and [none of
     *            the names of the new instructions equals the name of a
     *            primitive instruction in the BL language] then this = [Program
     *            corresponding to program string at start of #tokens] and
     *            tokens = <Tokenizer.END_OF_INPUT> else [reports an appropriate
     *            error message to the console and terminates client]
     */
    @Override
    public void parse(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0 : ""
                + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

        Program program = new Program1();
        Reporter.assertElseFatalError(tokens.front().equals("PROGRAM"),
                "Violation of: <\"PROGRAM\"> is a proper prefix of tokens");
        tokens.dequeue();
        // delete the String "PROGRAM"

        Reporter.assertElseFatalError(Tokenizer.isIdentifier(tokens.front()),
                "Violation of: The program name is an Identifier");
        String programName = tokens.dequeue();
        program.setName(programName);

        // set program name
        Reporter.assertElseFatalError(tokens.front().equals("IS"),
                "Violation of: The program name is followed by the String \"IS\"");
        tokens.dequeue();
        // delete the String "IS"

        /*
         * Start to Parse user defined Instruction(s)
         * ---------------------------------------------------------------------
         * --
         */
        Map<String, Statement> context = new Map1L<String, Statement>();

        while (!tokens.front().equals("BEGIN")) {

            Reporter.assertElseFatalError(tokens.front().equals("INSTRUCTION"),
                    "Violation of: The program context start with the String \"INSTRUCTION\"");

            Statement instructionBody = new Statement1();
            String contextName = parseInstruction(tokens, instructionBody);
            Reporter.assertElseFatalError(!context.hasKey(contextName),
                    "Violation of: The name of Instruction is unique");
            context.add(contextName, instructionBody);

        }
        program.swapContext(context);
        /*
         * End of Parsing user defined Instruction(s)
         * ---------------------------------------------------------------------
         * --
         */

        /*
         * Start to Parse Program body
         * ---------------------------------------------------------------------
         * --
         */

        Reporter.assertElseFatalError(tokens.front().equals("BEGIN"),
                "Violation of: program body start with the String \"BEGIN\"");
        tokens.dequeue();
        // delete the String "BEGIN"
        Statement programBody = new Statement1();
        programBody.parseBlock(tokens);
        // parse the program body into statement
        Reporter.assertElseFatalError(tokens.front().equals("END"),
                "Violation of: program body end with the String \"END\"");
        tokens.dequeue();
        // delete the String "END"
        Reporter.assertElseFatalError(tokens.front().equals(programName),
                "Violation of: the beginning name of Program equals its ending name");
        tokens.dequeue();
        // delete the Program name
        Reporter.assertElseFatalError(
                tokens.front().equals(Tokenizer.END_OF_INPUT),
                "Violation of: [<Tokenizer.END_OF_INPUT> is a suffix of tokens]");
        program.swapBody(programBody);
        this.transferFrom(program);

        /*
         * End of Parsing Program Body
         * ---------------------------------------------------------------------
         * --
         */

    }

    /*
     * Main test method -------------------------------------------------------
     */

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
        out.print("Enter valid BL program file name: ");
        String fileName = in.nextLine();
        /*
         * Parse input file
         */
        out.println("*** Parsing input file ***");
        Program p = new Program1Parse1();
        SimpleReader file = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        p.parse(tokens);
        /*
         * Pretty print the program
         */
        out.println("*** Pretty print of parsed program ***");
        p.prettyPrint(out);

        in.close();
        out.close();
    }

}
