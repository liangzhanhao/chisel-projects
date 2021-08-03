package circuits

import java.io._

object ProjectCreater {
  def main(args: Array[String]): Unit = {
    if(args.nonEmpty) {
      for(projectName <- args) {
        //TODO: check existence of projectName
        //TODO: add an item in mapping for projects
        val circuitFile = projectName ++ ".scala"
        val circuitPath = "src/main/scala/" ++ projectName ++ "/"
        (new File(circuitPath)).mkdir()
        val writer = new PrintWriter(new File(circuitPath ++ circuitFile))
        writer.println("/************************************************************")
        writer.println("*")
        writer.println("*       Filename        :       " + circuitFile)
        writer.println("*       Author          :       author")
        writer.println("*       Revision        :       first version")
        writer.println("*       Description     :       ")
        writer.println("*")
        writer.println("*       io.en           :       input, control, (description)")
        writer.println("*       io.din          :       input[width-1:0], data, (description)")
        writer.println("*       io.dout         :       output[width-1:0], data, (description)")
        writer.println("*")
        writer.println("************************************************************/")
        writer.println("package circuits")
        writer.println("")
        writer.println("import chisel3._")
        writer.println("import chisel3.util._")
        writer.println("")
        writer.println("class " + projectName + " extends Module {")
        writer.println("  val io = IO(new Bundle {")
        writer.println("")
        writer.println("  })")
        writer.println("")
        writer.println("}")
        writer.close()
        
        val circuitTestBench = projectName ++ "Tests.scala"
        val circuitTestPath = "src/test/scala/" ++ projectName ++ "/"
        (new File(circuitTestPath)).mkdir()
        val tester = new PrintWriter(new File(circuitTestPath ++ circuitTestBench))
        tester.println("package circuits")
        tester.println("")
        tester.println("import chisel3.iotesters._")
        tester.println("")
        tester.println("class " + projectName + "Tests(c: " + projectName + ") extends PeekPokeTester(c) {")
        tester.println("")
        tester.println("}")
        tester.close()
      }
    } else {
      System.exit(0)
    }
  }
}