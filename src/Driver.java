import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.*;
import java.util.ArrayList;

public class Driver {

    public static void main(String[] args) {

        CompilersLexer lexer;
        CompilersParser parser;
        Listener listener;
        TableTree tree = new TableTree();
        AST ast = new AST();
        try {
            lexer = new CompilersLexer(CharStreams.fromFileName(args[0]));
            parser = new CompilersParser(new CommonTokenStream(lexer));
            listener = new Listener(tree, ast);
            new ParseTreeWalker().walk(listener, parser.program());
            //ast.root.printSubTree();

            //every element in this arraylist will be a single line of 3AC
            ArrayList<String> ac3 = ast.buildCode(tree.getGlobal());
            System.out.println(";IR CODE");

            //print out the results of our 3AC generated code
            for (String line : ac3) {
                System.out.println(";" + line);
            }
            System.out.println(";tiny code");
            //convert 3AC into tiny code
            ArrayList<String> tiny = convertToTiny(ac3);
            //print the results of the tiny code conversion
            for (String line : tiny) {
                System.out.println(line);
            }
        } catch (IOException e){
            System.out.println("Could not read file");

        //catches ParseCancellationException that can be thrown if a declaration error occurs
        //logic to handle error is done elsewhere, just here to prevent program from spitting the error out
        } catch (ParseCancellationException e) {
            System.out.println("Error parsing program");
        }

        /*Code for Part 2*/
        /*
        parser.setErrorHandler(new ANTLRErrorStrategy() {
            @Override
            public void reset(Parser parser) {}

            @Override
            public Token recoverInline(Parser parser) throws RecognitionException {return null;}

            @Override
            public void recover(Parser parser, RecognitionException e) throws RecognitionException {}

            @Override
            public void sync(Parser parser) throws RecognitionException {}

            @Override
            public boolean inErrorRecoveryMode(Parser parser) {return false;}

            @Override
            public void reportMatch(Parser parser) {}

            @Override
            public void reportError(Parser parser, RecognitionException e) {
                throw new RuntimeException("Not good");
            }
        });

        try {
            //parser.program();
            listener = new Listener(tree);
            new ParseTreeWalker().walk(listener, parser.program());
            tree.printTables();
        } catch (Exception e) {
            System.out.printf("Not Accepted: %s", e);
        }
        */

        /* Code for Part 1 */
        /*
        List<? extends Token> tokens = lexer.getAllTokens();
        Vocabulary voc = lexer.getVocabulary();
        for (Token token : tokens) {
            System.out.println("Token Type: " + voc.getSymbolicName(token.getType()));
            System.out.println("Value: " + token.getText());
        }
        */
    }

    private static ArrayList<String> convertToTiny(ArrayList<String> ac3) {
        ArrayList<String> tiny = new ArrayList<>();
        for (String fullLine : ac3) {
            String [] line = fullLine.split(" ");
            if (line[0].contains("JUMP")) {

            } else if (line[0].contains("LABEL")) {

            } else if (line[0].equals("RET")) {
                tiny.add("sys halt");
            } else if (!line[0].equals("LINK")){
                String datatype = line[0].substring(line[0].length()-1).toLowerCase();
                //System.out.println(datatype);
                if (datatype.equals("f")) {
                    datatype = "r";
                }
                if (line[0].contains("STORE")) {
                    tiny.add("move " + TtoR(line[1]) + " " + TtoR(line[2]));
                } else if (line[0].contains("MULT")) {
                    tiny.add("move " + TtoR(line[1]) + " " + TtoR(line[3]));
                    tiny.add("mul" + datatype + " " + TtoR(line[2]) + " " + TtoR(line[3]));
                } else if (line[0].contains("DIV")) {
                    tiny.add("move " + TtoR(line[1]) + " " + TtoR(line[3]));
                    tiny.add("div" + datatype + " " + TtoR(line[2]) + " " + TtoR(line[3]));
                } else if (line[0].contains("ADD")) {
                    tiny.add("move " + TtoR(line[1]) + " " + TtoR(line[3]));
                    tiny.add("add" + datatype + " " + TtoR(line[2]) + " " + TtoR(line[3]));
                } else if (line[0].contains("SUB")) {
                    tiny.add("move " + TtoR(line[1]) + " " + TtoR(line[3]));
                    tiny.add("sub" + datatype + " " + TtoR(line[2]) + " " + TtoR(line[3]));
                } else if (line[0].contains("WRITE")) {
                    tiny.add("sys write" + datatype + " " + line[1]);
                } else if (line[0].contains("READ")) {
                    tiny.add("sys read" + datatype + " " + line[1]);
                } else if (line[0].contains("NE")) {

                } else if (line[0].contains("EQ")) {

                } else if (line[0].contains("GT")) {

                } else if (line[0].contains("GE")) {

                } else if (line[0].contains("LT")) {

                } else if (line[0].contains("TE")) {

                }
            }
        }
        return tiny;
    }

    private static String TtoR(String t) {
        return t.replace("$T", "r");
    }
}
