package nand2tetris.VMTranslator;

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
    

    private String command;

    public Parser(){}

    public Parser(String command){
        this.command=command;    
    }

    public void setCommand(String command){
        this.command=command;
    }

    public int getCommandType(){
        String operand=command.split(" ")[0];
        String arg1=getArg1();
        boolean isPush="push".equals(operand);
        boolean isPop="pop".equals(operand);
        boolean isSegmentPtr="local".equals(arg1) || "argument".equals(arg1) || "this".equals(arg1) || "that".equals(arg1);
        boolean isConstant="constant".equals(arg1);
        boolean isArithmeticAddOrSub="add".equals(operand) || "sub".equals(operand);
        boolean isArithmeticNeg="neg".equals(operand);
        boolean isLogicalAndOrOr="and".equals(operand) || "or".equals(operand);
        boolean isLogicalNot="not".equals(operand);
        boolean isComparison="eq".equals(operand) || "gt".equals(operand) || "lt".equals(operand);
        if(isPush){
            if(isSegmentPtr){
                return C_PUSH_SEGMENT_PTR;
            }else if(isConstant){
                return C_PUSH_CONSTANT;
            }else{
                return C_PUSH_SEGMENT_ADDR;
            }
        }else if(isPop){
            if(isSegmentPtr){
                return C_POP_SEGMENT_PTR;
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
