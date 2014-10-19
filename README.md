## Sudoku Solver

Purely functional Sudoku Solver written in Scala.

A puzzle may have multiple solutions, so this uses Scala's Stream (lazy list) so that each solution is only computed when required. In other words, even though `SudokuSolver.solve` returns a collection, if you only ask for one solution, only one will be computed.

### Example

```scala
val puzzle =
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

// Assumes a valid starting puzzle
SudokuSolver.solve(puzzle).foreach(println)
```

Output:
```
274196835
316875942
958342716
832614597
567938421
491527368
783461259
645289173
129753684
```
