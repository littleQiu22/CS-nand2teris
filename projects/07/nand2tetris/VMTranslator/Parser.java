package nand2tetris.VMTranslator;

/*
 * @Author: 邱俊彦 (Junyan Qiu)
 * @Created date: 2022-10-20
 * @Lastest modified date: 2022-10-20
 * @Description: Parser is a class which split a VM command into multiple predefined fields. The parser will ignore comment and empty line.  
 * @Usage: Parser parser=new Parser(String command); int commandType=parser.getCommandType(); String arg1=parser.getArg1(); int arg2=parser.getArg2(); 
 */
public class Parser {
    public static final int C_END_LOOP=-1;

    public static final int C_ARITHMETIC=0;

    public static final int C_PUSH_SEGMENT_PTR=1;
    public static final int C_PUSH_SEGMENT_ADDR=2;
    public static final int C_PUSH_CONSTANT=3;

    public static final int C_POP_SEGMENT_PTR=4;
    public static final int C_POP_SEGMENT_ADDR=5;

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
        boolean segmentPtr="local".equals(arg1) || "argument".equals(arg1) || "this".equals(arg1) || "that".equals(arg1);
        if("add".equals(operand) || "sub".equals(operand)){
            return C_ARITHMETIC;
        }else if("push".equals(operand)){
            if(segmentPtr){
                return C_PUSH_SEGMENT_PTR;
            }else{
                if("constant".equals(arg1)){
                    return C_PUSH_CONSTANT;
                }else{
                    return C_PUSH_SEGMENT_ADDR;
                }
            }
        }else if("pop".equals(operand)){
            if(segmentPtr){
                return C_POP_SEGMENT_PTR;
            }else{
                return C_POP_SEGMENT_ADDR;
            }
        }else{
            return -1;
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
