package lexer;

public class Token {
    final TokenTypes type;

    Token(TokenTypes type){
        this.type = type;
    }

    public TokenTypes getType() { return type; }
    public String toString(){
        return type + "";
    }
}
