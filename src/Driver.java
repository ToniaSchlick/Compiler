import org.antlr.v4.runtime.*;

import java.io.*;
import java.util.List;

public class Driver {

    public static void main(String[] args) {

        CompilersLexer lexer;

        try {
            lexer = new CompilersLexer(CharStreams.fromFileName(args[0]));
        } catch (IOException e){
            System.out.println("Could not read file");
            return;
        }

        List<? extends Token> tokens = lexer.getAllTokens();

        Vocabulary voc = lexer.getVocabulary();
        for (Token token : tokens) {
            if (voc.getSymbolicName(token.getType()) != null) {
                System.out.println("Token Type: " + voc.getSymbolicName(token.getType()));
            } else {
                System.out.println("Token literal type: " + voc.getLiteralName(token.getType()));
            }
            System.out.println("Value: " + token.getText());
        }
    }
}
