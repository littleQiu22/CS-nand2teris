package nand2tetris.SyntaxTranslator.JackTokenizer;

public enum Symbol {
    LEFT_BRACKET("("),
    RIGHT_BRACKET(")"),
    LEFT_CURLY_BRACKET("{"),
    RIGHT_CURLY_BRACKET("}"),
    LEFT_SQUARE_BRACKET("["),
    RIGHT_SQUARE_BREACKET("]"),
    DOT("."),
    COMMA(","),
    SEMICOLON(";"),
    PLUS("+"),
    MINUS("-"),
    MULTI("*"),
    DIVID("/"),
    BIT_AND("&"),
    BIT_OR("|"),
    LESS_THAN("<"),
    GREATER_THAN(">"),
    EQUAL("="),
    BIT_NOT("~");

    public String symbol;
    private Symbol(String symbol){
        this.symbol=symbol;
    }
    public String getSymbol(){
        return symbol;
    }
}
