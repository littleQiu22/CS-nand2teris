package nand2tetris.SyntaxTranslator.JackTokenizer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class JackTokenizer {
    private File f;
    BufferedReader br;

    public JackTokenizer(){}

    public JackTokenizer(File f){
        this.f=f;
    }

    public void setFile(File f){
        this.f=f;
    }

    public void read() throws FileNotFoundException{
        br=new BufferedReader(new FileReader(f));
    }

    public boolean advance(){
        // BufferedReader br=null;
        // try{
        //     br=new BufferedReader(new FileReader(ff));
        //     String strs=null;
        //     while((strs=br.readLine())!=null){
        //         String[] 
        //     }
        // }finally{
        //     br.close();
        // }
    }

    public TokenWrapper tokenTypeWrapper(){

    }

    public KeyWord keyword(){

    }

    public String symbol(){

    }

    public String identifier(){

    }

    public int intVal(){

    }

    public String stringVal(){

    }


}
