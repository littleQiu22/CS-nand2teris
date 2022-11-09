// push argument 1
@1
D=A
@ARG
A=D+M
D=M
@SP
M=M+1
A=M-1
M=D
// pop pointer 1
@3
D=A
@1
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
// push constant 0
@0
D=A
@SP
M=M+1
A=M-1
M=D
// pop that 0
@0
D=A
@THAT
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
// push constant 1
@1
D=A
@SP
M=M+1
A=M-1
M=D
// pop that 1
@1
D=A
@THAT
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
// push constant 2
@2
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
// label MAIN_LOOP_START
(FibonacciSeries.AUTO_ENTRY_FUNC$MAIN_LOOP_START)
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
// if-goto COMPUTE_ELEMENT
@SP
M=M-1
A=M
D=M
@FibonacciSeries.AUTO_ENTRY_FUNC$COMPUTE_ELEMENT
D;JNE
// goto END_PROGRAM
@FibonacciSeries.AUTO_ENTRY_FUNC$END_PROGRAM
0;JMP
// label COMPUTE_ELEMENT
(FibonacciSeries.AUTO_ENTRY_FUNC$COMPUTE_ELEMENT)
// push that 0
@0
D=A
@THAT
A=D+M
D=M
@SP
M=M+1
A=M-1
M=D
// push that 1
@1
D=A
@THAT
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
// pop that 2
@2
D=A
@THAT
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
// push pointer 1
@3
D=A
@1
A=D+A
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
//add
@SP
M=M-1
A=M-1
D=M
A=A+1
D=D+M
A=A-1
M=D
// pop pointer 1
@3
D=A
@1
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
// goto MAIN_LOOP_START
@FibonacciSeries.AUTO_ENTRY_FUNC$MAIN_LOOP_START
0;JMP
// label END_PROGRAM
(FibonacciSeries.AUTO_ENTRY_FUNC$END_PROGRAM)
//auto end loop
(AUTO_END_LOOP)
@AUTO_END_LOOP
0;JMP
