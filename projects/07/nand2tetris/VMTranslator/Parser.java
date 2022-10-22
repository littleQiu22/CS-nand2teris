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
    public static final int C_PUSH_SEGMENT=1;
    public static final int C_PUSH_CONSTANT=2;
    public static final int C_POP=3;

    private String command=null;

    public Parser(){}

    public Parser(String command){
        this.command=command;    
    }

    public void setCommand(String command){
        this.command=command;
    }

    public int getCommandType(){
        String operand=command.split(" ")[0];
        if("add".equals(operand) || "sub".equals(operand)){
            return C_ARITHMETIC;
        }else if("push".equals(operand)){
            if("constant".equals(getArg1())){
                return C_PUSH_CONSTANT;
            }else{
                return C_PUSH_SEGMENT;
            }
        }else if("pop".equals(operand)){
            return C_POP;
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

    public int getArg2(){
        String[] fields=command.split(" ");
        if(fields.length<=2){
            return -1;
        }else{
            return Integer.valueOf(fields[2]);
        }
    }

}
