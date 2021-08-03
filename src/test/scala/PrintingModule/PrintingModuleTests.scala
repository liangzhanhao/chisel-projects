package circuits

import chisel3.iotesters._

class PrintingModuleTests(c: PrintingModule) extends PeekPokeTester(c) {
  poke(c.io.in, 3)
  step(5) // circuit will print
    
  println(s"Print during testing: Input is ${peek(c.io.in)}")
}
