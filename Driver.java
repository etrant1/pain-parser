import lexer.Lexer;

import java.util.Scanner;

public class Driver {
    /* Driver code for application */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter terminals to be analyzed: ");
        String terminals = sc.nextLine();
        sc.close();

        Lexer lexer = new Lexer(terminals);
        lexer.lex();

        System.out.println("Tokens: " + lexer.getTokens());

        Parser parser = new Parser(lexer.getTokens());
        if(parser.parse()) System.out.println("Terminals passed");

    }
}
