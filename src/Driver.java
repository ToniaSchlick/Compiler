import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.*;

public class Driver {

    public static void main(String[] args) {

        CompilersLexer lexer;
        CompilersParser parser;
        Listener listener;
        TableTree tree = new TableTree();
        try {
            lexer = new CompilersLexer(CharStreams.fromFileName(args[0]));
            parser = new CompilersParser(new CommonTokenStream(lexer));
            listener = new Listener(tree);
            new ParseTreeWalker().walk(listener, parser.program());
            tree.printTables();
        } catch (IOException e){
            System.out.println("Could not read file");
        }

        /*Code for Part 2*/
        /*
        System.out.println(tree.getTokenStartIndex());
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
            parser.program();
            System.out.println("Accepted");
        } catch (Exception e) {
            System.out.println("Not Accepted");
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
}
