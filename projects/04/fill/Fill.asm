// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// Put your code here.


@8192
D=A
@n
M=D // n=8192
@i
M=0 // i=0
@val
M=0
@pointer
M=0

(LISTEN_KBD)
    @i
    M=0
    @KBD
    D=M
    @FILL
    D;JNE
    @val
    M=0
    @RENDER
    0;JMP
    (FILL)
        @val
        M=-1
    (RENDER)
        @i
        D=M
        @n
        D=D-M
        @LISTEN_KBD
        D;JGE
        @SCREEN
        D=A
        @i
        D=D+M
        @pointer
        M=D
        @val
        D=M
        @pointer
        A=M
        M=D
        @i
        M=M+1
        @RENDER
        0;JMP


