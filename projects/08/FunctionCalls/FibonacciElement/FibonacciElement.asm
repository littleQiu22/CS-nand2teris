// function Sys.init 0
(Sys.init)
@0
D=A
(Sys.init.initLoopIn)
@Sys.init.pushLocal
D;JGT
@Sys.init.initLoopOut
0;JMP
(Sys.init.pushLocal)
//push constant 0
@0
D=A
@SP
M=M+1
A=M-1
M=D
D=D-1
@Sys.init.initLoopIn
0;JMP
(Sys.init.initLoopOut)
//push constant 4
@4
D=A
@SP
M=M+1
A=M-1
M=D
// call Sys.init 1
@Sys.init$ret0
D=A
@SP
M=M+1
A=M-1
M=D
// push LCL
@LCL
D=M
@SP
M=M+1
A=M-1
M=D
// push ARG
@ARG
D=M
@SP
M=M+1
A=M-1
M=D
// push THIS
@THIS
D=M
@SP
M=M+1
A=M-1
M=D
// push THAT
@THAT
D=M
@SP
M=M+1
A=M-1
M=D
// push SP
@SP
D=M
@SP
M=M+1
A=M-1
M=D
//push constant 6
@6
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
//pop ARG
@SP
M=M-1
A=M
D=M
@ARG
M=D
@SP
D=M
@LCL
M=D
@Main.fibonacci
0;JMP
(Sys.init$ret0)
// label WHILE
(Sys.init$WHILE)
// goto WHILE
@Sys.init$WHILE
0;JMP
// function Main.fibonacci 0
(Main.fibonacci)
@0
D=A
(Main.fibonacci.initLoopIn)
@Main.fibonacci.pushLocal
D;JGT
@Main.fibonacci.initLoopOut
0;JMP
(Main.fibonacci.pushLocal)
//push constant 0
@0
D=A
@SP
M=M+1
A=M-1
M=D
D=D-1
@Main.fibonacci.initLoopIn
0;JMP
(Main.fibonacci.initLoopOut)
//push argument 0
@0
D=A
@ARG
A=D+M
D=M
@SP
M=M+1
A=M-1
M=D
//push constant 2
@2
D=A
@SP
M=M+1
A=M-1
M=D
//lt
@SP
M=M-1
A=M
D=M
A=A-1
D=D-M
M=0
@AUTO_TRUE_LABEL_0
D;JGT
@AUTO_END_LABEL_0
0;JMP
(AUTO_TRUE_LABEL_0)
@SP
A=M-1
M=-1
(AUTO_END_LABEL_0)
// if-goto IF_TRUE
@SP
M=M-1
A=M
D=M
@Main.fibonacci$IF_TRUE
D;JNE
// goto IF_FALSE
@Main.fibonacci$IF_FALSE
0;JMP
// label IF_TRUE
(Main.fibonacci$IF_TRUE)
//push argument 0
@0
D=A
@ARG
A=D+M
D=M
@SP
M=M+1
A=M-1
M=D
// return
//pop ARG
@SP
M=M-1
A=M
D=M
@ARG
A=M
M=D
@ARG
D=M+1
@SP
M=D
@LCL
D=M
@R13
M=D-1
A=M
D=M
@THAT
M=D
@R13
M=M-1
A=M
D=M
@THIS
M=D
@R13
M=M-1
A=M
D=M
@ARG
M=D
@R13
A=M-1
A=M
0;JMP
// label IF_FALSE
(Main.fibonacci$IF_FALSE)
//push argument 0
@0
D=A
@ARG
A=D+M
D=M
@SP
M=M+1
A=M-1
M=D
//push constant 2
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
// call Main.fibonacci 1
@Main.fibonacci$ret0
D=A
@SP
M=M+1
A=M-1
M=D
// push LCL
@LCL
D=M
@SP
M=M+1
A=M-1
M=D
// push ARG
@ARG
D=M
@SP
M=M+1
A=M-1
M=D
// push THIS
@THIS
D=M
@SP
M=M+1
A=M-1
M=D
// push THAT
@THAT
D=M
@SP
M=M+1
A=M-1
M=D
// push SP
@SP
D=M
@SP
M=M+1
A=M-1
M=D
//push constant 6
@6
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
//pop ARG
@SP
M=M-1
A=M
D=M
@ARG
M=D
@SP
D=M
@LCL
M=D
@Main.fibonacci
0;JMP
(Main.fibonacci$ret0)
//push argument 0
@0
D=A
@ARG
A=D+M
D=M
@SP
M=M+1
A=M-1
M=D
//push constant 1
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
// call Main.fibonacci 1
@Main.fibonacci$ret1
D=A
@SP
M=M+1
A=M-1
M=D
// push LCL
@LCL
D=M
@SP
M=M+1
A=M-1
M=D
// push ARG
@ARG
D=M
@SP
M=M+1
A=M-1
M=D
// push THIS
@THIS
D=M
@SP
M=M+1
A=M-1
M=D
// push THAT
@THAT
D=M
@SP
M=M+1
A=M-1
M=D
// push SP
@SP
D=M
@SP
M=M+1
A=M-1
M=D
//push constant 6
@6
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
//pop ARG
@SP
M=M-1
A=M
D=M
@ARG
M=D
@SP
D=M
@LCL
M=D
@Main.fibonacci
0;JMP
(Main.fibonacci$ret1)
//add
@SP
M=M-1
A=M-1
D=M
A=A+1
D=D+M
A=A-1
M=D
// return
//pop ARG
@SP
M=M-1
A=M
D=M
@ARG
A=M
M=D
@ARG
D=M+1
@SP
M=D
@LCL
D=M
@R13
M=D-1
A=M
D=M
@THAT
M=D
@R13
M=M-1
A=M
D=M
@THIS
M=D
@R13
M=M-1
A=M
D=M
@ARG
M=D
@R13
A=M-1
A=M
0;JMP
