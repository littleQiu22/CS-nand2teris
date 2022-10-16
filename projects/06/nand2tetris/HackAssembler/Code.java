package nand2tetris.HackAssembler;

import java.util.HashMap;
import java.util.Map;

/*
 * @Author: 邱俊彦 (Junyan Qiu)
 * @Created date: 2022-10-16
 * @Lastest modified date: 2022-10-16
 * @Description: Code is a class which translate a field String into binary String  
 * @Usage: String binaryComp=Code.translateComp(<compField>); String binaryDest=Code.translateDest(<destField>); String binaryJump=Code.translateJump(<jumpField>):
 */
public class Code{
    private static Map<String,String> compMap=new HashMap<>();
    private static Map<String,String> destMap=new HashMap<>();
    private static Map<String,String> jumpMap=new HashMap<>();
    static{
        // mapping for comp
        compMap.put("0","0101010");
        compMap.put("1","0111111");
        compMap.put("-1","0111010" );
        compMap.put("D","0001100");
        compMap.put("A","0110000" );
        compMap.put("M","1110000");
        compMap.put("!D","0001101" );
        compMap.put("!A","0110001" );
        compMap.put("!M","1110001" );
        compMap.put("-D","0001111" );
        compMap.put("-A","0110011" );
        compMap.put("-M","1110011" );
        compMap.put("D+1","0011111" );
        compMap.put("A+1","0110111" );
        compMap.put("M+1","1110111" );
        compMap.put("D-1","0001110" );
        compMap.put("A-1","0110010" );
        compMap.put("M-1","1110010" );
        compMap.put("D+A","0000010" );
        compMap.put("D+M","1000010" );
        compMap.put("D-A","0010011" );
        compMap.put("D-M","1010011" );
        compMap.put("A-D","0000111" );
        compMap.put("M-D","1000111" );
        compMap.put("D&A","0000000" );
        compMap.put("D&M","1000000" );
        compMap.put("D|A","0010101" );
        compMap.put("D|M","1010101" );
        // mapping for dest
        destMap.put(null, "000");
        destMap.put("M", "001");
        destMap.put("D", "010");
        destMap.put("MD", "011");
        destMap.put("A", "100");
        destMap.put("AM", "101");
        destMap.put("AD", "110");
        destMap.put("AMD", "111");
        // mapping for jump
        jumpMap.put(null, "000");
        jumpMap.put("JGT", "001");
        jumpMap.put("JEQ", "010");
        jumpMap.put("JGE", "011");
        jumpMap.put("JLT", "100");
        jumpMap.put("JNE", "101");
        jumpMap.put("JLE", "110");
        jumpMap.put("JMP", "111");
    }

    public static String translateComp(String compField){
        return compMap.get(compField);
    }

    public static String translateDest(String destField){
        return destMap.get(destField);
    }

    public static String translateJump(String jumpField){
        return jumpMap.get(jumpField);
    }

    public static String translateDecimalNum(int num){
        return String.format("%16s",Integer.toBinaryString(num)).replace(" ", "0");
    }
}
