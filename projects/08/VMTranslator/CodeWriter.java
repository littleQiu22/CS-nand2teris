package nand2tetris.VMTranslator;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * @Author: 邱俊彦 (Junyan Qiu)
 * @Created date: 2022-10-20
 * @Lastest modified date: 2022-10-20
 * @Description: CodeWriter is a class which map a single VM command into Hack Assembler commands.  
 * @Usage: String assembler=CodeWriter.translate(String command);
 * @Implementation details: 1. R13 and R14 are used for storage for arithmetic command.  R15 is used for temporary storage.  
 */
public class CodeWriter {
    private static final String defaultFunctionName="AUTO_ENTRY_FUNC";
    private static Map<Integer,String> commandType2Snippet=new HashMap<>();
    private static Map<String,String> VMMemory2HackMemory=new HashMap<>();
    private static Map<String,String> symbolicMath2Math=new HashMap<>();
    private static Map<String,String> symbolicComp2JumpCond=new HashMap<>();

    static{
        // commandType to hack assembly language
        // push
        commandType2Snippet.put(Parser.C_PUSH_SEGMENT_PTR, "//push <segmentName> <index>\n@<index>\nD=A\n@<segmentPtrOrAddr>\nA=D+M\nD=M\n@SP\nM=M+1\nA=M-1\nM=D");
        /*
         * // push <segmentName> <index> [<segmentName> is used for pointer, not just a address symbolic]
         * @<index>
         * D=A
         * @<segmentPtrOrAddr>
         * A=D+M
         * D=M
         * @SP
         * M=M+1
         * A=M-1
         * M=D
         */

        commandType2Snippet.put(Parser.C_PUSH_SEGMENT_ADDR, "//push <segmentName> <index>\n@<segmentPtrOrAddr>\nD=A\n@<index>\nA=D+A\nD=M\n@SP\nM=M+1\nA=M-1\nM=D");
        /*
         * // push <segmentName> <index> [<segmentName> is used for address symbolic, not for a pointer]
         * @<index>
         * D=A
         * @<segmentPtrOrAddr>
         * A=D+A
         * D=M
         * @SP
         * M=M+1
         * A=M-1
         * M=D
         */

         commandType2Snippet.put(Parser.C_PUSH_SEGMENT_ADDR_WITHOUT_IDX, "// push <segmentName>\n@<segmentPtrOrAddr>\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1");
        /*
         * // push <segmentName> [<segmentName> is used for address symbolic, not for a pointer]
         * @<segmentPtrOrAddr>
         * D=M
         * @SP
         * M=M+1
         * A=M-1
         * M=D
         */

        commandType2Snippet.put(Parser.C_PUSH_CONSTANT, "//push constant <index>\n@<index>\nD=A\n@SP\nM=M+1\nA=M-1\nM=D");
        /*
         * // push constant <index>
         * @<index>
         * D=A
         * @SP
         * M=M+1
         * A=M-1
         * M=D
         */

        // pop
        commandType2Snippet.put(Parser.C_POP_SEGMENT_PTR, "//pop <segmentName> <index>\n@<index>\nD=A\n@<segmentPtrOrAddr>\nD=D+M\n@R15\nM=D\n@SP\nM=M-1\nA=M\nD=M\n@R15\nA=M\nM=D");
        /* // pop <segmentName> <index> [<segmentName> is used for pointer, not just a address symbolic]
         * @<index>
         * D=A
         * @<segmentPtrOrAddr>
         * D=D+M
         * @R15
         * M=D
         * @SP
         * M=M-1
         * A=M
         * D=M
         * @R15
         * A=M
         * M=D
         */

        commandType2Snippet.put(Parser.C_POP_SEGMENT_ADDR, "//pop <segmentName> <index>\n@<segmentPtrOrAddr>\nD=A\n@<index>\nD=D+A\n@R15\nM=D\n@SP\nM=M-1\nA=M\nD=M\n@R15\nA=M\nM=D");
        /*
         * // pop <segmentName> <index> [<segmentName> is used for address symbolic, not for a pointer]
         * @<index>
         * D=A
         * @<segmentPtrOrAddr>
         * D=D+A
         * @R15
         * M=D
         * @SP
         * M=M-1
         * A=M
         * D=M
         * @R15
         * A=M
         * M=D
         */

        commandType2Snippet.put(Parser.C_POP_SEGMENT_ADDR_WITHOUT_IDX, "//pop <segmentName>\n@SP\nM=M-1\nA=M\nD=M\n@<segmentPtrOrAddr>\nM=D");
        /*
         * // pop <segmentName> [<segmentName> is used for address symbolic, not for a pointer]
         * @SP
         * M=M-1
         * A=M
         * D=M
         * @<segmentPtrOrAddr>
         * M=D
         */

        // arithmetic
        commandType2Snippet.put(Parser.C_ARITHMETIC_ADD_OR_SUB, "//<operand>\n@SP\nM=M-1\nA=M-1\nD=M\nA=A+1\nD=D<mathOperand>M\nA=A-1\nM=D");
        /*
         * // <oprand>
         * @SP
         * M=M-1
         * A=M-1
         * D=M
         * A=A+1
         * D=D<mathOperand>M
         * A=A-1
         * M=D
         */

        commandType2Snippet.put(Parser.C_ARITHMETIC_NEG, "//neg\n@SP\nA=M-1\nM=-M");
        /*
         * // neg
         * @SP
         * A=M-1
         * M=-M
         */

        // logical
        commandType2Snippet.put(Parser.C_LOGICAL_AND_OR_OR,"//<operand>\n@SP\nM=M-1\nA=M\nD=M\nA=A-1\nM=D<mathOperand>M");
        /*
         * // <oprand>
         * @SP
         * M=M-1
         * A=M
         * D=M
         * A=A-1
         * M=D<mathOperand>M 
         */

        commandType2Snippet.put(Parser.C_LOGICAL_NOT,"//not\n@SP\nA=M-1\nM=!M");
        /*
         * // not
         * @SP
         * A=M-1
         * M=!M
         */

        // comparison
        commandType2Snippet.put(Parser.C_COMPARISON,"//<comparison>\n@SP\nM=M-1\nA=M\nD=M\nA=A-1\nD=D-M\nM=0\n@AUTO_TRUE_LABEL_<idx>\nD;<jumpCond>\n@AUTO_END_LABEL_<idx>\n0;JMP\n(AUTO_TRUE_LABEL_<idx>)\n@SP\nA=M-1\nM=-1\n(AUTO_END_LABEL_<idx>)");
        /*
         * // <comparison>
         * @SP
         * M=M-1
         * A=M
         * D=M
         * A=A-1
         * D=D-M
         * M=0
         * @ AUTO_TRUE_LABEL_<idx>
         * D;<jumpCond>
         * @ AUTO_END_LABEL_<idx>
         * 0;JMP
         * (AUTO_TRUE_LABEL)
         * @SP
         * A=M-1
         * M=-1
         * (AUTO_END_LABEL)
         */

        // endloop
        commandType2Snippet.put(Parser.C_END_LOOP, "//auto end loop\n(AUTO_END_LOOP)\n@AUTO_END_LOOP\n0;JMP");
        /*
         * // auto end loop
         * (AUTO_END_LOOP)
         * @AUTO_END_LOOP
         * 0;JMP 
         */

        // define function
        // commandType2Snippet.put(Parser.C_DEFINE_FUNCTION,"// function <functionName> <nVars>\n(<fileName>.<functionName>)\n@R13\nM=<nVars>\n(<fileName>.<functionName>.initLoop)\n<<push constant 0>>\n@R13\nM=M-1\nD=M\n@<fileName>.<functionName>.initLoop\nD;JGT");
        commandType2Snippet.put(Parser.C_DEFINE_FUNCTION,"// function <functionName> <nVars>\n(<fileName>.<functionName>)\nD=<nVars>\n(<fileName>.<functionName>.initLoop)\n<<push constant 0>>\nD=D-1\n@<fileName>.<functionName>.initLoop\nD;JGT");
        /*
         * (<fileName>.<functionName>)
         * D=<nVars>
         * (<fileName>.<functionName>.initLoop)
         * <<push constant 0>> [need be translated into asm]
         * D=D-1
         * @<fileName>.<functionName>.initLoop
         * D;JGT
         */
        
        /*
         * (<fileName>.<functionName>)
         * @R13
         * M=<nVars>
         * (<fileName>.<functionName>.initLoop)
         * <<push constant 0>> [need be translated into asm]
         * @R13
         * M=M-1
         * D=M
         * @<fileName>.<functionName>.initLoop
         * D;JGT
         */

        // call function
        // commandType2Snippet.put(Parser.C_CALL_FUNCTION,"// call <functionName> <nArgs>\\@<fileName>.<functionName>$ret<i>\nD=A\n@R13\nM=D\n<<push R13 0>>\n<<push LCL 0>>\n<<push ARG 0>>\n<<push THIS 0>>\n<<push THAT 0>>\n<<push SP 0>>\n<<push constant <num>>>\n<<sub>>\n<<pop ARG 0>>\n@SP\nD=M\n@LCL\nM=D\n@<calleeFileName>.<calleeFunctionName>\n0;JMP;(<fileName>.<functionName>$ret<i>)");
        commandType2Snippet.put(Parser.C_CALL_FUNCTION, "// call <functionName> <nArgs>\\@<fileName>.<functionName>$ret<i>\nD=A\n@SP\nM=M+1\nA=M-1\nM=D\n<<push LCL>>\n<<push ARG>>\n<<push THIS>>\n<<push THAT>>\n<<push SP>>\n<<push constant <num>>>\n<<sub>>\n<<pop ARG>>\n@SP\nD=M\n@LCL\nM=D\n@<calleeFileName>.<calleeFunctionName>\n0;JMP;(<fileName>.<functionName>$ret<i>)");
        /*
         * @<fileName>.<functionName>$ret<i>
         * D=A
         * @SP
         * M=M+1
         * A=M-1
         * M=D
         * <<push LCL>> [PUSH_SEGMENT_ADDR]
         * <<push ARG>> [PUSH_SEGMENT_ADDR]
         * <<push THIS>> [PUSH_SEGMENT_ADDR]
         * <<push THAT>> [PUSH_SEGMENT_ADDR]
         * <<push SP>> [PUSH_SEGMENT_ADDR]
         * <<push constant <num>>>
         * <<sub>>
         * <<pop ARG>> [POP_SEGMENT_ADDR]
         * @SP
         * D=M
         * @LCL
         * M=D
         * @<calleeFileName>.<calleeFunctionName>
         * 0;JMP
         * (<fileName>.<functionName>$ret<i>)
         */
        
        /*
         * @<fileName>.<functionName>$ret<i>
         * D=A
         * @R13
         * M=D
         * <<push R13>> [need into asm] [PUSH_SEGMENT_ADDR]
         * <<push LCL>> [PUSH_SEGMENT_ADDR]
         * <<push ARG>> [PUSH_SEGMENT_ADDR]
         * <<push THIS>> [PUSH_SEGMENT_ADDR]
         * <<push THAT>> [PUSH_SEGMENT_ADDR]
         * <<push SP>> [PUSH_SEGMENT_ADDR]
         * <<push constant <num>>>
         * <<sub>>
         * <<pop ARG>> [POP_SEGMENT_ADDR]
         * @SP
         * D=M
         * @LCL
         * M=D
         * @<calleeFileName>.<calleeFunctionName>
         * 0;JMP
         * (<fileName>.<functionName>$ret<i>)
         */

        // return
        commandType2Snippet.put(Parser.C_RETURN, "// return\n<<pop argument 0>>\n@ARG\nD=M+1\n@SP\nM=D\n@LCL\nD=M\n@R13\nM=D-1\nA=M\nD=M\n@THAT\nM=D\n@R13\nM=M-1\nA=M\nD=M\n@THIS\nM=D\n@R13\nM=M-1\nA=M\nD=M\n@ARG\nM=D\n@R13\nM=M-1\nA=M\nD=M\nA=D\n0;JMP");
        /*
         * <<pop argument 0>> [POP_SEGMENT_PTR]
         * 
         * @ARG
         * D=M+1
         * @SP
         * M=D
         * 
         * @LCL
         * D=M
         * 
         * @R13
         * M=D-1
         * A=M
         * D=M
         * @THAT
         * M=D
         * 
         * @R13
         * M=M-1
         * A=M
         * D=M
         * @THIS
         * M=D
         * 
         * @R13
         * M=M-1
         * A=M
         * D=M
         * @ARG
         * M=D
         * 
         * @R13
         * M=M-1
         * A=M
         * D=M
         * @LCL
         * M=D
         * 
         * @R13
         * M=M-1
         * A=M
         * D=M
         * A=D
         * 0;JMP
         * 
         */

        // goto label
        commandType2Snippet.put(Parser.C_GOTO,"// goto <label>\n@<fileName>.<functionName>$<label>\n0;JMP");
        /*
         * @<fileName>.<functionName>$<label>
         * 0;JMP
         */

        // if-goto label
        commandType2Snippet.put(Parser.C_IF_GOTO,"// if-goto <label>\n<<pop R13 0>>\n@R13\nD=M\n@<fileName>.<functionName>$<label>\nD;JNE");
        /*
         * <<pop R13 0>> [POP_SEGMENT_ADDR]
         * @R13
         * D=M
         * @<fileName>.<functionName>$<label>
         * D;JNE 
         */

        //  label 
        commandType2Snippet.put(Parser.C_LABEL,"// label <labelName>\n(<fileName>.<functionName>$<labelName>)");

        // vm memory to hack assembly memory
        // pointer map
        VMMemory2HackMemory.put("local", "LCL");
        VMMemory2HackMemory.put("argument", "ARG");
        VMMemory2HackMemory.put("this", "THIS");
        VMMemory2HackMemory.put("that", "THAT");
        // initial address map
        VMMemory2HackMemory.put("temp", "5");
        VMMemory2HackMemory.put("static", "16");
        VMMemory2HackMemory.put("pointer", "3");

        // symbolic math operand to mathematic operand
        symbolicMath2Math.put("add", "+");
        symbolicMath2Math.put("sub", "-");
        symbolicMath2Math.put("and","&");
        symbolicMath2Math.put("or","|");
        
        // symbolic comparison to jump condition
        symbolicComp2JumpCond.put("gt", "JLT");
        symbolicComp2JumpCond.put("lt","JGT");
        symbolicComp2JumpCond.put("eq","JEQ"); 
    }
    private IntWrapper intWrapperForComp=new IntWrapper();
    private IntWrapper defaultIntWrapperForFunc=new IntWrapper();
    private IntWrapper curIntWrapperForFunc=new IntWrapper();
    private Parser parser=new Parser();
    private String curFileName;
    private String curFunctionName;

