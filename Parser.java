import lexer.Token;
import lexer.TokenType;

import java.util.List;

import static lexer.TokenType.*;

public class Parser {
    private final List<Token> tokens;
    private Token currentToken;
    private int pos = 0;

    public Parser(List<Token> tokens){
        this.tokens = tokens;
        this.currentToken = tokens.get(pos);
    }

    public boolean parse(){
        stmt();
        return true;
    }

    // <STMT> --> <IF_STMT> | <BLOCK> | <ASSIGN> | <DECLARE> |<WHILE_LOOP>
    private void stmt(){
        switch (currentToken.getType()){
            case IF:
                if_stmt();
            case WHILE:
                while_loop();
            case LEFT_BRACE:
                block();
            case DATATYPE:
                declare();
            case IDENTIFIER:
                assign();
            default:
                error();
        }
    }

    // <STMT_LIST> --> { <STMT> `;` }
    private void stmt_list(){
        stmt();
        if(isDifferentType(currentToken, SEMICOLON)) error();
        switch (currentToken.getType()){
            case IF, WHILE, LEFT_BRACE, DATATYPE, IDENTIFIER:
                stmt_list();
        }
    }

    // <IF_STMT> --> `if` `(` <BOOL_EXPR> `)` <BLOCK>  [ `else`  <BLOCK> ]
    private void if_stmt(){
        if(isDifferentType(currentToken, IF)) error();
        if(isDifferentType(currentToken, LEFT_PAREN)) error();

        bool_expr();

        if(isDifferentType(currentToken, RIGHT_PAREN)) error();

        block();

        if(isSameType(currentToken, ELSE)){
            block();
        }
    }

    // <WHILE_LOOP> --> `while` `(` <BOOL_EXPR> `)` <BLOCK>
    private void while_loop(){
        if(isDifferentType(currentToken, WHILE)) error();
        if(isDifferentType(currentToken, LEFT_PAREN)) error();

        bool_expr();

        if(isDifferentType(currentToken, RIGHT_PAREN)) error();

        block();
    }

    private void block(){
        if(isDifferentType(currentToken, LEFT_BRACE)) error();
        stmt_list();
        if(isDifferentType(currentToken, RIGHT_BRACE)) error();
    }

    // <DECLARE> --> `DataType` ID {`,` ID }
    private void declare(){
        if(isDifferentType(currentToken, DATATYPE)) error();
        if(isDifferentType(currentToken, IDENTIFIER)) error();
        if(isSameType(currentToken, COMMA)){
            if(isDifferentType(currentToken, IDENTIFIER)) error();
        }
    }

    // <ASSIGN> --> ID `=` <EXPR>
    private void assign(){
        if(isDifferentType(currentToken, IDENTIFIER)) error();
        if(isDifferentType(currentToken, EQUALS)) error();
        expr();
    }

    // <EXPR> --> <TERM> {(`+`|`-`) <TERM>}
    private void expr(){
        term();
        if(isSameType(currentToken, PLUS) || isSameType(currentToken, MINUS)) term();
    }

    // <TERM> --> <FACT> {(`*`|`/`|`%`) <FACT>}
    private void term(){
        fact();
        if(isSameType(currentToken, STAR) || isSameType(currentToken, DIVIDE) ||
                isSameType(currentToken, MODULO)) fact();
    }

    // <FACT> --> ID | INT_LIT | FLOAT_LIT | `(` <EXPR> `)`
    private void fact(){
        switch (currentToken.getType()){
            case IDENTIFIER, FLOAT_LIT, INT_LIT: break;
            case LEFT_PAREN:
                expr();
                if(isDifferentType(currentToken, RIGHT_PAREN)) error();
            default:
                error();
        }
    }

    // <BOOL_EXPR> --> <BTERM> {(`>`|`<`|`>=`|`<=`) <BTERM>}
    private void bool_expr(){
        b_term();
        if(isSameType(currentToken, GREATER) || isSameType(currentToken, LESS) ||
                isSameType(currentToken, GREATER_EQUALS) || isSameType(currentToken, LESS_EQUALS)) b_term();
    }

    // <BTERM> --> <BAND> {(`==`|`!=`) <BAND>}
    private void b_term(){
        b_and();
        if(isSameType(currentToken, AND) || isSameType(currentToken, NOT_EQUALS)) b_and();
    }

    // <BAND> --> <BOR> {`&&` <BOR>}
    private void b_and(){
        b_or();
        if(isSameType(currentToken, AND)) b_or();
    }

    // <BOR> --> <EXPR> {`&&` <EXPR>}
    private void b_or(){
        expr();
        if(isSameType(currentToken, OR)) expr();
    }

    /*
    ------------------------------------------------------------
                        Helper functions
    ------------------------------------------------------------
     */
    private boolean isDifferentType(Token token, TokenType desired){
        if(token.getType() != desired) return true;
        else pos++; return false;
    }
    private boolean isSameType(Token token, TokenType desired){
        if(token.getType() == desired) {
            pos++;
            return true;
        }
        return false;
    }
    private void error(){
        System.out.println("Syntax error");
        System.exit(1);
    }
}
