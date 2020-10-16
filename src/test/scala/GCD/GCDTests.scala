package circuits

import chisel3.iotesters._

class GCDTests(c: GCD) extends PeekPokeTester(c) {
  def computeGcd(a: Int, b: Int): (Int, Int) = {
    var x = a
    var y = b
    var depth = 1
    while(y > 0 ) {
      if (x > y) {
        x -= y
      }
      else {
        y -= x
      }
      depth += 1
    }
    (x, depth)
  }

  for(i <- 1 to 40 by 3) {
    for (j <- 1 to 40 by 7) {
      poke(c.io.value1, i)
      poke(c.io.value2, j)
      poke(c.io.inputValid, 1)
      step(1)
      poke(c.io.inputValid, 0)

      val (expected_gcd, steps) = computeGcd(i, j)

      step(steps - 1) // -1 is because we step(1) already to toggle the enable
      expect(c.io.outputGCD, expected_gcd)
      expect(c.io.outputValid, 1)
    }
  }
}
