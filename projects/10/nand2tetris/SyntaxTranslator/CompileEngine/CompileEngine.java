package nand2tetris.SyntaxTranslator.CompileEngine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Struct;

import nand2tetris.SyntaxTranslator.JackTokenizer.JackTokenizer;
import nand2tetris.SyntaxTranslator.JackTokenizer.KeyWord;
import nand2tetris.SyntaxTranslator.JackTokenizer.Structure;
import nand2tetris.SyntaxTranslator.JackTokenizer.Symbol;
import nand2tetris.SyntaxTranslator.JackTokenizer.TokenType;

public class CompileEngine {
    private JackTokenizer jTokenizer;
    private File targetFile;
    private final static String TAG_ATTR="<tag> attr </tag>"; 
    private final static String START_TAG="<tag>";
    private final static String END_TAG="</tag>";
    private final static String TAG_STR="tag";
    private final static String ATTR_STR="attr";
    private PrintWriter pWriter;

    public CompileEngine(){}

    public CompileEngine(File f){
        targetFile=f;
    }

    public CompileEngine(JackTokenizer jTokenizer){
        this.jTokenizer=jTokenizer;
    }

    public CompileEngine(JackTokenizer jTokenizer, File f){
        this.jTokenizer=jTokenizer;
        targetFile=f;
    }

    public void setTokenizer(JackTokenizer jTokenizer){
        this.jTokenizer=jTokenizer;
    }

    public void setFile(File f){
        targetFile=f;
    }

    public void compile() throws FileNotFoundException{
        pWriter=new PrintWriter(targetFile);
        compileClass();
        pWriter.close();
    }

    // decompose the whole compilation into mulitple samll compilations

    // the whole structure of class
    public void compileClass(){
        pWriter.println(START_TAG.replace(TAG_STR, Structure.CLASS.getStructure()));
        TokenType tokenType=jTokenizer.tokenType();
        // 'class'(\s+)<className>(\s*)'{'(\s)*(<classVarDec(\s)*>*(<subroutineDec>(\s)*)*)*'}'   
        // 'class'
        jTokenizer.advance();
        if((TokenType.KEYWORD.equals(tokenType) && jTokenizer.keyword().equals(KeyWord.CLASS))){
            replaceKeyword();
        }else{
            System.out.println("'class' error");

        }
        // <className>
        jTokenizer.advance();
        if(!TokenType.IDENTIFIER.equals(tokenType)){
            System.out.println("<className> error");
        }else{
            replaceKeyword();
        }
        // '{'
        jTokenizer.advance();
        if(!TokenType.SYMBOL.equals(tokenType) && Symbol.LEFT_CURLY_BRACKET.getSymbol().equals(jTokenizer.symbol())){
            System.out.println("'{'' error");
        }else{
            pWriter.println(TAG_ATTR.replace(TAG_STR, TokenType.SYMBOL.getToken()).replace(ATTR_STR, Symbol.LEFT_CURLY_BRACKET.getSymbol()));
        }

        // (<classVarDec>\s*)*
        jTokenizer.advance();
        while(TokenType.KEYWORD.equals(tokenType) && (KeyWord.STATIC.equals(jTokenizer.keyword())|| KeyWord.FIELD.equals(jTokenizer.keyword()))){
            compileClassVarDec(true);
            jTokenizer.advance();
        }
        
        // (<subroutineDec>\s*)*
        while(TokenType.KEYWORD.equals(tokenType) && KeyWord.CONSTRUCTOR.equals(jTokenizer.keyword()) || KeyWord.FUNCTION.equals(jTokenizer.keyword()) || KeyWord.METHOD.equals(jTokenizer.keyword())){
            compileSubroutine(true);
            jTokenizer.advance();
        }
        // '}'
        if(!TokenType.SYMBOL.equals(tokenType) && Symbol.RIGHT_CURLY_BRACKET.getSymbol().equals(jTokenizer.symbol())){
            System.out.println("'}'' error");
        }else{
            pWriter.println(TAG_ATTR.replace(TAG_STR, TokenType.SYMBOL.getToken()).replace(ATTR_STR, Symbol.RIGHT_BRACKET.getSymbol()));
        }
        pWriter.println(END_TAG.replace(TAG_STR, Structure.CLASS.getStructure()));
    }

