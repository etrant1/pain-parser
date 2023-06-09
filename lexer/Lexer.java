package lexer;

import java.util.ArrayList;
import java.util.List;
import static lexer.TokenType.*;

public class Lexer {
    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    private int start = 0;
    private int current = 0;
    private int line = 1;

    public Lexer(String source){
        this.source = source;
    }

    // Begin lexical analysis
    public void lex() {
        while (!isAtEnd()) {
            start = current;
            scanToken();
        }

        tokens.add(new Token(EOF));
    }

    private void addToken(TokenType type) {
        tokens.add(new Token(type));
    }

    public List<Token> getTokens() {
        return tokens;
    }

    private void scanToken() {
        char c = advance();
        switch (c) {
            case '(': addToken(LEFT_PAREN); break;
            case ')': addToken(RIGHT_PAREN); break;
            case '{': addToken(LEFT_BRACE); break;
            case '}': addToken(RIGHT_BRACE); break;
            case ';': addToken(SEMICOLON); break;
            case '-': addToken(MINUS); break;
            case '+': addToken(PLUS); break;
            case '*': addToken(STAR); break;
            case '/': addToken(DIVIDE); break;
            case ',': addToken(COMMA); break;
            case '!':
                addToken(match('=') ? NOT_EQUALS : NOT);
                break;
            case '=':
                addToken(match('=') ? EQUALS_EQUALS : EQUALS);
                break;
            case '<':
                addToken(match('=') ? LESS_EQUALS : LESS);
                break;
            case '>':
                addToken(match('=') ? GREATER_EQUALS : GREATER);
                break;
            case '|':
                if (match('|')) {
                    addToken(OR);
                }
                break;
            case '&':
                if (match('&')) {
                    addToken(AND);
                 }
                break;
            case '\n':
                line++;
                break;
            case ' ':
            case '\t':
                break;
            default: {
                if (isDigit(c)) {
                    number();
                } else if (isAlpha(c)) {
                    keyword();
                } else {
                    System.out.println("Unexpected character on line: " + line);
                }
            }
        }
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private char advance() {
        return source.charAt(current++);
    }

    // Conditional advance
    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(current) != expected) return false;

        current++;
        return true;
    }

    // Lookahead without consuming a character
    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }

    // Lookahead twice as far without consuming a character
    private char peekNext() {
        if (current + 1 >= source.length()) return '\0';
        return source.charAt(current + 1);
    }

    private boolean isDigit(char c) {
        return Character.isDigit(c);
    }

    private boolean isAlpha(char c) {
        return ( Character.isAlphabetic(c) || c == '_');
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }

    private void number() {
        while (isDigit(peek())) advance();

        // Check for floats
        boolean float_lit = false;
        if (peek() == '.' && isDigit(peekNext())) {
            // Consume the "."
            advance();
            float_lit = true;
            while (isDigit(peek())) advance();
        }
        if (float_lit) addToken(FLOAT_LIT);
        else addToken(INT_LIT);
    }

    private void keyword(){
        while (isAlphaNumeric(peek())) advance();
        String text = source.substring(start, current);
        switch (text) {
            case "while": addToken(WHILE); break;
            case "if": addToken(IF); break;
            case "else": addToken(ELSE); break;
            case "int":
            case "float":
            case "var":
                addToken(DATATYPE); break;
            default: addToken(IDENTIFIER);
        }
    }
}
