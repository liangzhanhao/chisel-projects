package circuits

import chisel3.iotesters._

class ALUTests(c: ALU) extends PeekPokeTester(c) {
  for(opcode <- 0 until 4; a <- 0 until 1 << c.dwidth; b <- 0 until 1 << c.dwidth) {
    val out = opcode match {
      case 0 => (a + b) & ((1 << c.dwidth) - 1)
      case 1 => (a - b) & ((1 << c.dwidth) - 1)
      case 2 => a
      case 3 => b
    }
    poke(c.io.a, a)
    poke(c.io.b, b)
    poke(c.io.opcode, opcode)
    step(1)
    expect(c.io.out, out)
  }
}