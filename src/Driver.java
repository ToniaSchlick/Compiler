import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.Vocabulary;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Driver {

    public static void main(String[] args) {

        File file = new File("fibonacci.micro");

        String code = "";
        String line;
        CompilersLexer lexer;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null){
                code = code.concat(" " + line);
            }
            lexer = new  CompilersLexer(CharStreams.fromFileName("fibonacci.micro"));
        } catch (IOException e){
            System.out.println("Could not read file");
            return;
        }

        //CompilersLexer lexer = new  CompilersLexer(CharStreams.fromFileName("fibonacci.micro"));
        //CompilersParser parser = new CompilersParser(new CommonTokenStream(lexer));

        List<? extends Token> tokens = lexer.getAllTokens();

        Vocabulary voc = lexer.getVocabulary();
        //lexer.get
        for (Token token : tokens) {
            System.out.println("Token type: " + voc.getSymbolicName(token.getType()));
            System.out.println("Token type value: " + token.getType());
            System.out.println("Value: " + token.getText() + "\n");
        }
    }
}
