/************************************************************
*
*       Filename        :       ByteSelector.scala
*       Author          :       author
*       Revision        :       first version
*       Description     :       
*
*       io.en           :       input, control, (description)
*       io.din          :       input[width-1:0], data, (description)
*       io.dout         :       output[width-1:0], data, (description)
*
************************************************************/
package circuits

import chisel3._
import chisel3.util._

class ByteSelector extends Module {
  val io = IO(new Bundle {
    val in     = Input(UInt(32.W))
    val offset = Input(UInt(2.W))
    val out    = Output(UInt(8.W))
  })

  io.out := 0.U(8.W)

  when(io.offset === 0.U) {
    io.out := io.in(7,0)
  }.elsewhen(io.offset === 1.U) {
    io.out := io.in(15,8)
  }.elsewhen(io.offset === 2.U) {
    io.out := io.in(23,16)
  }.otherwise {
    io.out := io.in(31,24)
  }

}
