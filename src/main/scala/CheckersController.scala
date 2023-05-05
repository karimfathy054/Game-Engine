import scala.io.StdIn.readLine

class CheckersController extends Controller {
  def stateInit():(Array[Array[Char]],Boolean)={
    val board = Array(
      Array('-', 'B', '-', 'B', '-', 'B', '-', 'B'),
      Array('B', '-', 'B', '-', 'B', '-', 'B', '-'),
      Array('-', 'B', '-', 'B', '-', 'B', '-', 'B'),
      Array('-', '-', '-', '-', '-', '-', '-', '-'),
      Array('-', '-', '-', '-', '-', '-', '-', '-'),
      Array('R', '-', 'R', '-', 'R', '-', 'R', '-'),
      Array('-', 'R', '-', 'R', '-', 'R', '-', 'R'),
      Array('R', '-', 'R', '-', 'R', '-', 'R', '-'),
    )
    val playerTurn = true
    (board,playerTurn)
  }

  private def validateInputForm(rawInput: String): Boolean = {
    if (rawInput.matches("""^[a-h]+[1-8]+-\>[a-h]+[1-8]+$""")) {
      //return parse input
      return true
    }
    println("wrong input form")
    false
  }

  private def parseInput(input: String): (Int, Int, Int, Int) = {
    val fromRow = '8' - input.charAt(1)
    val fromCol = input.charAt(0) - 'a'
    val toRow = '8' - input.charAt(input.length - 1)
    val toCol = input.charAt(input.length - 2) - 'a'
    //return validate move
    (fromRow, fromCol, toRow, toCol)
  }

  private def redCheckerMove(move: (Int, Int, Int, Int), state: (Array[Array[Char]], Boolean)): Boolean = {
    val board = state._1
    val fromRow = move._1
    val fromCol = move._2
    val toRow = move._3
    val toCol = move._4
    var valid = false
    //move forward
    if (toRow - fromRow == -1 && Math.abs(fromCol - toCol) == 1 && board(toRow)(toCol) == '-') { //pawn takes
      valid = true
    }
    valid
  }

  private def redCheckerEat(move: (Int, Int, Int, Int), state: (Array[Array[Char]], Boolean)): Boolean = {
    val board = state._1
    val fromRow = move._1
    val fromCol = move._2
    val toRow = move._3
    val toCol = move._4
    var valid = false
    //move forward
    if (toRow - fromRow == -2 && Math.abs(fromCol - toCol) == 2 && board(toRow)(toCol) == '-' && board(fromRow-1)((fromCol+toCol)/2) == 'B') { //pawn takes
      valid = true
    }
    valid
  }
  private def blackCheckerMove(move: (Int, Int, Int, Int), state: (Array[Array[Char]], Boolean)): Boolean = {
    val board = state._1
    val fromRow = move._1
    val fromCol = move._2
    val toRow = move._3
    val toCol = move._4
    var valid = false
    //move forward
    if (toRow - fromRow == 1 && Math.abs(fromCol - toCol) == 1 && board(toRow)(toCol) == '-') { //pawn takes
      valid = true
    }
    valid
  }

  private def blackCheckerEat(move: (Int, Int, Int, Int), state: (Array[Array[Char]], Boolean)): Boolean = {
    val board = state._1
    val fromRow = move._1
    val fromCol = move._2
    val toRow = move._3
    val toCol = move._4
    var valid = false
    //move forward
    if (toRow - fromRow == 2 && Math.abs(fromCol - toCol) == 2 && board(toRow)(toCol) == '-' && board(fromRow + 1)((fromCol + toCol) / 2) == 'R') { //pawn takes
      valid = true
    }
    valid
  }

  private def checkRules(move: (Int, Int, Int, Int), state: (Array[Array[Char]], Boolean)): Boolean = {
    val playerTurn = state._2 //white==true black==false
    if (playerTurn) {
      redCheckerMove(move, state)||redCheckerEat(move, state)
    } else {
      blackCheckerMove(move, state)||blackCheckerEat(move, state)
    }
  }

  def validateInput(input: String, state: (Array[Array[Char]], Boolean)): Boolean = {
    if (validateInputForm(input)) {
      val move = parseInput(input)
      checkRules(move, state)
    } else {
      println("wrong input form")
      false
    }
  }
   def applyAction(input: String, state: (Array[Array[Char]], Boolean)): (Array[Array[Char]], Boolean) = {
    //move piece and print screen
    val move = parseInput(input)
    var board = state._1
    val playerTurn = state._2
    val fromRow = move._1
    val fromCol = move._2
    val toRow = move._3
    val toCol = move._4
    board(toRow)(toCol) = board(fromRow)(fromCol)
    board(fromRow)(fromCol) = '-'
    if(Math.abs(fromCol - toCol) == 2){
      board((toRow+fromRow)/2)((toCol+fromCol)/2)='-'
    }
    (board, !playerTurn)
  }
  private def printboard(state: (Array[Array[Char]], Boolean)): Unit = {
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
      Array('-', 'B', '-', 'B', '-', 'B', '-', 'B'),
      Array('B', '-', 'B', '-', 'B', '-', 'B', '-'),
      Array('-', '-', '-', 'B', '-', 'B', '-', 'B'),
      Array('-', '-', 'R', '-', '-', '-', '-', '-'),
      Array('-', 'B', '-', '-', '-', '-', '-', '-'),
      Array('R', '-', '-', '-', 'R', '-', 'R', '-'),
      Array('-', 'R', '-', 'R', '-', 'R', '-', 'R'),
      Array('R', '-', 'R', '-', 'R', '-', 'R', '-'),
    )
    state= (board,true)
    printboard(state)
    val input = readLine()

    if (validateInput(input, state)) {
      state = applyAction(input, state)
      printboard(state)
    }
  }

  override def EightQStateInit(): (Array[Array[Char]], List[(Int, Int)]) = ???

  override def EightQvalidateInput(input: String, state: (Array[Array[Char]], List[(Int, Int)])): Boolean = ???

  override def EightQapplyAction(input: String, state: (Array[Array[Char]], List[(Int, Int)])): (Array[Array[Char]], List[(Int, Int)]) = ???

  override def SudokuStateInit(): Array[Array[Char]] = ???

  override def SudokuvalidateInput(input: String, state: Array[Array[Char]]): Boolean = ???

  override def SudokuapplyAction(input: String, state: Array[Array[Char]]): Array[Array[Char]] = ???
}
