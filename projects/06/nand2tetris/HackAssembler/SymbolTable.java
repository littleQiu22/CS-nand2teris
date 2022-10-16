package nand2tetris.HackAssembler;

import java.util.HashMap;
import java.util.Map;

/*
 * @Author: 邱俊彦 (Junyan Qiu)
 * @Created date: 2022-10-16
 * @Lastest modified date: 2022-10-16
 * @Description: SymbolTable is a class which records symbol and associated number in source file written in assembly language 
 * @Usage: SymbolTable st=new SymbolTable(); Integer num=st.getNum(String symbol); st.setSymbol(String symbol); st.setSymbol(String symbol, int num)
 */
public class SymbolTable{
    private static Map<String,Integer> predefinedSymbolMap=new HashMap<>();
    static{
        predefinedSymbolMap.put("R0", 0);
        predefinedSymbolMap.put("R1", 1);
        predefinedSymbolMap.put("R2", 2);
        predefinedSymbolMap.put("R3", 3);
        predefinedSymbolMap.put("R4", 4);
        predefinedSymbolMap.put("R5", 5);
        predefinedSymbolMap.put("R6", 6);
        predefinedSymbolMap.put("R7", 7);
        predefinedSymbolMap.put("R8", 8);
        predefinedSymbolMap.put("R9", 9);
        predefinedSymbolMap.put("R10", 10);
        predefinedSymbolMap.put("R11", 11);
        predefinedSymbolMap.put("R12", 12);
        predefinedSymbolMap.put("R13", 13);
        predefinedSymbolMap.put("R14", 14);
        predefinedSymbolMap.put("R15", 15);
        predefinedSymbolMap.put("SP", 0);
        predefinedSymbolMap.put("LCL", 1);
        predefinedSymbolMap.put("ARG", 2);
        predefinedSymbolMap.put("THIS", 3);
        predefinedSymbolMap.put("THAT", 4);
        predefinedSymbolMap.put("SCREEN", 16384);
        predefinedSymbolMap.put("KBD", 24576);
    }
    private Map<String,Integer> dynamicSymbolMap=new HashMap<>();
    private int variableNum=16;

    public Integer getNum(String symbol){
        Integer preNum=predefinedSymbolMap.get(symbol);
        if(preNum==null){
            return dynamicSymbolMap.get(symbol);
        }
        return preNum;
        
    }

    public void setSymbol(String symbol){
        dynamicSymbolMap.put(symbol, variableNum);
        variableNum++;
    }

    public void setSymbol(String symbol, int num){
        dynamicSymbolMap.put(symbol, num);
    }
}