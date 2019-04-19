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
            for (String line : ac3) {
                System.out.println(";" + line);
            }
            System.out.println(";tiny code");
            convertToTiny(ac3);
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

    private static void convertToTiny(ArrayList<String> ac3) {
        
    }
}
