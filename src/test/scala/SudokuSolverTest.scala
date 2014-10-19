
import org.scalatest.WordSpec

class SudokuSolverTest extends WordSpec {

  val board1 =
    """
      |248395716
      |571628349
      |936741582
      |682539174
      |359174628
      |714862953
      |863417295
      |195286437
      |427953861
    """.stripMargin

  val board2 =
    """
      |---39571-
      |---------
      |936741582
      |6825-9174
      |359174628
      |--4862953
      |863--7295
      |19528643-
      |427953---
    """.stripMargin

  val board3 =
    """
      |---1----5
      |----75--2
      |9-8---7--
      |-326-----
      |--7---4--
      |-----736-
      |--3---2-9
      |6--28----
      |1----3---
    """.stripMargin

  "SudokuSolver" must {
    "solve" in {
      println(board3)

      val solutions = SudokuSolver.solve(board3)
      solutions.foreach(println)
    }
  }
}
