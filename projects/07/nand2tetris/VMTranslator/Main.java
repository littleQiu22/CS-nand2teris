package nand2tetris.VMTranslator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;



public class Main {
    private String sourceFilePath;
    private String destFilePath;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;
    private IntWrapper intWrapper=new IntWrapper();
    
    public Main(){}

    public Main(String sourceFilePath){
        this.sourceFilePath=sourceFilePath;
        this.destFilePath=sourceFilePath.replace(".vm", ".asm");
    }

    public Main(String sourceFilePath,String destFilePath){
        this.sourceFilePath=sourceFilePath;
        this.destFilePath=destFilePath;
    }

    public void translate(){
        try {
            bufferedReader=new BufferedReader(new FileReader(sourceFilePath));
            printWriter=new PrintWriter(new File(destFilePath));
            String vmCommand=null;
            // read each VM command and translate it into ASM commands
            while((vmCommand=bufferedReader.readLine())!=null){
                // ignore comment
                int commentIdx=vmCommand.indexOf("//");
                if(commentIdx>=0){
                    vmCommand=vmCommand.substring(0, commentIdx);
                }
                // ignore prefix and suffix whitespaces
                vmCommand=vmCommand.trim();
                if(vmCommand.length()==0){
                    continue;
                }
                String asmCommand=CodeWriter.translate(vmCommand,intWrapper);
                printWriter.println(asmCommand);
            }
            // add an auto-generated infinite loop for any VM file 
            printWriter.print(CodeWriter.getAutoEndLoop());
            // close resources
            bufferedReader.close();
            printWriter.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int len=args.length;
        if(len==0){
            System.out.println("Translation failed! Need source file.");
        }else if(len==1){
            Main vmTranslator=new Main(args[0]);
            vmTranslator.translate();
        }else if(len==2){
            Main vmTranslator=new Main(args[0],args[1]);
            vmTranslator.translate();
        }else{
            System.out.println("Translation failed! At most source file and destination file are needed.");
        }
    }
}