    public CodeWriter(){}

    public CodeWriter(String curFileName){
        this.curFileName=curFileName;
    }

    public String translate(String command){
        parser.setCommand(command);
        int commandType=parser.getCommandType();
        boolean pushOrPop=Parser.C_PUSH_CONSTANT==commandType || Parser.C_PUSH_SEGMENT_ADDR==commandType || Parser.C_PUSH_SEGMENT_PTR==commandType || Parser.C_PUSH_SEGMENT_ADDR_WITHOUT_IDX==commandType || Parser.C_POP_SEGMENT_ADDR==commandType || Parser.C_POP_SEGMENT_PTR==commandType || Parser.C_POP_SEGMENT_ADDR_WITHOUT_IDX==commandType;
        boolean arithmeticOrLogical=Parser.C_ARITHMETIC_ADD_OR_SUB==commandType || Parser.C_ARITHMETIC_NEG==commandType || Parser.C_LOGICAL_AND_OR_OR==commandType || Parser.C_LOGICAL_NOT==commandType;
        boolean comparison=Parser.C_COMPARISON==commandType;
        boolean callFunc=Parser.C_CALL_FUNCTION==commandType;
        boolean defFunc=Parser.C_DEFINE_FUNCTION==commandType;
        boolean funcReturn=Parser.C_RETURN==commandType;
        boolean goToOrLabel=Parser.C_GOTO==commandType || Parser.C_IF_GOTO==commandType || Parser.C_LABEL==commandType;
        if(pushOrPop){
            return translatePushOrPop(command);
        }else if(arithmeticOrLogical){
            return translateArithmeticOrLogical(command);
        }else if(comparison){
            return translateComparison(command);
        }else if(callFunc){
            return translateCallFunc(command);
        }else if(defFunc){
            return translateDefFunc(command);
        }else if(funcReturn){
            return translateFuncReturn(command);
        }else if(goToOrLabel){
            return translateGoToOrLabel(command);
        }else{
            return null;
        }
    }

