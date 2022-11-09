// push constant 0
@0
D=A
@SP
M=M+1
A=M-1
M=D
// pop local 0
@0
D=A
@LCL
D=D+M
@R15
M=D
@SP
M=M-1
A=M
D=M
@R15
A=M
M=D
// label LOOP_START
(BasicLoop.AUTO_ENTRY_FUNC$LOOP_START)
// push argument 0
@0
D=A
@ARG
A=D+M
D=M
@SP
M=M+1
A=M-1
M=D
// push local 0
@0
D=A
@LCL
A=D+M
D=M
@SP
M=M+1
A=M-1
M=D
//add
@SP
M=M-1
A=M-1
D=M
A=A+1
D=D+M
A=A-1
M=D
// pop local 0
@0
D=A
@LCL
D=D+M
@R15
M=D
@SP
M=M-1
A=M
D=M
@R15
A=M
M=D
// push argument 0
@0
D=A
@ARG
A=D+M
D=M
@SP
M=M+1
A=M-1
M=D
// push constant 1
@1
D=A
@SP
M=M+1
A=M-1
M=D
//sub
@SP
M=M-1
A=M-1
D=M
A=A+1
D=D-M
A=A-1
M=D
// pop argument 0
@0
D=A
@ARG
D=D+M
@R15
M=D
@SP
M=M-1
A=M
D=M
@R15
A=M
M=D
// push argument 0
@0
D=A
@ARG
A=D+M
D=M
@SP
M=M+1
A=M-1
M=D
// if-goto LOOP_START
@SP
M=M-1
A=M
D=M
@BasicLoop.AUTO_ENTRY_FUNC$LOOP_START
D;JNE
// push local 0
@0
D=A
@LCL
A=D+M
D=M
@SP
M=M+1
A=M-1
M=D
//auto end loop
(AUTO_END_LOOP)
@AUTO_END_LOOP
0;JMP
