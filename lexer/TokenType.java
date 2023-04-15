package lexer;

public enum TokenType {
    // Single-character tokens
    LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE, SEMICOLON,

    // Single-character operators
    PLUS, MINUS, STAR, DIVIDE, MODULO,
    EQUALS, LESS, GREATER, NOT, COMMA,

    // Two-character operators
    EQUALS_EQUALS, GREATER_EQUALS, LESS_EQUALS, AND, OR, NOT_EQUALS,

    // Literals
    IDENTIFIER, FLOAT_LIT, INT_LIT,

    // Keywords
    IF, WHILE, ELSE, DATATYPE,

    // End-of-file
    EOF
}