    // static variable declaration
    public void compileClassVarDec(boolean hasReadFirstToken){
        pWriter.println(START_TAG.replace(TAG_ATTR, Structure.CLASS_VAR_DEC.getStructure()));
        // ('static' | 'field')(\s+)<type>(\s+)<varName>(\s*','\s*<varName>)*\s*';' 
        TokenType tokenType=jTokenizer.tokenType();
        if(!hasReadFirstToken){
            jTokenizer.advance();
        }
        // 'static' | 'field'
        if(TokenType.KEYWORD.equals(tokenType) && (KeyWord.STATIC.equals(jTokenizer.keyword())) || (KeyWord.FIELD.equals(jTokenizer.keyword()))){
            pWriter.println(TAG_ATTR.replace(TAG_STR, TokenType.KEYWORD.getToken()).replace(ATTR_STR, jTokenizer.keyword().getKeyword()));
        }
        // <type>
        jTokenizer.advance();
        if(TokenType.KEYWORD.equals(tokenType) && (KeyWord.INT.equals(jTokenizer.keyword()) || KeyWord.CHAR.equals(jTokenizer.keyword()) || KeyWord.BOOLEAN.equals(jTokenizer.keyword()))){
            pWriter.println(TAG_ATTR.replace(TAG_STR,TokenType.KEYWORD.getToken()).replace(ATTR_STR, jTokenizer.keyword().getKeyword()));
        }else if(TokenType.IDENTIFIER.equals(tokenType)){
            pWriter.println(TAG_ATTR.replace(TAG_STR, TokenType.IDENTIFIER.getToken()).replace(ATTR_STR, jTokenizer.identifier()));
        }
        // <varName>
        jTokenizer.advance();
        if(TokenType.IDENTIFIER.equals(tokenType)){
            pWriter.println(TAG_ATTR.replace(TAG_STR, TokenType.IDENTIFIER.getToken()).replace(ATTR_STR, jTokenizer.identifier()));
        }
        // (','\s*<varName>)*
        while(jTokenizer.hasToken()){
            int hasNext=0;
            // ','
            jTokenizer.advance();
            if(TokenType.SYMBOL.equals(tokenType) && Symbol.COMMA.getSymbol().equals(jTokenizer.symbol())){
                pWriter.println(TAG_ATTR.replace(TAG_STR,TokenType.SYMBOL.getToken()).replace(ATTR_STR, jTokenizer.symbol()));
                hasNext+=1;
            }
            // <varName>
            jTokenizer.advance();
            tokenType=jTokenizer.tokenType();
            if(TokenType.IDENTIFIER.equals(tokenType)){
                pWriter.println(TAG_ATTR.replace(TAG_STR, TokenType.IDENTIFIER.getToken()).replace(ATTR_STR, jTokenizer.identifier()));
                hasNext+=1;
            }
            if(hasNext<2){
                break;
            }
        }
        // ';'
        jTokenizer.advance();
        if(TokenType.SYMBOL.equals(tokenType) && Symbol.SEMICOLON.getSymbol().equals(jTokenizer.symbol())){
            pWriter.println(TAG_ATTR.replace(TAG_STR, TokenType.SYMBOL.getToken()).replace(ATTR_STR, jTokenizer.symbol()));
        }

        pWriter.println(END_TAG.replace(TAG_ATTR, Structure.CLASS_VAR_DEC.getStructure()));

    }

    // method, function, constructor
    public void compileSubroutine(boolean hasReadFirstToken){
        pWriter.println(START_TAG.replace(TAG_STR, Structure.SUBROUTINE_DEC.getStructure()));
        // ('constructor'|'function'|'method')(\s+)('void'|<type>)\s+<subroutineName>\s*'('\s*<parameterList>\s*')'\s*subroutineBody
        TokenType tokenType=jTokenizer.tokenType();
        if(!hasReadFirstToken){
            jTokenizer.advance();
        }
        // ('constructor'|'function'|'method')
        if(TokenType.KEYWORD.equals(tokenType) && 
        (KeyWord.CONSTRUCTOR.equals(jTokenizer.keyword()) || KeyWord.FUNCTION.equals(jTokenizer.keyword()) || KeyWord.METHOD.equals(jTokenizer.keyword()))){
            pWriter.println(TAG_ATTR.replace(TAG_STR, TokenType.KEYWORD.getToken()).replace(ATTR_STR, jTokenizer.keyword().getKeyword()));
        }
        // ('void'|<type>)
        jTokenizer.advance();
        if(TokenType.KEYWORD.equals(tokenType)){
            if(KeyWord.VOID.equals(jTokenizer.keyword())){
                pWriter.println(TAG_ATTR.replace(TAG_STR, TokenType.KEYWORD.getToken()).replace(ATTR_STR, jTokenizer.keyword().getKeyword()));
            }
        }else if(TokenType.IDENTIFIER.equals(tokenType)){
            pWriter.println(TAG_ATTR.replace(TAG_STR, TokenType.IDENTIFIER.getToken()).replace(ATTR_STR, jTokenizer.identifier()));
        }else{

        }
        // <subroutineName>
        jTokenizer.advance();
        if(TokenType.IDENTIFIER.equals(tokenType)){
            pWriter.println();
        }
        pWriter.println(END_TAG.replace(TAG_STR, Structure.SUBROUTINE_DEC.getStructure()));
    }

    

    // possible parameter list
    public void  compileParameterList(){

    }

    // subroutine body
    public void compileSubroutineBody(){

    }

    // var declaration
    public void compileVarDec(){

    }

    // statements
    public void  compileStatements(){

    }

    // let statement
    public void  compileLet(){

    }

    // if statement
    public void compileIf(){

    }

    // while statement
    public void compileWhile(){

    }

    // do statement
    public void compileDo(){

    }

    // return statement
    public void compileReturn(){

    }

    // expression
    public void compileExpression(){

    }

    // term
    public void compileTerm(){

    }

    // expression list
    public int compileExpressionList(){

    }

    public void replaceIdentifier(){
        pWriter.println(TAG_ATTR.replace(TAG_STR, TokenType.IDENTIFIER.getToken()).replace(ATTR_STR, jTokenizer.identifier()));
    }

    public void replaceKeyword(){
        pWriter.println(TAG_ATTR.replace(TAG_STR, TokenType.KEYWORD.getToken()).replace(ATTR_STR, jTokenizer.keyword().getKeyword()));
    }

    public void replaceSymbol(){
        pWriter.println(TAG_ATTR.replace(TAG_STR, TokenType.KEYWORD.getToken()).replace(ATTR_STR, jTokenizer.keyword().getKeyword()));
    }



}
