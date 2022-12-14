// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/08/FunctionCalls/StaticsTest/StaticsTest.tst

load StaticsTest.asm,
output-file StaticsTest.out,
compare-to StaticsTest.cmp,
output-list RAM[0]%D1.6.1 RAM[5]%D1.6.1 RAM[6]%D1.6.1;


repeat 2500 {
  ticktock;
}

output;
