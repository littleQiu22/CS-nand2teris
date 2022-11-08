package nand2tetris.VMTranslatorPlus;

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
