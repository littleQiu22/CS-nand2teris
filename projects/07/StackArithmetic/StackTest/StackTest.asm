//push constant 17
@17
D=A
@SP
A=M
M=D
@SP
M=M+1
//push constant 17
@17
D=A
@SP
A=M
M=D
@SP
M=M+1
//eq
@SP
M=M-1
A=M
D=M
A=A-1
D=D-M
M=0
@AUTO_TRUE_LABEL_0
D;JEQ
@AUTO_END_LABEL_0
0;JMP
(AUTO_TRUE_LABEL_0)
@SP
A=M-1
M=!M
(AUTO_END_LABEL_0)
//push constant 17
@17
D=A
@SP
A=M
M=D
@SP
M=M+1
//push constant 16
@16
D=A
@SP
A=M
M=D
@SP
M=M+1
//eq
@SP
M=M-1
A=M
D=M
A=A-1
D=D-M
M=0
@AUTO_TRUE_LABEL_1
D;JEQ
@AUTO_END_LABEL_1
0;JMP
(AUTO_TRUE_LABEL_1)
@SP
A=M-1
M=!M
(AUTO_END_LABEL_1)
//push constant 16
@16
D=A
@SP
A=M
M=D
@SP
M=M+1
//push constant 17
@17
D=A
@SP
A=M
M=D
@SP
M=M+1
//eq
@SP
M=M-1
A=M
D=M
A=A-1
D=D-M
M=0
@AUTO_TRUE_LABEL_2
D;JEQ
@AUTO_END_LABEL_2
0;JMP
(AUTO_TRUE_LABEL_2)
@SP
A=M-1
M=!M
(AUTO_END_LABEL_2)
//push constant 892
@892
D=A
@SP
A=M
M=D
@SP
M=M+1
//push constant 891
@891
D=A
@SP
A=M
M=D
@SP
M=M+1
//lt
@SP
M=M-1
A=M
D=M
A=A-1
D=D-M
M=0
@AUTO_TRUE_LABEL_3
D;JGT
@AUTO_END_LABEL_3
0;JMP
(AUTO_TRUE_LABEL_3)
@SP
A=M-1
M=!M
(AUTO_END_LABEL_3)
//push constant 891
@891
D=A
@SP
A=M
M=D
@SP
M=M+1
//push constant 892
@892
D=A
@SP
A=M
M=D
@SP
M=M+1
//lt
@SP
M=M-1
A=M
D=M
A=A-1
D=D-M
M=0
@AUTO_TRUE_LABEL_4
D;JGT
@AUTO_END_LABEL_4
0;JMP
(AUTO_TRUE_LABEL_4)
@SP
A=M-1
M=!M
(AUTO_END_LABEL_4)
//push constant 891
@891
D=A
@SP
A=M
M=D
@SP
M=M+1
//push constant 891
@891
D=A
@SP
A=M
M=D
@SP
M=M+1
//lt
@SP
M=M-1
A=M
D=M
A=A-1
D=D-M
M=0
@AUTO_TRUE_LABEL_5
D;JGT
@AUTO_END_LABEL_5
0;JMP
(AUTO_TRUE_LABEL_5)
@SP
A=M-1
M=!M
(AUTO_END_LABEL_5)
//push constant 32767
@32767
D=A
@SP
A=M
M=D
@SP
M=M+1
//push constant 32766
@32766
D=A
@SP
A=M
M=D
@SP
M=M+1
//gt
@SP
M=M-1
A=M
D=M
A=A-1
D=D-M
M=0
@AUTO_TRUE_LABEL_6
D;JLT
@AUTO_END_LABEL_6
0;JMP
(AUTO_TRUE_LABEL_6)
@SP
A=M-1
M=!M
(AUTO_END_LABEL_6)
//push constant 32766
@32766
D=A
@SP
A=M
M=D
@SP
M=M+1
//push constant 32767
@32767
D=A
@SP
A=M
M=D
@SP
M=M+1
//gt
@SP
M=M-1
A=M
D=M
A=A-1
D=D-M
M=0
@AUTO_TRUE_LABEL_7
D;JLT
@AUTO_END_LABEL_7
0;JMP
(AUTO_TRUE_LABEL_7)
@SP
A=M-1
M=!M
(AUTO_END_LABEL_7)
//push constant 32766
@32766
D=A
@SP
A=M
M=D
@SP
M=M+1
//push constant 32766
@32766
D=A
@SP
A=M
M=D
@SP
M=M+1
//gt
@SP
M=M-1
A=M
D=M
A=A-1
D=D-M
M=0
@AUTO_TRUE_LABEL_8
D;JLT
@AUTO_END_LABEL_8
0;JMP
(AUTO_TRUE_LABEL_8)
@SP
A=M-1
M=!M
(AUTO_END_LABEL_8)
//push constant 57
@57
D=A
@SP
A=M
M=D
@SP
M=M+1
//push constant 31
@31
D=A
@SP
A=M
M=D
@SP
M=M+1
//push constant 53
@53
D=A
@SP
A=M
M=D
@SP
M=M+1
//add
@SP
M=M-1
A=M-1
D=M
A=A+1
D=D+M
A=A-1
M=D
//push constant 112
@112
D=A
@SP
A=M
M=D
@SP
M=M+1
//sub
@SP
M=M-1
A=M-1
D=M
A=A+1
D=D-M
A=A-1
M=D
//neg
@SP
A=M-1
M=!M
M=M+1
//and
@SP
M=M-1
A=M
D=M
A=A-1
M=D&M
//push constant 82
@82
D=A
@SP
A=M
M=D
@SP
M=M+1
//or
@SP
M=M-1
A=M
D=M
A=A-1
M=D|M
//not
@SP
A=M-1
M=!M
//end loop
(AUTO_INFINITE_LOOP)
@AUTO_INFINITE_LOOP
0;JMP