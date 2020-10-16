/************************************************************
*
*       Filename        :       GCD.scala
*       Author          :       liangzhanhao
*       Revision        :       first version
*       Description     :       from official chisel-template example
*                               Compute GCD using subtraction method
*                               Subtracts the smaller from the larger until register y is zero
*                               value in register x is then the GCD
*
*       io.value1       :       input[dwidth-1:0], data, operand 1
*       io.value2       :       input[dwidth-1:0], data, operand 2
*       io.inputValid   :       input, control, input valid
*       io.outputGCD    :       output[dwidth-1:0], data, GCD value
*       io.outputValid  :       output, control, output valid
*
************************************************************/
package circuits

import chisel3._
import chisel3.util._

class GCD(val dwidth: Int = 16) extends Module {
  val io = IO(new Bundle {
    val value1      = Input(UInt(dwidth.W))
    val value2      = Input(UInt(dwidth.W))
    val inputValid  = Input(Bool())
    val outputGCD   = Output(UInt(dwidth.W))
    val outputValid = Output(Bool())
  })

  //TODO replace posedge async reset signals with negedge ones
  val x = withReset(reset.asAsyncReset) { RegInit(0.U(dwidth.W)) }
  val y = withReset(reset.asAsyncReset) { RegInit(0.U(dwidth.W)) }

  //load values
  x := Mux(io.inputValid, io.value1, Mux(x > y, x - y, x))
  y := Mux(io.inputValid, io.value2, Mux(x > y, y, y - x))

  io.outputGCD := x
  io.outputValid := y === 0.U
}
