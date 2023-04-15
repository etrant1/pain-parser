package lexer;

public class Token {
    final TokenType type;

    public Token(TokenType type){
        this.type = type;
    }

    public TokenType getType() { return type; }
    public String toString(){
        return type + "";
    }
}
