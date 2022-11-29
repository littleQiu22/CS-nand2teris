package nand2tetris.SyntaxTranslator.CompileEngine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import nand2tetris.SyntaxTranslator.JackTokenizer.JackTokenizer;
import nand2tetris.SyntaxTranslator.JackTokenizer.KeyWord;
import nand2tetris.SyntaxTranslator.JackTokenizer.Symbol;
import nand2tetris.SyntaxTranslator.JackTokenizer.TokenType;

public class CompileEngine {
    private JackTokenizer jTokenizer;
    private File targetFile;
    private static String irreducibleTemplate="<tag> attr </tag>"; 
    private final static String TEMPLATE_REP_WORD1="tag";
    private final static String TEMPLATE_REP_WORD2="attr";
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
        pWriter.println("<class>");
        compileClass();
        pWriter.println("/class");
        pWriter.close();
    }

    // decompose the whole compilation into mulitple samll compilations

    // the whole structure of class
    public void compileClass(){
        TokenType tokenType=jTokenizer.tokenType();
        // 'class'(\s+)<className>(\s*)'{'(\s)*(<classVarDec(\s)*>*(<subroutineDec>(\s)*)*)*'}'   
        
        // 'class'
        jTokenizer.advance();
        if(!(TokenType.KEYWORD.equals(tokenType) && jTokenizer.keyword().equals(KeyWord.CLASS))){
            System.out.println("'class' error");
        }else{
            pWriter.println(irreducibleTemplate.replace(TEMPLATE_REP_WORD1, TokenType.KEYWORD.getToken()).replace(TEMPLATE_REP_WORD2, TokenType.KEYWORD.getToken()));
        }
        // <className>
        jTokenizer.advance();
        if(!TokenType.IDENTIFIER.equals(tokenType)){
            System.out.println("<className> error");
        }else{
            pWriter.println(irreducibleTemplate.replace(TEMPLATE_REP_WORD1,TokenType.IDENTIFIER.getToken()).replace(TEMPLATE_REP_WORD2, jTokenizer.identifieer()));
        }
        // '{'
        jTokenizer.advance();
        if(!TokenType.SYMBOL.equals(tokenType) && Symbol.LEFT_CURLY_BRACKET.getSymbol().equals(jTokenizer.symbol())){
            System.out.println("'{'' error");
        }else{
            pWriter.println(irreducibleTemplate.replace(TEMPLATE_REP_WORD1, TokenType.SYMBOL.getToken()).replace(TEMPLATE_REP_WORD2, Symbol.LEFT_CURLY_BRACKET.getSymbol()));
        }

        // ((<classVarDec>\s*)*(<subroutineDec>\s*)*)*
        while(jTokenizer.hasToken()){
            jTokenizer.advance();
            //  case: <classVarDec>
            if(TokenType.KEYWORD.equals(tokenType) && (KeyWord.STATIC.equals(jTokenizer.keyword())|| KeyWord.FIELD.equals(jTokenizer.keyword()))){
                compileClassVarDec();
            }else{
                // case: <subroutineDec>
                compileSubroutine();
            }
        }

        jTokenizer.advance();
        if(!TokenType.SYMBOL.equals(tokenType) && Symbol.RIGHT_CURLY_BRACKET.getSymbol().equals(jTokenizer.symbol())){
            System.out.println("'}'' error");
        }else{
            pWriter.println(irreducibleTemplate.replace(TEMPLATE_REP_WORD1, TokenType.SYMBOL.getToken()).replace(TEMPLATE_REP_WORD2, Symbol.RIGHT_BRACKET.getSymbol()));
        }
    }

    // static variable declaration
    public void compileClassVarDec(){

    }

    // method, function, constructor
    public void compileSubroutine(){

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



}
