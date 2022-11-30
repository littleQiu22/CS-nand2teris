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
import nand2tetris.SyntaxTranslator.JackTokenizer.TokenWrapper;

public class CompileEngine {
    private JackTokenizer jTokenizer;
    private File targetFile;
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
        replaceStartStructure(Structure.CLASS);
        TokenWrapper tokenType=jTokenizer.tokenTypeWrapper();
        // 'class'(\s+)<className>(\s*)'{'(\s)*(<classVarDec(\s)*>*(<subroutineDec>(\s)*)*)*'}'   
        // 'class'
        jTokenizer.advance();
        if((TokenType.KEYWORD.equals(tokenType.getTokenType()) && jTokenizer.keyword().equals(KeyWord.CLASS))){
            replaceKeyword();
        }else{
            System.out.println("'class' error");

        }
        // <className>
        jTokenizer.advance();
        if(!TokenType.IDENTIFIER.equals(tokenType.getTokenType())){
            System.out.println("<className> error");
        }else{
            replaceKeyword();
        }
        // '{'
        jTokenizer.advance();
        if(!TokenType.SYMBOL.equals(tokenType.getTokenType()) && Symbol.LEFT_CURLY_BRACKET.getSymbol().equals(jTokenizer.symbol())){
            System.out.println("'{'' error");
        }else{
            replaceSymbol();
        }

        // (<classVarDec>\s*)*
        jTokenizer.advance();
        while(TokenType.KEYWORD.equals(tokenType.getTokenType()) && (KeyWord.STATIC.equals(jTokenizer.keyword())|| KeyWord.FIELD.equals(jTokenizer.keyword()))){
            compileClassVarDec(true);
            jTokenizer.advance();
        }
        
        // (<subroutineDec>\s*)*
        while(TokenType.KEYWORD.equals(tokenType.getTokenType()) && KeyWord.CONSTRUCTOR.equals(jTokenizer.keyword()) || KeyWord.FUNCTION.equals(jTokenizer.keyword()) || KeyWord.METHOD.equals(jTokenizer.keyword())){
            compileSubroutine(true);
            jTokenizer.advance();
        }
        // '}'
        if(!TokenType.SYMBOL.equals(tokenType.getTokenType()) && Symbol.RIGHT_CURLY_BRACKET.getSymbol().equals(jTokenizer.symbol())){
            System.out.println("'}'' error");
        }else{
            replaceSymbol();
        }
        replaceEndStructure(Structure.CLASS);
    }

    // static variable declaration
    public void compileClassVarDec(boolean hasReadFirstToken){
        replaceStartStructure(Structure.CLASS_VAR_DEC);
        // ('static' | 'field')(\s+)<type>(\s+)<varName>(\s*','\s*<varName>)*\s*';' 
        TokenWrapper tokenType=jTokenizer.tokenTypeWrapper();
        if(!hasReadFirstToken){
            jTokenizer.advance();
        }
        // 'static' | 'field'
        if(TokenType.KEYWORD.equals(tokenType.getTokenType()) && (KeyWord.STATIC.equals(jTokenizer.keyword())) || (KeyWord.FIELD.equals(jTokenizer.keyword()))){
            replaceKeyword();
        }
        // <type>
        jTokenizer.advance();
        if(TokenType.KEYWORD.equals(tokenType.getTokenType()) && (KeyWord.INT.equals(jTokenizer.keyword()) || KeyWord.CHAR.equals(jTokenizer.keyword()) || KeyWord.BOOLEAN.equals(jTokenizer.keyword()))){
            replaceKeyword();
        }else if(TokenType.IDENTIFIER.equals(tokenType.getTokenType())){
            replaceIdentifier();
        }
        // <varName>
        jTokenizer.advance();
        if(TokenType.IDENTIFIER.equals(tokenType.getTokenType())){
            replaceKeyword();
        }
        // (','\s*<varName>)*
        while(true){
            int hasNext=0;
            // ','
            jTokenizer.advance();
            if(TokenType.SYMBOL.equals(tokenType.getTokenType()) && Symbol.COMMA.getSymbol().equals(jTokenizer.symbol())){
                replaceSymbol();
                hasNext+=1;
            }
            // <varName>
            jTokenizer.advance();
            if(TokenType.IDENTIFIER.equals(tokenType.getTokenType())){
                replaceIdentifier();
                hasNext+=1;
            }
            if(hasNext<2){
                break;
            }
        }
        // ';'
        jTokenizer.advance();
        if(TokenType.SYMBOL.equals(tokenType.getTokenType()) && Symbol.SEMICOLON.getSymbol().equals(jTokenizer.symbol())){
            replaceSymbol();
        }
        replaceEndStructure(Structure.CLASS_VAR_DEC);

    }

    // method, function, constructor
    public void compileSubroutine(boolean hasReadFirstToken){
        replaceStartStructure(Structure.SUBROUTINE_DEC);
        // ('constructor'|'function'|'method')(\s+)('void'|<type>)\s+<subroutineName>\s*'('\s*<parameterList>\s*')'\s*subroutineBody
        TokenWrapper tokenType=jTokenizer.tokenTypeWrapper();
        if(!hasReadFirstToken){
            jTokenizer.advance();
        }
        // ('constructor'|'function'|'method')
        if(TokenType.KEYWORD.equals(tokenType.getTokenType()) && 
        (KeyWord.CONSTRUCTOR.equals(jTokenizer.keyword()) || KeyWord.FUNCTION.equals(jTokenizer.keyword()) || KeyWord.METHOD.equals(jTokenizer.keyword()))){
            replaceKeyword();
        }
        // ('void'|<type>)
        jTokenizer.advance();
        if(TokenType.KEYWORD.equals(tokenType.getTokenType())){
            if(KeyWord.VOID.equals(jTokenizer.keyword())){
                replaceKeyword();
            }
        }else if(TokenType.IDENTIFIER.equals(tokenType.getTokenType())){
        }else{

        }
        // <subroutineName>
        jTokenizer.advance();
        if(TokenType.IDENTIFIER.equals(tokenType.getTokenType())){
            pWriter.println();
        }
        pWriter.println(END_TAG.replace(TAG_STR, Structure.SUBROUTINE_DEC.getStructure()));
        replaceEndStructure(Structure.SUBROUTINE_DEC);
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

    public void replaceStartStructure(Structure structure){
    }

    public void replaceEndStructure(Structure structure){
    }

    public void replaceIdentifier(){
    }

    public void replaceKeyword(){
    }

    public void replaceSymbol(){
    }



}
