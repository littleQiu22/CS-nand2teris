package nand2tetris.SyntaxTranslator.CompileEngine;

import java.io.File;

import nand2tetris.SyntaxTranslator.JackTokenizer.JackTokenizer;

public class CompileEngine {
    private JackTokenizer jTokenizer;
    private File targetFile;

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

    public void compile(){
        
    }
}
