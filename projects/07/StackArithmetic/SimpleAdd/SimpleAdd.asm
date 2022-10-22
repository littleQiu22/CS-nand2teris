//push constant 7
@7
D=A
@SP
A=M
M=D
@SP
M=M+1
//push constant 8
@8
D=A
@SP
A=M
M=D
@SP
M=M+1
//pop R14 0
@R14
D=A
@0
D=D+A
@R15
M=D
@SP
M=M-1
A=M
D=M
@R15
A=M
M=D
//pop R13 0
@R13
D=A
@0
D=D+A
@R15
M=D
@SP
M=M-1
A=M
D=M
@R15
A=M
M=D
//add
@R13
D=M
@R14
D=D+M
@SP
A=M
M=D
@SP
M=M+1
//end loop
(AUTO_END)
@AUTO_END
0;JMP