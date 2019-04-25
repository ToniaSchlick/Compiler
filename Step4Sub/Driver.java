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
            ArrayList<String> tiny = convertToTiny(ac3, tree.getGlobal());
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

    private static ArrayList<String> convertToTiny(ArrayList<String> ac3, SymbolTable table) {
        ArrayList<String> tiny = new ArrayList<>();
        //initialize all the variables in the symbol table
        for (Entry entry : table.entries) {
            if (entry.type.equals("STRING")) {
                tiny.add("str " + entry.name + " " + entry.value);
            } else {
                tiny.add("var " + entry.name);
            }
        }
        for (String fullLine : ac3) {
            //split the commands into their different parts
            String[] line = fullLine.split(" ");

            //converts JUMP to jmp command
            if (line[0].contains("JUMP")) {
                tiny.add("jmp " + line[1]);
            //converts LABEL to label command
            } else if (line[0].contains("LABEL")) {
                tiny.add("label " + line[1]);
            //due to givens in the assignment, we know the end of a function is the end of the program
            } else if (line[0].equals("RET")) {
                tiny.add("sys halt");
            } else if (!line[0].equals("LINK")){
                String datatype = line[0].substring(line[0].length()-1).toLowerCase();
                if (datatype.equals("f")) {
                    datatype = "r";
                }

                //convert a STORE command to a move command
                if (line[0].contains("STORE")) {
                    tiny.add("move " + TtoR(line[1]) + " " + TtoR(line[2]));
                //for all math functions, in order to keep our registers consistent with our temporaries,
                //we move the left operand in the function into the register we want the result to be stored
                //in before the actual function
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
                //read and write commands
                } else if (line[0].contains("WRITE")) {
                    tiny.add("sys write" + datatype + " " + line[1]);
                } else if (line[0].contains("READ")) {
                    tiny.add("sys read" + datatype + " " + line[1]);
                //give the comp and jump commands
                } else if (line[0].contains("NE")) {
                    tiny.add("comp" + datatype + " " + TtoR(line[1]) + " " + TtoR(line[2]));
                    tiny.add("jne " + line[3]);
                } else if (line[0].contains("EQ")) {
                    tiny.add("comp" + datatype + " " + TtoR(line[1]) + " " + TtoR(line[2]));
                    tiny.add("jeq " + line[3]);
                } else if (line[0].contains("GT")) {
                    tiny.add("comp" + datatype + " " + TtoR(line[1]) + " " + TtoR(line[2]));
                    tiny.add("jgt " + line[3]);
                } else if (line[0].contains("GE")) {
                    tiny.add("comp" + datatype + " " + TtoR(line[1]) + " " + TtoR(line[2]));
                    tiny.add("jge " + line[3]);
                } else if (line[0].contains("LT")) {
                    tiny.add("comp" + datatype + " " + TtoR(line[1]) + " " + TtoR(line[2]));
                    tiny.add("jlt " + line[3]);
                } else if (line[0].contains("LE")) {
                    tiny.add("comp" + datatype + " " + TtoR(line[1]) + " " + TtoR(line[2]));
                    tiny.add("jle " + line[3]);
                }
            }
        }
        return tiny;
    }

    //simple function to easily convert temporaries to registers
    private static String TtoR(String t) {
        return t.replace("$T", "r");
    }
}
