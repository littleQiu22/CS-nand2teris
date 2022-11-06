package nand2tetris.VMTranslator;

public class IntWrapper {
    private int value;
    public int getValue(){
        return value;
    }

    public void setValue(int value){
        this.value=value;
    }

    public void incValue(){
        value++;
    }
}
