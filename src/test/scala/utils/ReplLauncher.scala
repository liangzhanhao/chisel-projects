package circuits

import chisel3.iotesters.{Driver, ReplOptionsManager}
import utils.ProjectRepl

object ReplLauncher {
  val circuits = Map(
    "MuxVectorModule" -> { (manager: ReplOptionsManager) => Driver.executeFirrtlRepl(() => new MuxVectorModule, manager) },
    "ALU_dwidth4" -> { (manager: ReplOptionsManager) => Driver.executeFirrtlRepl(() => new ALU(4), manager) },
    "ALU_dwidth8" -> { (manager: ReplOptionsManager) => Driver.executeFirrtlRepl(() => new ALU(8), manager) },
    "GCD" -> { (manager: ReplOptionsManager) => Driver.executeFirrtlRepl(() => new GCD, manager) }
  )

  def main(args: Array[String]): Unit = {
    ProjectRepl(circuits, args)
  }
}
