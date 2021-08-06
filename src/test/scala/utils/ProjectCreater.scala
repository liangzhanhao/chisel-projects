package circuits

import java.io._
import scala.io.Source
import scala.util.matching.Regex
import scala.collection.mutable._

object ProjectCreater {
  def main(args: Array[String]): Unit = {
    if(args.nonEmpty) {
      for(projectName <- args) {
        //create circuit files
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
        
        //create circuit testbenches
        val circuitTestBench = projectName ++ "Tests.scala"
        val circuitTestPath  = "src/test/scala/" ++ projectName ++ "/"
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
        
        //create mapping for running projects
        val verilogLaunchFile  = "src/test/scala/utils/VerilogLauncher.scala"
        val testLaunchFile     = "src/test/scala/utils/TestLauncher.scala"
        val replLaunchFile     = "src/test/scala/utils/ReplLauncher.scala"
        val launchFilesArray   = Array(verilogLaunchFile, testLaunchFile, replLaunchFile)
        val verilogMapping     = "    " + '"' + projectName + '"' + " -> { (args: Array[String]) => (new chisel3.stage.ChiselStage).execute(args, Seq(ChiselGeneratorAnnotation(() => new " + projectName + "))) }"
        val testMapping        = "    " + '"' + projectName + '"' + " -> { (manager: TesterOptionsManager) => Driver.execute(() => new " + projectName + ", manager){(c) => new " + projectName + "Tests(c)} }"
        val replMapping        = "    " + '"' + projectName + '"' + " -> { (manager: ReplOptionsManager) => Driver.executeFirrtlRepl(() => new " + projectName + ", manager) }"
        val mappingArray       = Array(verilogMapping, testMapping, replMapping)
        val lastMappingPattern = new Regex("->.*}$")
        for(i <- (launchFilesArray zip mappingArray)) {
          val launcherreader = Source.fromFile(i._1, "UTF-8")
          val lineIterator   = launcherreader.getLines
          val launcherBuffer: ArrayBuffer[String] = ArrayBuffer()
          for(L <- lineIterator) {
            val hasPattern = lastMappingPattern findFirstIn L
            hasPattern match {
              case Some(p) => launcherBuffer += (L + ","); launcherBuffer += (i._2)
              case None => launcherBuffer += L
            }
          }
          launcherreader.close()
          val launcherwriter = new PrintWriter(new File(i._1))
          launcherBuffer.map(launcherwriter.println(_))
          launcherwriter.close()
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
