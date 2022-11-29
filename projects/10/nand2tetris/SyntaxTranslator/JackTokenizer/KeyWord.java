package nand2tetris.SyntaxTranslator.JackTokenizer;

public enum KeyWord {
    
    CLASS("class"),
    METHOD("method"),
    FUNCTION("function"),
    CONSTRUCTOR("constructor"),
    INT("int"),
    BOOLEAN("boolean"),
    CHAR("char"),
    VOID("void"),
    VAR("var"),
    STATIC("static"),
    FIELD("field"),
    LET("let"),
    DO("do"),
    IF("if"),
    ELSE("else"),
    WHILE("while"),
    RETURN("return"),
    TRUE("true"),
    FALSE("false"),
    NULL("null"),
    THIS("this")
    ;
    private KeyWord(String keyWord){
        this.keyWord=keyWord;
    }

    public String getKeyword(){
        return keyWord;
    }

    public String keyWord;
    
}
