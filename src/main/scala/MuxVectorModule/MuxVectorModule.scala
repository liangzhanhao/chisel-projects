/************************************************************
*
*       Filename        :       MuxVectorModule.scala
*       Revision        :       first version
*       Description     :       
*
*       io.sel          :       input, control, 
*       io.in0          :       input[dWidth-1:0], data, vector
*       io.in1          :       input[dWidth-1:0], data, vector
*       io.out          :       output[dWidth-1:0], data, vetor
*
************************************************************/
package circuits

import chisel3._
import chisel3.util._

class MuxVectorModule(val depth: Int = 8, val dwidth: Int = 16) extends Module {
  val io = IO(new Bundle {
    val sel = Input(Vec(depth, Bool()))
    val in0 = Input(Vec(depth, UInt(dwidth.W)))
    val in1 = Input(Vec(depth, UInt(dwidth.W)))
    val out = Output(Vec(depth, UInt(dwidth.W)))
  })

  io.out := MuxVec(io.sel, io.in0, io.in1)
}
