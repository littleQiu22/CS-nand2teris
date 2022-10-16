package nand2tetris.HackAssembler;

/*
 * @Author: 邱俊彦 (Junyan Qiu)
 * @Created date: 2022-10-16
 * @Lastest modified date: 2022-10-16
 * @Description: Parser is a class which partitions a C-instruction into multiple fields (i.e. dest, comp and jump fields)
 * @Usage: Parser parser=new Parser(<C-instruction>); String comp=parser.getComp(); String dest=parser.getDest(); String jump=parser.getJump():
 */
public class Parser{
    private String CInstruction;

    public Parser(){}

    public Parser(String CInstruction){
        this.CInstruction=CInstruction;
    }

    public void setCInstruction(String CInstruction){
        this.CInstruction=CInstruction;
    }

    public String getCInstruction(){
        return CInstruction;
    }

    // parse <comp> filed in C-instruction
    public String getComp(){
        // <comp> field is located after "=" and before ";"
        int equalIdx=CInstruction.indexOf("=");
        int semicolonIdx=CInstruction.indexOf(";");
        if(semicolonIdx<0){
            return CInstruction.substring(equalIdx+1);
        }
        return CInstruction.substring(equalIdx+1, semicolonIdx);
    }

    // parse <dest> field in C-instruction
    public String getDest(){
        // <dest> field is located before "="
        int equalIdx=CInstruction.indexOf("=");
        if(equalIdx<0){
            return null;
        }
        return CInstruction.substring(0,equalIdx);
    }

    // parse <jump> field in C-instruction
    public String getJump(){
        // <jump> field is located after ";"
        int semiconlonIdx=CInstruction.indexOf(";");
        if(semiconlonIdx<0){
            return null;
        }
        return CInstruction.substring(semiconlonIdx+1);
    }
}