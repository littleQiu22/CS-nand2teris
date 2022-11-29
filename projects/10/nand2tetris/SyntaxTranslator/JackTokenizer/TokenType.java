package nand2tetris.SyntaxTranslator.JackTokenizer;

public enum TokenType {
    KEYWORD("keyword"),
    SYMBOL("symbol"),
    IDENTIFIER("identifier"),
    INT_CONST("integerConstant"),
    STRING_CONST("stringConstant");
    public String tokenType;
    private TokenType(String tokenType){
        this.tokenType=tokenType;
    }
    public String getToken(){
        return tokenType;
    }
}
