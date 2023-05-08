import scala.io.StdIn.readLine

class TicTacToeController extends Controller {
  def stateInit(): (Array[Array[Char]], Boolean) ={
    val board = Array(
      Array('-','-','-'),
      Array('-','-','-'),
      Array('-','-','-')
    )
    val playerTurn = true
    (board,playerTurn)
  }
  private def validateInputForm(input:String): Boolean ={
    if(input.matches("""[1-3]+\,+[1-3]""")){
      return true
    }
    println("wrong input form")
    false
  }
  private def parseInput(input:String)={
    val row = input.charAt(0) - '1'
    val col = input.charAt(input.length-1) - '1'
    (row,col)
  }
  def validateInput(input:String , state:(Array[Array[Char]],Boolean)): Boolean ={
    val board = state._1
    if(validateInputForm(input)){
      val position = parseInput(input)
      if(board(position._1)(position._2)=='-'){
        return true
      }else{
        println("that position has been took")
        return false
      }
    }
    false
  }

  def applyAction(input: String, state: (Array[Array[Char]], Boolean)): (Array[Array[Char]], Boolean) = {
    val (row, col) = parseInput(input)
    val board = state._1
    val playerTurn = state._2 //player x = true player O = false
    if (playerTurn) {
      board(row)(col) = 'X'
    } else {
      board(row)(col) = 'O'
    }
    (board, !playerTurn)
  }

  private def printboard(state: (Array[Array[Char]], Boolean)): Unit = {
    val board = state._1
    for (i <- 0 to 2) {
      for (j <- 0 to 2) {
        printf("%c\t", board(i)(j))
      }
      printf("\n-----------------------------------------\n")
    }
    printf("a\tb\tc\td\te\tf\tg\th\n")
  }

  def main(args: Array[String]): Unit = {
    val board = Array(
      Array('-', 'X', '-'),
      Array('-', '-', '-'),
      Array('-', '-', '-')
    )
    val playerTurn = true
    var state = (board, playerTurn)
    printboard(state)
    val input = readLine()
    if(validateInput(input, state)){
      state = applyAction(input, state)
      printboard(state)
    }
  }

  override def EightQStateInit(): (Array[Array[Char]], List[(Int, Int)]) = ???

  override def EightQvalidateInput(input: String, state: (Array[Array[Char]], List[(Int, Int)])): Boolean = ???

  override def EightQapplyAction(input: String, state: (Array[Array[Char]], List[(Int, Int)])): (Array[Array[Char]], List[(Int, Int)]) = ???

  override def SudokuStateInit(): Array[Array[(Char, Boolean)]] = ???

  override def SudokuvalidateInput(input: String, state: Array[Array[(Char, Boolean)]]): Boolean = ???

  override def SudokuapplyAction(input: String, state: Array[Array[(Char, Boolean)]]): Array[Array[(Char, Boolean)]] = ???
}
