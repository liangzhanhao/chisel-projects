/************************************************************
*
*       Filename        :       Mux2.scala
*       Revision        :       first version
*       Description     :       wrap verilog module
*
*       io.sel          :       input, control, sel == 0, output in0 else output in1
*       io.in0          :       input[dWidth-1:0], data, input data 0
*       io.in1          :       input[dWidth-1:0], data, input data 1
*       io.out          :       output[dWidth-1:0], data, output data
*
************************************************************/
package circuits

import chisel3._
import chisel3.util._
import chisel3.experimental._

class Mux2(val dWidth: Int) extends BlackBox(Map("DWIDTH" -> dWidth))
  with HasBlackBoxResource {

  val io = IO(new Bundle {
    val sel = Input(Bool())
    val in0 = Input(UInt(dWidth.W))
    val in1 = Input(UInt(dWidth.W))
    val out = Output(UInt(dWidth.W))
  })

  setResource("/Mux2.v")
}

object Mux2 {
  def apply(s: Bool, in0: UInt, in1: UInt): UInt = {
    require(in0.getWidth == in1.getWidth)

    val m = Module(new Mux2(in0.getWidth))
    m.io.sel := s
    m.io.in0 := in0
    m.io.in1 := in1
    m.io.out
  }
}

object MuxVec {
  def apply(s: Vec[Bool], in0: Vec[UInt], in1: Vec[UInt]): Vec[UInt] = {
    require(in0.length == in1.length && in0.nonEmpty && s.nonEmpty)

    val out = Wire(Vec(in0.length, chiselTypeOf(in0(0))))
    out := (s, in0, in1).zipped.map((sel, inData0, inData1) => Mux2(sel, inData0, inData1))

    out
  }
}
