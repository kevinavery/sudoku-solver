
object SudokuSolver {

  val ZeroToEight: Iterable[Int] = 0 to 8
  val OneToNine: Iterable[Int] = 1 to 9

  trait IntValue {
    def value: Int
  }
  case class GivenInt(value: Int) extends IntValue
  case class GuessInt(value: Int) extends IntValue
  case object Unknown extends IntValue {
    def value = throw new IllegalAccessError
  }

  case class Board(values: Vector[IntValue]) {
    private def toIndex(row: Int, col: Int) = row * 9 + col

    def setValue(row: Int, col: Int, v: IntValue) = copy(values.updated(toIndex(row, col), v))

    def value(row: Int, col: Int): IntValue = values(toIndex(row, col))

    def rowValues(row: Int) = values.slice(row * 9, row * 9 + 9)

    def colValues(col: Int) = ZeroToEight.map(row => values(toIndex(row, col))).toVector

    def globValues(row: Int, col: Int) = {
      def round(x: Int) = x - (x % 3)
      val r = round(row)
      val c = round(col)
      for {
        ri <- r to r + 2
        ci <- c to c + 2
      } yield value(ri, ci)
    }

    def nearestFullRow = nearestFull(rowValues)

    def nearestFull(indexer: Int => Iterable[IntValue]): Int = {
      ZeroToEight.map { i =>
        (i, countUnknowns(indexer(i)))
      }.filter(_._2 != 0).minBy(_._2)._1
    }

    def isSolved = 0 == countUnknowns(values)

    private def countUnknowns(values: Iterable[IntValue]) = values.count {
      case Unknown => true
      case _ => false
    }

    override def toString = values.zipWithIndex.map {
      case (v, i) =>
        val lineEnd = if ((i + 1) % 9 == 0) "\n" else ""
        s"${v.value}$lineEnd"
    }.mkString
  }

  def solveRec(board: Board): Stream[Board] = {
    if (board.isSolved) {
      Stream(board)
    } else {
      val row = board.nearestFullRow
      val col = board.rowValues(row).indexWhere(_ == Unknown)
      val existingValues = board.rowValues(row).toSet ++ board.colValues(col).toSet ++ board.globValues(row, col)
      val validGuesses = OneToNine.toSet -- existingValues.filter(_ != Unknown).map(_.value)
      validGuesses.toStream.flatMap { guess =>
        solveRec(board.setValue(row, col, GuessInt(guess)))
      }
    }
  }

  def solve(gameString: String) = solveRec(Board(parseGame(gameString).toVector))

  def parseGame(gameString: String) = gameString.toCharArray.filter(c => c == '-' || (c >= '1' && c <= '9')).map {
    case '-' => Unknown
    case c => GivenInt(c.toInt - '0')
  }
}
