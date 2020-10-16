/************************************************************
*
*       Filename        :       ALU.scala
*       Author          :       liangzhanhao
*       Revision        :       first version
*       Description     :       simple alu, data width configurable
*
*       io.a            :       input[dwidth-1:0], data, operand 1
*       io.b            :       input[dWidth-1:0], data, operand 2
*       io.opcode       :       input[dWidth-1:0], control, to select which operation result output
*       io.out          :       output[dWidth-1:0], data, operation result output
*
************************************************************/
package circuits

import chisel3._
import chisel3.util._

class ALU(val dwidth: Int) extends Module {
  val io = IO(new Bundle {
    val a      = Input(UInt(dwidth.W))
    val b      = Input(UInt(dwidth.W))
    val opcode = Input(UInt(2.W))
    val out    = Output(UInt(dwidth.W))
  })

  io.out := MuxCase(io.b,
             Array((io.opcode === 0.U) -> (io.a + io.b),
                   (io.opcode === 1.U) -> (io.a - io.b),
                   (io.opcode === 2.U) -> io.a))
}
