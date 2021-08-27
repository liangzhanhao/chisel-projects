/************************************************************
*
*       Filename        :       FixedPriorityArbiter.scala
*       Author          :       liangzhanhao
*       Revision        :       first version
*       Description     :       fixed priority arbiter, LSB has highest priority
*
*       io.en           :       input, control, (description)
*       io.din          :       input[width-1:0], data, (description)
*       io.dout         :       output[width-1:0], data, (description)
*
************************************************************/
package circuits

import chisel3._
import chisel3.util._

class FixedPriorityArbiter(val reqWidth: Int = 8, val impl: String = "recursion") extends Module {
  val io = IO(new Bundle {
    val req   = Input(Vec(reqWidth, UInt(1.W)))
    val grant = Output(Vec(reqWidth, UInt(1.W)))
  })
  
  impl match {
    case "recursion" => {
      val preReq = Wire(Vec(reqWidth, UInt(1.W)))
      preReq(0)   := io.req(0)
      io.grant(0) := io.req(0)
      for(i <- 1 until reqWidth) {
        io.grant(i)  := io.req(i) & ~preReq(i-1)
        preReq(i)    := io.req(i) | preReq(i-1)
      }
    }
    case "feedback" => {
      val preReq = RegInit(0.U(reqWidth.W))
      val req = io.req.asUInt
      preReq := (req(reqWidth-2, 0) | preReq(reqWidth-2, 0)) << 1
      io.grant := (req & ~preReq).asBools
    }
    case "findFirstOne" => {
      io.grant := (io.req.asUInt & ~(io.req.asUInt-1.U)).asBools
    }
    case _ => io.grant := io.req
  }
}
