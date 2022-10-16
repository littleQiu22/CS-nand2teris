package nand2tetris.HackAssembler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/*
 * @Author: 邱俊彦 (Junyan Qiu)
 * @Created date: 2022-10-16
 * @Lastest modified date: 2022-10-16
 * @Description: Main is a class which control the process of assembling. The process includes: 
 *  1. reading source file line by line
 *  2. parse every line into binary representation
 *  3. concat each binary represetation into a new file
 * @Usage: Main main=new Main(<sourceFilePath>,<destFilePath>); main.compile();
 */
public class Main{
    private String sourceFilePath;
    private String destFilePath;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;
    private int lineNumber=0;
    private SymbolTable symbolTable=new SymbolTable();
    private Parser parser=new Parser();
    
    public Main(){}

    public Main(String sourceFilePath){
        this.sourceFilePath=sourceFilePath;
        this.destFilePath=sourceFilePath.replace(".asm", ".hack");
    }

    public Main(String sourceFilePath,String destFilePath){
        this.sourceFilePath=sourceFilePath;
        this.destFilePath=destFilePath;
    }

    public boolean isNumber(String str){
        for(int i=0;i<str.length();i++){
            if(!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }

    public void compile(){
        // read file and write file alternatively
        try {
            // first pass to replace label to number
            bufferedReader=new BufferedReader(new FileReader(sourceFilePath));
            String instruction=null;
            while((instruction=bufferedReader.readLine())!=null){
                // ignore whitespace
                instruction=instruction.replace(" ", "");
                // ignore comment 
                int commentIdx=instruction.indexOf("//");
                if(commentIdx>=0){
                    instruction=instruction.substring(0,commentIdx);
                }
                if(instruction.length()==0){
                    continue;
                }
                boolean isLabel=instruction.length()>0 && instruction.charAt(0)=='(';
                if(isLabel){
                    String labelSymbol=instruction.substring(1,instruction.length()-1);
                    symbolTable.setSymbol(labelSymbol, lineNumber);
                }
                // increase linenumber if not label
                if(!isLabel){
                    lineNumber++;
                }
            }
            bufferedReader.close();
            // second pass to replace variable to number and to binary instruction
            bufferedReader=new BufferedReader(new FileReader(sourceFilePath));
            printWriter=new PrintWriter(new File(destFilePath));
            String binaryInstruction=null;
            while((instruction=bufferedReader.readLine())!=null){
                // ignore whitespace
                instruction=instruction.replace(" ", "");
                // ignore comment 
                int commentIdx=instruction.indexOf("//");
                if(commentIdx>=0){
                    instruction=instruction.substring(0,commentIdx);
                }
                if(instruction.length()==0){
                    continue;
                }
                // ignore label
                if(instruction.charAt(0)=='('){
                    continue;
                }
                // A-instruction
                boolean isAIns=instruction.charAt(0)=='@';
                if(isAIns){
                    String symbol=instruction.substring(1);
                    Integer num=null;
                    if(!isNumber(symbol)){
                        if(symbolTable.getNum(symbol)==null){
                            symbolTable.setSymbol(symbol);
                        }
                        num=symbolTable.getNum(symbol);
                    }else{
                        num=Integer.valueOf(symbol);
                    }
                    binaryInstruction=Code.translateDecimalNum(num);
                    
                }else{
                    // C-instruction
                    parser.setCInstruction(instruction);
                    String comp=parser.getComp();
                    String dest=parser.getDest();
                    String jump=parser.getJump();
                    String binaryComp=Code.translateComp(comp);
                    String binaryDest=Code.translateDest(dest);
                    String binaryJump=Code.translateJump(jump);
                    binaryInstruction="111"+binaryComp+binaryDest+binaryJump;
                }
                printWriter.println(binaryInstruction);
            }
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

    public static void main(String[] args){
        int len=args.length;
        if(len==0){
            System.out.println("Compile failed! Need source file");
        }else if(len==1){
            Main main=new Main(args[0]);
            main.compile();
        }else if(len==2){
            Main main=new Main(args[0],args[1]);
            main.compile();
        }else{
            System.out.println("Compile failed! At most source file and destination file are needed");
        }
    }
}