import scala.annotation.tailrec
import scala.io.StdIn.readLine

class EightQueensController {
  def stateInit(): (Array[Array[Char]], List[(Int, Int)]) = {
    val board = Array(
      Array('-', '-', '-', '-', '-', '-', '-', '-'),
      Array('-', '-', '-', '-', '-', '-', '-', '-'),
      Array('-', '-', '-', '-', '-', '-', '-', '-'),
      Array('-', '-', '-', '-', '-', '-', '-', '-'),
      Array('-', '-', '-', '-', '-', '-', '-', '-'),
      Array('-', '-', '-', '-', '-', '-', '-', '-'),
      Array('-', '-', '-', '-', '-', '-', '-', '-'),
      Array('-', '-', '-', '-', '-', '-', '-', '-'),
    )
    val queens = List()
    (board, queens)
  }

  private def validateInputForm(rawInput: String): Boolean = {
    if (rawInput.matches("""^[a-h]+[1-8]+$""")) {
      return true
    }
    println("wrong input form")
    false
  }

  private def parseInput(input: String): (Int, Int) = {
    val fromRow = '8' - input.charAt(1)
    val fromCol = input.charAt(0) - 'a'
    (fromRow, fromCol)
  }

  private def validateMove(pos: (Int, Int), state: (Array[Array[Char]], List[(Int, Int)])): Boolean = {
    val board = state._1
    val queens = state._2
    val row = pos._1
    val col = pos._2
    if (row < 0 || row > 7 || col < 0 || col > 7) {
      println("out of bounds")
      return false
    }
    if (board(row)(col) == '-') {
      !conflict(pos, queens)
    }
    else {
      true
    }
  }

  private def threat(p1: (Int, Int), p2: (Int, Int)): Boolean = {
    (p1._1 + p1._2 == p2._1 + p2._2) || (p1._1 - p1._2 == p2._1 - p2._2) || (p1._1 == p2._1) || (p1._2 == p2._2)
  }

  @tailrec
  private def conflict(p: (Int, Int), sol: List[(Int, Int)]): Boolean = sol match {
    case Nil => false
    case q :: qs => threat(p, q) || conflict(p, qs)
  }

  def validateInput(input: String, state: (Array[Array[Char]], List[(Int, Int)])): Boolean = {
    if (validateInputForm(input)) {
      val position = parseInput(input)
      if (validateMove(position, state)) {
        return true
      } else {
        println("there may be a conflict between queens")
        return false
      }
    }
    false
  }

  def applyAction(input: String, state: (Array[Array[Char]], List[(Int, Int)])): (Array[Array[Char]], List[(Int, Int)]) = {
    val move = parseInput(input)
    val row = move._1
    val col = move._2
    val board = state._1
    val queens = state._2

    if (board(row)(col) == '-') {
      board(row)(col) = 'Q'
      val newList = move :: queens
      (board, newList)
    } else {
      board(row)(col) = '-'
      val newList = queens.filterNot(_ == move)
      (board, newList)
    }
  }

  private def printboard(state: (Array[Array[Char]], Any)): Unit = {
    val board = state._1
    for (i <- 0 to 7) {
      for (j <- 0 to 7) {
        printf("%c\t", board(i)(j))
      }
      printf("\n-----------------------------------------\n")
    }
    printf("a\tb\tc\td\te\tf\tg\th\n")
  }

  def main(args: Array[String]): Unit = {
    var state = stateInit()
    val board = Array(
      Array('-', '-', '-', '-', '-', '-', '-', '-'),
      Array('-', '-', '-', '-', '-', '-', '-', '-'),
      Array('-', '-', '-', '-', '-', '-', '-', '-'),
      Array('-', '-', '-', '-', '-', '-', '-', '-'),
      Array('-', '-', '-', '-', '-', '-', '-', '-'),
      Array('-', '-', 'Q', '-', '-', '-', '-', '-'),
      Array('-', '-', '-', '-', '-', '-', '-', '-'),
      Array('-', '-', '-', '-', '-', '-', '-', '-'),
    )
    val list = (5,2) :: state._2
    state = (board,list)
    printboard(state)
    val input = readLine()
    //    val x =validateInputForm(input)
    //    println(x)
    if (validateInput(input, state)) {
      state = applyAction(input, state)
      printboard(state)
    }
  }
}