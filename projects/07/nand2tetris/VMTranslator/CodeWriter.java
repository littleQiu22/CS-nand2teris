package nand2tetris.VMTranslator;

import java.util.HashMap;
import java.util.Map;

/*
 * @Author: 邱俊彦 (Junyan Qiu)
 * @Created date: 2022-10-20
 * @Lastest modified date: 2022-10-20
 * @Description: CodeWriter is a class which map a single VM command into Hack Assembler commands.  
 * @Usage: String assembler=CodeWriter.translate(String command);
 * @Implementation details: 1. R13 and R14 are used for storage for arithmetic command.  R15 is used for temporary storage.  
 */
public class CodeWriter {
    private static Parser parser=new Parser();
    private static Map<Integer,String> commandType2Snippet=new HashMap<>();
    private static Map<String,String> VMMemory2HackMemory=new HashMap<>();
    private static Map<String,String> symbolicMath2Math=new HashMap<>();
    static{
        // commandType to hack assembly language
        commandType2Snippet.put(Parser.C_PUSH_SEGMENT, "//push <rawSegment> <index>\n@<index>\nD=A\n@<segment>\nA=D+A\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1");
        commandType2Snippet.put(Parser.C_PUSH_CONSTANT, "//push constant <index>\n@<index>\nD=A\n@SP\nA=M\nM=D\n@SP\nM=M+1");
        commandType2Snippet.put(Parser.C_POP_SEGMENT, "//pop <rawSegment> <index>\n@SP\nM=M-1\n@<index>\nD=A\n@<segment>\nD=D+M\n@R15\nM=D\n@SP\nD=M\n@R15\nA=M\nM=D");
        commandType2Snippet.put(Parser.C_POP_CONSTANT, "//pop constant <index>\n@SP\nM=M-1\nA=M\nD=M\n@<index>\nM=D");
        commandType2Snippet.put(Parser.C_ARITHMETIC, "//<operand>\n@R13\nD=M\n@R14\nD=D<mathOperand>M\n@SP\nA=M\nM=D\n@SP\nM=M+1");
        commandType2Snippet.put(Parser.C_END_LOOP,"//end loop\n(AUTO_END)\n@AUTO_END\n0;JMP");

        // vm memory to hack assembly memory
        VMMemory2HackMemory.put("local", "LCL");
        VMMemory2HackMemory.put("argument", "ARG");
        VMMemory2HackMemory.put("this", "THIS");
        VMMemory2HackMemory.put("that", "THAT");
        VMMemory2HackMemory.put("temp", "TEMP");
        VMMemory2HackMemory.put("static", "Foo");
        VMMemory2HackMemory.put("pointer", "3");

        // symbolic math operand to mathematic operand
        symbolicMath2Math.put("add", "+");
        symbolicMath2Math.put("sub", "-");
    }

    public static String translate(String command){
        parser.setCommand(command);
        int commandType=parser.getCommandType();
        if(Parser.C_PUSH_SEGMENT==commandType || Parser.C_PUSH_CONSTANT==commandType || Parser.C_POP_SEGMENT==commandType || Parser.C_POP_CONSTANT==commandType){
            return translatePushOrPop(command);
        }else if(Parser.C_ARITHMETIC==commandType){
            return translateArithmetic(command);
        }else{
            return null;
        }
    }

    public static String translatePushOrPop(String command){
        parser.setCommand(command);
        int commandType=parser.getCommandType();
        String segment=parser.getArg1();
        String segmentMapped=VMMemory2HackMemory.get(segment);
        if(segmentMapped==null){
            segmentMapped=segment;
        }
        String index=parser.getArg2();
        String assemblerCommands=commandType2Snippet.get(commandType);
        assemblerCommands=assemblerCommands.replace("<rawSegment>", segment);
        assemblerCommands=assemblerCommands.replace("<segment>", segmentMapped);
        assemblerCommands=assemblerCommands.replace("<index>", index);
        return assemblerCommands;
    }

    public static String translateArithmetic(String command){
        // add,sub
        parser.setCommand(command);
        int commandType=parser.getCommandType();
        String symbolicOperand=parser.getArg1();
        String popVMCommand1="pop constant R13";
        String popVMCommand2="pop constant R14";
        String assemblerCommand1=translatePushOrPop(popVMCommand1);
        String assemblerCommand2=translatePushOrPop(popVMCommand2);
        String assemblerCommand3=commandType2Snippet.get(commandType);
        assemblerCommand3=assemblerCommand3.replace("<operand>", symbolicOperand);
        assemblerCommand3=assemblerCommand3.replace("<mathOperand>", symbolicMath2Math.get(symbolicOperand));
        return assemblerCommand1+"\n"+assemblerCommand2+"\n"+assemblerCommand3;
    }

    public static String getAutoEndLoop(){
        return commandType2Snippet.get(Parser.C_END_LOOP);
    }
}
