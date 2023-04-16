import lexer.Lexer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Driver {
    /* Driver code for application */
    public static void main(String[] args) {
        if (args[0] == null || args[0].trim().isEmpty()) {
            System.out.println("You need to specify a path!");
            return;
        }

        StringBuilder data = new StringBuilder();
        try {
            System.out.println(args[0]);

            File file = new File(args[0]);
            Scanner reader = new Scanner(file);

            while (reader.hasNextLine()) {
                data.append(reader.nextLine());
            }

            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        Lexer lexer = new Lexer(data.toString());
        lexer.lex();

        System.out.println("[INFO] Tokens: " + lexer.getTokens());

        Parser parser = new Parser(lexer.getTokens());
        if (parser.parse()) System.out.println("[INFO] Syntax is valid");
    }
}