    public String vmSecondTranslate(String mixedCommand){
        Pattern pattern=Pattern.compile("<<.+>>");
        Matcher matcher=pattern.matcher(mixedCommand);
        String assemblerCommands="";
        int prevIdx=0;
        while(matcher.find()){
            int stIdx=matcher.start();
            int edIdx=matcher.end();
            String vmCommand=mixedCommand.substring(stIdx, edIdx);
            if(stIdx>prevIdx){
                assemblerCommands+=mixedCommand.substring(prevIdx, stIdx-1);
            }
            assemblerCommands+=translate(vmCommand);
            prevIdx=edIdx;
        }
        parser.setUseExInfo(false);
        return assemblerCommands;
    }

    public String translateGoToOrLabel(String command){
        parser.setCommand(command);
        int commandType=parser.getCommandType();
        String assemblerCommands=commandType2Snippet.get(commandType);
        String labelName=parser.getArg1();
        if(curFunctionName==null){
            curFunctionName=defaultFunctionName;
        }
        assemblerCommands=assemblerCommands.replace("<labelName>", labelName);
        assemblerCommands=assemblerCommands.replace("<fileName>", curFileName);
        assemblerCommands=assemblerCommands.replace("<functionName>", curFunctionName);
        parser.setUseExInfo(true);
        parser.setExPtrNotAddr(false);
        return vmSecondTranslate(assemblerCommands);
    }


