package lexer;

public enum TokenTypes {
    // Single-character tokens
    LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE, SEMICOLON,

    // Single-character operators
    PLUS, MINUS, MULTIPLY, DIVIDE, MODULO,
    EQUALS, LESS, GREATER, NOT,

    // Two-character operators
    EQUALS_EQUALS, GREATER_EQUALS, LESS_EQUALS, AND, OR, NOT_EQUALS,

    // Literals
    IDENTIFIER, FLOAT, INTEGER,

    // Keywords
    IF, WHILE, ELSE,

    // End-of-file
    EOF
}

