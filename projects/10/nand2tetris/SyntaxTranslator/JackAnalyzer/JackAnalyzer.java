package nand2tetris.SyntaxTranslator.JackAnalyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import nand2tetris.SyntaxTranslator.CompileEngine.CompileEngine;
import nand2tetris.SyntaxTranslator.JackTokenizer.JackTokenizer;

class JackAnalyzer{
    private boolean isDirectory;
    private String source;

    public JackAnalyzer(){
        isDirectory=true;
        source=".";
    }

    public JackAnalyzer(String sourcePath) throws IOException{
        File f=new File(sourcePath);
        if(!f.exists()){
            System.out.println("Source does not exist");
            return;
        }
        if(f.isDirectory()){
            isDirectory=true;
        }else{
            if(!f.getCanonicalPath().endsWith(".jack")){
                System.out.println("File does not have the suffix \".jack\"");
            }
        }
        source=sourcePath;
    }

    public void analyze() throws IOException {
        File[] fs=null;
        File f=new File(source);
        FileFilter fileFilter=new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                try {
                    String path= pathname.getCanonicalPath();
                    if(path!=null && path.endsWith(".jack")){
                        return true;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            }
        };
        if(isDirectory){
            fs=f.listFiles(fileFilter);
            if(fs==null){
                System.out.println("Directory does not contain file which named \".jack\"");
                return;
            }
        }else{
            fs=new File[]{f};
        }
        JackTokenizer jTokenizer=new JackTokenizer();
        CompileEngine compileEngine=new CompileEngine(jTokenizer);
        for(File ff:fs){
            jTokenizer.setFile(ff);
            jTokenizer.read();
            File targetFile=new File(ff.getCanonicalPath().replace(".jack", ".xml"));
            compileEngine.setFile(targetFile);
            compileEngine.compile();
        }
    }
}