package nand2tetris.VMTranslatorPlus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;



public class Main {
    private String sourceName;
    private String destFolder;
    private File[] sourceFileList;
    private File destFile;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;
    
    public Main(){}

    public Main(String sourceName){
        this.sourceName=sourceName;
    }

    public Main(String sourceName,String destFolder){
        this.sourceName=sourceName;
        this.destFolder=destFolder;
    }

    public boolean sourceDestCheck() throws IOException{
        // source
        File file=new File(sourceName);
        if(!file.exists()){
            return false;
        }
        if(file.isDirectory()){
            sourceFileList=file.listFiles();
        }
        if(file.isFile()){
            sourceFileList=new File[]{file};
        }
        // dest
        if(destFolder==null){
            if(file.isDirectory()){
                String[] strs=file.getCanonicalPath().split(File.separator);
                destFile=new File(file.getCanonicalPath(), strs[strs.length-1]+".asm");
            }
            if(file.isFile()){
                destFile=new File(sourceName.replace(".vm", ".asm"));
            }
        }else{
            File destFolderFile=new File(destFolder);
            if(!destFolderFile.exists()){
                return false;
            }else{
                String[] strs=file.getCanonicalPath().split(File.separator);
                if(file.isDirectory()){
                    destFile=new File(destFolder, strs[strs.length-1]+".asm");
                }
                if(file.isFile()){
                    destFile=new File(destFolder, strs[strs.length-1].replace(".vm", ".asm"));
                }
            }
        }
        return true;
    }

    public void translate(){
        try {
            boolean isSourceValid=sourceDestCheck();
            if(!isSourceValid){
                System.out.println("There are problems with source and destination.");
                return;
            }
            printWriter=new PrintWriter(destFile);
            for(File f:sourceFileList){
                if(f.isDirectory() || !(f.getName().endsWith(".vm"))){
                    continue;
                }
                bufferedReader=new BufferedReader(new FileReader(f));
                CodeWriter codeWriter=new CodeWriter(f.getName().replace(".vm", ""));
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
                    String asmCommand=codeWriter.translate(vmCommand);
                    printWriter.println(asmCommand);
                }
                // close resources
                bufferedReader.close();
            }
            // close resources
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
