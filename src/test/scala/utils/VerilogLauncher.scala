package circuits

import firrtl._
import chisel3.stage._

object VerilogLauncher {
  val circuits = Map(
    "MuxVectorModule" -> { (args: Array[String]) => (new chisel3.stage.ChiselStage).execute(args, Seq(ChiselGeneratorAnnotation(() => new MuxVectorModule))) },
    "ALU_dwidth4" -> { (args: Array[String]) => (new chisel3.stage.ChiselStage).execute(args, Seq(ChiselGeneratorAnnotation(() => new ALU(4)))) },
    "ALU_dwidth8" -> { (args: Array[String]) => (new chisel3.stage.ChiselStage).execute(args, Seq(ChiselGeneratorAnnotation(() => new ALU(8)))) },
    "GCD" -> { (args: Array[String]) => (new chisel3.stage.ChiselStage).execute(args, Seq(ChiselGeneratorAnnotation(() => new GCD))) },
    "ByteSelector" -> { (args: Array[String]) => (new chisel3.stage.ChiselStage).execute(args, Seq(ChiselGeneratorAnnotation(() => new ByteSelector))) },
    "PrintingModule" -> { (args: Array[String]) => (new chisel3.stage.ChiselStage).execute(args, Seq(ChiselGeneratorAnnotation(() => new PrintingModule))) }
  )

  def main(args: Array[String]): Unit = {
    if(args.nonEmpty) {
      for(circuitName <- args) {
        circuits.get(circuitName) match {
          case Some(v) => {
            val generateArgs = Array("-X", "verilog", "-td", s"test_run_dir/$circuitName")
            v(generateArgs)
          }
          case _ => println(s"Project name not found: $circuitName")
        }
      }
    } else {
      println("\nAvailable projects:")
      for(x <- circuits.keys) {
        println("  " + x)
      }
      System.exit(0)
    }
  }
}
