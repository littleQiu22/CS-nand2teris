package nand2tetris.VMTranslator;

/*
 * @Author: 邱俊彦 (Junyan Qiu)
 * @Created date: 2022-10-20
 * @Lastest modified date: 2022-10-20
 * @Description: CodeWriter is a class which map a single VM command into Hack Assembler commands.  
 * @Usage: String assembler=CodeWriter.translate(String command);
 */
public class CodeWriter {
    private static Parser parser=null;

    public String translate(String command){
        if(parser==null){
            parser=new Parser();
        }
    }
}