    public String translateFuncReturn(String command){
        parser.setCommand(command);
        int commandType=parser.getCommandType();
        String assemblerCommands=commandType2Snippet.get(commandType);
        curFunctionName=null;
        curIntWrapperForFunc.setValue(0);
        return vmSecondTranslate(assemblerCommands);
    }

    public String translateCallFunc(String command){
        parser.setCommand(command);
        int commandType=parser.getCommandType();
        String assemblerCommands=commandType2Snippet.get(commandType);
        if(curFunctionName==null){
            curFunctionName=defaultFunctionName;
        }
        int reducedNum=5+Integer.parseInt(parser.getArg2());
        String[] calleeInfos=parser.getArg1().split("\\.");
        String calleeFileName=calleeInfos[0];
        String calleeFunctionName=calleeInfos[1];
        int callerReturnAddrIdx=-1;
        if(curFunctionName==null){
            callerReturnAddrIdx=defaultIntWrapperForFunc.getValue();
            defaultIntWrapperForFunc.incValue();
        }else{
            callerReturnAddrIdx=curIntWrapperForFunc.getValue();
            curIntWrapperForFunc.incValue();
        }
        assemblerCommands=assemblerCommands.replace("<fileName>", curFileName);
        assemblerCommands=assemblerCommands.replace("<functionName>", curFunctionName);
        assemblerCommands=assemblerCommands.replace("<num>", reducedNum);
        assemblerCommands=assemblerCommands.replace("<calleeFileName>", calleeFunctionName);
        assemblerCommands=assemblerCommands.replace("<calleeFunctionName>", calleeFunctionName);
        assemblerCommands=assemblerCommands.replace("<i>", ""+callerReturnAddrIdx);
        parser.setUseExInfo(true);
        parser.setExPtrNotAddr(false);
        return vmSecondTranslate(assemblerCommands);
    }


