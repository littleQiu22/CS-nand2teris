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
//add
@SP
M=M-1
A=M-1
D=M
A=A+1
D=D+M
A=A-1
M=D
//end loop
(AUTO_INFINITE_LOOP)
@AUTO_INFINITE_LOOP
0;JMP