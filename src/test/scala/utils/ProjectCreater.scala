package circuits

import java.io._

object ProjectCreater {
  def main(args: Array[String]): Unit = {
    if(args.nonEmpty) {
      for(projectName <- args) {
        //TODO: add an item in mapping for projects
        val circuitFile = projectName ++ ".scala"
        val circuitPath = "src/main/scala/" ++ projectName ++ "/"
        val circuitFullPathFile = new File(circuitPath ++ circuitFile)
        if(!circuitFullPathFile.exists()) {
          (new File(circuitPath)).mkdir()
          val writer = new PrintWriter(circuitFullPathFile)
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
        } else {
          println(circuitPath + circuitFile + " exist! Can not be created!")
        }
        
        val circuitTestBench = projectName ++ "Tests.scala"
        val circuitTestPath = "src/test/scala/" ++ projectName ++ "/"
        val circuitFullPathTestBench = new File(circuitTestPath ++ circuitTestBench)
        if(!circuitFullPathTestBench.exists()) {
          (new File(circuitTestPath)).mkdir()
          val tester = new PrintWriter(circuitFullPathTestBench)
          tester.println("package circuits")
          tester.println("")
          tester.println("import chisel3.iotesters._")
          tester.println("")
          tester.println("class " + projectName + "Tests(c: " + projectName + ") extends PeekPokeTester(c) {")
          tester.println("")
          tester.println("}")
          tester.close()
        } else {
          println(circuitTestPath + circuitFile + " exist! Can not be created!")
        }
      }
    } else {
      println("/*************************************************/")
      println("/                                                 /")
      println("/ Should input projects' name to create projects! /")
      println("/                                                 /")
      println("/*************************************************/")
      System.exit(0)
    }
  }
}
