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
        pWriter.println();
        TokenType tokenType=jTokenizer.tokenType();
        // 'class'(\s+)<className>(\s*)'{'(\s)*(<classVarDec(\s)*>*(<subroutineDec>(\s)*)*)*'}'   
        
        // 'class'
        jTokenizer.advance();
        if(!(TokenType.KEYWORD.equals(tokenType) && jTokenizer.keyword().equals(KeyWord.CLASS))){
            System.out.println("'class' error");
        }else{
            pWriter.println(TAG_ATTR.replace(TAG_STR, TokenType.KEYWORD.getToken()).replace(ATTR_STR, TokenType.KEYWORD.getToken()));
        }
        // <className>
        jTokenizer.advance();
        if(!TokenType.IDENTIFIER.equals(tokenType)){
            System.out.println("<className> error");
        }else{
            pWriter.println(TAG_ATTR.replace(TAG_STR,TokenType.IDENTIFIER.getToken()).replace(ATTR_STR, jTokenizer.identifier()));
        }
        // '{'
        jTokenizer.advance();
        if(!TokenType.SYMBOL.equals(tokenType) && Symbol.LEFT_CURLY_BRACKET.getSymbol().equals(jTokenizer.symbol())){
            System.out.println("'{'' error");
        }else{
            pWriter.println(TAG_ATTR.replace(TAG_STR, TokenType.SYMBOL.getToken()).replace(ATTR_STR, Symbol.LEFT_CURLY_BRACKET.getSymbol()));
        }

        // ((<classVarDec>\s*)*(<subroutineDec>\s*)*)*
        while(jTokenizer.hasToken()){
            jTokenizer.advance();
            //  case: <classVarDec>
            if(TokenType.KEYWORD.equals(tokenType) && (KeyWord.STATIC.equals(jTokenizer.keyword())|| KeyWord.FIELD.equals(jTokenizer.keyword()))){
                compileClassVarDec();
            }else if(TokenType.KEYWORD.equals(tokenType) && 
            (KeyWord.CONSTRUCTOR.equals(jTokenizer.keyword()) || KeyWord.FUNCTION.equals(jTokenizer.keyword()) || KeyWord.METHOD.equals(jTokenizer.keyword()))){
                // case: <subroutineDec>
                compileSubroutine();
            }else{
                break;
            }
        }
        jTokenizer.advance();
        if(!TokenType.SYMBOL.equals(tokenType) && Symbol.RIGHT_CURLY_BRACKET.getSymbol().equals(jTokenizer.symbol())){
            System.out.println("'}'' error");
        }else{
            pWriter.println(TAG_ATTR.replace(TAG_STR, TokenType.SYMBOL.getToken()).replace(ATTR_STR, Symbol.RIGHT_BRACKET.getSymbol()));
        }
    }

    // static variable declaration
    public void compileClassVarDec(){
        // ('static' | 'field')(\s+)<type>(\s+)<varName>(\s*','\s*<varName>)*\s*';' 
        jTokenizer.advance();
        TokenType tokenType=jTokenizer.tokenType();
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

    }

    // method, function, constructor
    public void compileSubroutine(){
        // ('constructor'|'function'|'method')(\s+)('void'|<type>)\s+<subroutineName>\s*'('\s*<parameterList>\s*')'\s*subroutineBody

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