    public String translateDefFunc(String command){
        parser.setCommand(command);
        int commandType=parser.getCommandType();
        String assemblerCommands=commandType2Snippet.get(commandType);
        String[] callerInfos=parser.getArg1().split("\\.");
        String callerFileName=callerInfos[0];
        String callerFunctionName=callerInfos[1];
        String nVars=parser.getArg2();
        assemblerCommands=assemblerCommands.replace("<fileName>", callerFileName);
        assemblerCommands=assemblerCommands.replace("<functionName>", callerFunctionName);
        assemblerCommands=assemblerCommands.replace("<nVars>", nVars);
        curFunctionName=callerFunctionName;
        curIntWrapperForFunc.setValue(0);
        return vmSecondTranslate(assemblerCommands);
    }

    

    public String translatePushOrPop(String command){
        parser.setCommand(command);
        int commandType=parser.getCommandType();
        String segment=parser.getArg1();
        String segmentMapped=VMMemory2HackMemory.get(segment);
        if(segmentMapped==null){
            segmentMapped=segment;
        }
        String index=parser.getArg2();
        String assemblerCommands=commandType2Snippet.get(commandType);
        assemblerCommands=assemblerCommands.replace("<segmentName>", segment);
        assemblerCommands=assemblerCommands.replace("<segmentPtrOrAddr>", segmentMapped);
        assemblerCommands=assemblerCommands.replace("<index>", index);
        return assemblerCommands;
    }

    public String translateArithmeticOrLogical(String command){
        // add,sub,neg; and,or,not
        parser.setCommand(command);
        int commandType=parser.getCommandType();
        String symbolicOperand=parser.getArg1();
        String assemblerCommand=commandType2Snippet.get(commandType);
        assemblerCommand=assemblerCommand.replace("<operand>", symbolicOperand);
        assemblerCommand=assemblerCommand.replace("<mathOperand>", symbolicMath2Math.getOrDefault(symbolicOperand,symbolicOperand));
        return assemblerCommand;
    }

    public String translateComparison(String command){
        // comparison
        parser.setCommand(command);
        int commandType=parser.getCommandType();
        String comparison=parser.getArg1();
        String assemblerCommand=commandType2Snippet.get(commandType);
        assemblerCommand=assemblerCommand.replace("<comparison>", comparison);
        assemblerCommand=assemblerCommand.replace("<idx>", ""+intWrapper.getValue());
        assemblerCommand=assemblerCommand.replace("<jumpCond>", symbolicComp2JumpCond.getOrDefault(comparison,comparison));
        intWrapperForComp.incValue();
        return assemblerCommand;
    }

    public String getAutoEndLoop(){
        return commandType2Snippet.get(Parser.C_END_LOOP);
    }
}
