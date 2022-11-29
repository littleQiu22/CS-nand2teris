package nand2tetris.SyntaxTranslator.JackTokenizer;

public enum Structure {
    CLASS("class"),
    CLASS_VAR_DEC("classVarDec"),
    SUBROUTINE_DEC("subroutineDec")
    ;

    private Structure(String structure){
        this.structure=structure;
    }

    public String getStructure(){
        return structure;
    }

    public String structure;
}
