package nand2tetris.VMTranslatorPlus;

/*
 * @Author: 邱俊彦 (Junyan Qiu)
 * @Created date: 2022-10-20
 * @Lastest modified date: 2022-10-20
 * @Description: Parser is a class which split a VM command into multiple predefined fields. The parser will ignore comment and empty line.  
 * @Usage: Parser parser=new Parser(String command); int commandType=parser.getCommandType(); String arg1=parser.getArg1(); int arg2=parser.getArg2(); 
 */
public class Parser {
    public static final int C_UNDEFINED=-2;
    public static final int C_END_LOOP=-1;
    // push
    // push <addressThatContainsBaseAddress> <index>
    // push <addressThatisInitialAddress> <index>
    // push constant <num>
    public static final int C_PUSH_SEGMENT_PTR=0;
    public static final int C_PUSH_SEGMENT_ADDR=1;
    public static final int C_PUSH_CONSTANT=2;
    // pop
    // pop <addressThatContainsBaseAddress> <index>
    // pop <addressThatisInitialAddress> <index>
    public static final int C_POP_SEGMENT_PTR=3;
    public static final int C_POP_SEGMENT_ADDR=4;
    // arithmetic: add,sub,neg
    public static final int C_ARITHMETIC_ADD_OR_SUB=5;
    public static final int C_ARITHMETIC_NEG=6;
    // logical:and,or,not
    public static final int C_LOGICAL_AND_OR_OR=7;
    public static final int C_LOGICAL_NOT=8;
    // comparison:eq,gt,lt
    public static final int C_COMPARISON=9;
    // define function: function fName nVars
    public static final int C_DEFINE_FUNCTION=10; 
    // call function: call fName nArgs
    public static final int C_CALL_FUNCTION=11; 
    // return
    public static final int C_RETURN=12;
    // goto and if-goto
    public static final int C_GOTO=13;
    public static final int C_IF_GOTO=14;
    // label
    public static final int C_LABEL=15;

    // additional patch
    public static final int C_PUSH_SEGMENT_ADDR_WITHOUT_IDX=16;
    public static final int C_POP_SEGMENT_ADDR_WITHOUT_IDX=17;
    public static final int C_POP_SEGMENT_PTR_WITHOUT_IDX=18;

    private String command;

    private boolean useExInfo;
    
    private boolean exPtrNotAddr;

    public Parser(){}

    public Parser(String command){
        this.command=command;    
    }

    public boolean getUseExInfo(){
        return useExInfo;
    }

    public boolean getExPtrNotAddr(){
        return exPtrNotAddr;
    }

    public void setUseExInfo(boolean useExInfo){
        this.useExInfo=useExInfo;
    }

    public void setExPtrNotAddr(boolean exPtrNotAddr){
        this.exPtrNotAddr=exPtrNotAddr;
    }

    public void setCommand(String command){
        this.command=command;
    }

    public int getCommandType(){
        String operand=command.split(" ")[0];
        String arg1=getArg1();
        boolean isPush="push".equals(operand);
        boolean isPop="pop".equals(operand);
        boolean isSegmentPtr=("local".equals(arg1) || "argument".equals(arg1) || "this".equals(arg1) || "that".equals(arg1));
        if(useExInfo){
            isSegmentPtr=exPtrNotAddr;
        }
        boolean isSegmentWithoutIdx=getArg2()==null;
        boolean isConstant="constant".equals(arg1);
        boolean isArithmeticAddOrSub="add".equals(operand) || "sub".equals(operand);
        boolean isArithmeticNeg="neg".equals(operand);
        boolean isLogicalAndOrOr="and".equals(operand) || "or".equals(operand);
        boolean isLogicalNot="not".equals(operand);
        boolean isComparison="eq".equals(operand) || "gt".equals(operand) || "lt".equals(operand);
        boolean isDefineFunc="function".equals(operand);
        boolean isCallFunc="call".equals(operand);
        boolean isReturn="return".equals(operand);
        boolean isGoto="goto".equals(operand);
        boolean isIfGoto="if-goto".equals(operand);
        boolean isLabel="label".equals(operand);
        if(isPush){
            if(isSegmentPtr){
                return C_PUSH_SEGMENT_PTR;
            }else if(isConstant){
                return C_PUSH_CONSTANT;
            }else if(isSegmentWithoutIdx){
                return C_PUSH_SEGMENT_ADDR_WITHOUT_IDX;
            }else{
                return C_PUSH_SEGMENT_ADDR;
            }
        }else if(isPop){
            if(isSegmentPtr){
                if(isSegmentWithoutIdx){
                    return C_POP_SEGMENT_PTR_WITHOUT_IDX;
                }
                return C_POP_SEGMENT_PTR;
            }else if(isSegmentWithoutIdx){
                return C_POP_SEGMENT_ADDR_WITHOUT_IDX;
            }else{
                return C_POP_SEGMENT_ADDR;
            }
        }else if(isArithmeticAddOrSub){
            return C_ARITHMETIC_ADD_OR_SUB;
        }else if(isArithmeticNeg){
            return C_ARITHMETIC_NEG;
        }else if(isLogicalAndOrOr){
            return C_LOGICAL_AND_OR_OR;
        }else if(isLogicalNot){
            return C_LOGICAL_NOT;
        }else if(isComparison){
            return C_COMPARISON;
        }else if(isDefineFunc){
            return C_DEFINE_FUNCTION;
        }else if(isCallFunc){
            return C_CALL_FUNCTION;
        }else if(isReturn){
            return C_RETURN;
        }else if(isGoto){
            return C_GOTO;
        }else if(isIfGoto){
            return C_IF_GOTO;
        }else if(isLabel){
            return C_LABEL;
        }else{
            return C_UNDEFINED;
        }
    }

    public String getArg1(){
        String[] fields=command.split(" ");
        if(fields.length==1){
            return fields[0];
        }else if(fields.length>1){
            return fields[1];
        }else{
            return null;
        }
    }

    public String getArg2(){
        String[] fields=command.split(" ");
        if(fields.length<=2){
            return null;
        }else{
            return fields[2];
        }
    }

}
