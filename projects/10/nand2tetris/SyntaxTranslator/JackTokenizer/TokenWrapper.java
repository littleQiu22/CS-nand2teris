package nand2tetris.SyntaxTranslator.JackTokenizer;

public class TokenWrapper {
    public TokenType tokenType;
    public TokenWrapper(TokenType tokenType){
        this.tokenType=tokenType;
    }
    public TokenWrapper(){}

    public TokenType getTokenType(){
        return tokenType;
    }

    public void  setTokenType(TokenType tokenType){
        this.tokenType=tokenType;
    }

}
