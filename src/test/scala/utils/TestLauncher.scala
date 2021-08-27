package circuits

import chisel3.iotesters.{Driver, TesterOptionsManager}
import utils.ProjectRunner

object TestLauncher {
  val circuits = Map(
    //use default parameters
    "MuxVectorModule" -> { (manager: TesterOptionsManager) => Driver.execute(() => new MuxVectorModule, manager){(c) => new MuxVectorModuleTests(c)} },
    //circuit ALU with dwidth=4
    "ALU_dwidth4" -> { (manager: TesterOptionsManager) => Driver.execute(() => new ALU(4), manager){(c) => new ALUTests(c)} },
    //circuit ALU with dwidth=8
    "ALU_dwidth8" -> { (manager: TesterOptionsManager) => Driver.execute(() => new ALU(8), manager){(c) => new ALUTests(c)} },
    "GCD" -> { (manager: TesterOptionsManager) => Driver.execute(() => new GCD, manager){(c) => new GCDTests(c)} },
    "ByteSelector" -> { (manager: TesterOptionsManager) => Driver.execute(() => new ByteSelector, manager){(c) => new ByteSelectorTests(c)} },
    "PrintingModule" -> { (manager: TesterOptionsManager) => Driver.execute(() => new PrintingModule, manager){(c) => new PrintingModuleTests(c)} },
    "FixedPriorityArbiter" -> { (manager: TesterOptionsManager) => Driver.execute(() => new FixedPriorityArbiter(reqWidth=8, impl="recursion"), manager){(c) => new FixedPriorityArbiterTests(c)} }
  )

  def main(args: Array[String]): Unit = {
    ProjectRunner(circuits, args)
  }
}
