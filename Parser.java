import lexer.Token;
import lexer.TokenTypes;

import java.util.List;

import static lexer.TokenTypes.*;

public class Parser {
    List<Token> tokens;
    int current = -1;
    TokenTypes debugToken;
    Parser(List<Token> tokens){
        this.tokens = tokens;
    }

    public boolean parse(){
        stmt_list();
        if (current < tokens.size()-2) error();
        return true;
    }

    void stmt(){
        switch (lookAhead()) {
            case WHILE -> while_stmt();
            case IF -> if_stmt();
            case LEFT_BRACE -> block();
            case IDENTIFIER, FLOAT, INTEGER -> expr();
            default -> error();
        }
    }
    void stmt_list(){
       stmt();
       if (lookAhead() != SEMICOLON) error();
       current++;
    }
    void if_stmt(){
        current++;
        if (lookAhead() != LEFT_PAREN) error();
        current++;
        bool_expr();
        if (lookAhead() != RIGHT_PAREN) error();
        current++;
        if (lookAhead() == LEFT_BRACE) {
            block();
        }
        else {
            stmt();
            if (lookAhead() != SEMICOLON) error();
        }
        if (lookAhead() == ELSE) {
            current++;
            if (lookAhead() == LEFT_BRACE) {
                block();
            }
            else {
                stmt();
                if (lookAhead() != SEMICOLON) error();
                current++;
            }
        }
    }
    void while_stmt(){
        current++;
        if (lookAhead() != LEFT_PAREN) error();
        current++;
        bool_expr();
        if (lookAhead() != RIGHT_PAREN) error();
        current++;
        if (lookAhead() == LEFT_BRACE) {
            block();
        }
        else {
            stmt();
            if (lookAhead() != SEMICOLON) error();
            current++;
        }
    }
    void block(){
        current++;
        stmt_list();
        if (lookAhead() != RIGHT_BRACE) error();
        current++;
    }
    void expr(){
        term();
        TokenTypes next = lookAhead();
        if (next == PLUS || next == MINUS) {
            current++;
            term();
        }
    }
    void term(){
        fact();
        switch (lookAhead()) {
            case MULTIPLY, DIVIDE, MODULO -> {
                current++;
                fact();
            }
        }
    }
    void fact(){
        switch (lookAhead()){
            case IDENTIFIER:
            case FLOAT:
            case INTEGER:
                current++;
                break;
            case LEFT_PAREN:
                current++;
                expr();
                if (lookAhead() != RIGHT_PAREN) error();
                current++;
            default:
                error();
        }
    }
    void bool_expr(){
        b_term();
        switch (lookAhead()) {
            case GREATER, LESS, GREATER_EQUALS, LESS_EQUALS -> {
                current++;
                b_term();
            }
        }
    }
    void b_term(){
        b_and();
        TokenTypes next = lookAhead();
        if (next == EQUALS_EQUALS || next == NOT_EQUALS){
            current++;
            b_and();
        }
    }
    void b_and(){
        b_or();
        if (lookAhead() == AND) {
            current++;
            b_or();
        }
    }
    void b_or(){
        expr();
        if (lookAhead() == AND) {
            current++;
            expr();
        }
    }

    void error(){
        System.out.println("Syntax error");
        System.exit(64);
    }

    TokenTypes lookAhead() {
        debugToken = tokens.get(current+1).getType();
        return tokens.get(current+1).getType();
    }
}
