//push constant 10
@10
D=A
@SP
A=M
M=D
@SP
M=M+1
//pop local 0
@0
D=A
@LCL
D=D+M
@R15
M=D
@SP
M=M-1
@SP
A=M
D=M
@R15
A=M
M=D
//push constant 21
@21
D=A
@SP
A=M
M=D
@SP
M=M+1
//push constant 22
@22
D=A
@SP
A=M
M=D
@SP
M=M+1
//pop argument 2
@2
D=A
@ARG
D=D+M
@R15
M=D
@SP
M=M-1
@SP
A=M
D=M
@R15
A=M
M=D
//pop argument 1
@1
D=A
@ARG
D=D+M
@R15
M=D
@SP
M=M-1
@SP
A=M
D=M
@R15
A=M
M=D
//push constant 36
@36
D=A
@SP
A=M
M=D
@SP
M=M+1
//pop this 6
@6
D=A
@THIS
D=D+M
@R15
M=D
@SP
M=M-1
@SP
A=M
D=M
@R15
A=M
M=D
//push constant 42
@42
D=A
@SP
A=M
M=D
@SP
M=M+1
//push constant 45
@45
D=A
@SP
A=M
M=D
@SP
M=M+1
//pop that 5
@5
D=A
@THAT
D=D+M
@R15
M=D
@SP
M=M-1
@SP
A=M
D=M
@R15
A=M
M=D
//pop that 2
@2
D=A
@THAT
D=D+M
@R15
M=D
@SP
M=M-1
@SP
A=M
D=M
@R15
A=M
M=D
//push constant 510
@510
D=A
@SP
A=M
M=D
@SP
M=M+1
//pop temp 6
@5
D=A
@6
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
//push local 0
@0
D=A
@LCL
A=D+M
D=M
@SP
A=M
M=D
@SP
M=M+1
//push that 5
@5
D=A
@THAT
A=D+M
D=M
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
//push argument 1
@1
D=A
@ARG
A=D+M
D=M
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
//sub
@R13
D=M
@R14
D=D-M
@SP
A=M
M=D
@SP
M=M+1
//push this 6
@6
D=A
@THIS
A=D+M
D=M
@SP
A=M
M=D
@SP
M=M+1
//push this 6
@6
D=A
@THIS
A=D+M
D=M
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
//sub
@R13
D=M
@R14
D=D-M
@SP
A=M
M=D
@SP
M=M+1
//push temp 6
@5
D=A
@6
A=D+A
D=M
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