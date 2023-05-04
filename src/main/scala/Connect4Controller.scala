import scala.io.StdIn.readLine

class Connect4Controller {
  def stateInit()={
    val board = Array(
      Array('-','-','-','-','-','-','-'),
      Array('-','-','-','-','-','-','-'),
      Array('-','-','-','-','-','-','-'),
      Array('-','-','-','-','-','-','-'),
      Array('-','-','-','-','-','-','-'),
      Array('-','-','-','-','-','-','-')
    )
    val playerTurn = true //red player==true yellow player==false
    (board,playerTurn)
  }

  private def isColumnFull(board: Array[Array[Char]], column: Int): Boolean = {
    // Check if the column is full
    val columnList = board.map(_(column)).toList
    columnList match {
      case Nil => false
      case head :: tail if head != '-' => true
      case _ => false
    }
  }
  def validateInput(input:String,state:(Array[Array[Char]],Boolean)):Boolean = {
    val board = state._1
    if(input.matches("""[1-7]+$""")){
      val colNumber = input.charAt(0) - '1'
      if(!isColumnFull(board,colNumber)){
        true
      }else{
        println("column is full")
        false
      }
    }else{
      println("out of bounds")
      false
    }
  }

  private def getAvailableRow(colNumber: Int, board: Array[Array[Char]]): Int = {
    for (r <- 5 to 0 by -1) {
      if (board(r)(colNumber) == '-') {
        return r
      }
    }
    -1
  }

  def applyAction(input: String, state: (Array[Array[Char]], Boolean)): (Array[Array[Char]], Boolean) = {
    val colNumber = input.charAt(0) - '1'
    var board = state._1
    val playerTurn = state._2 //red==true yellow==false
    val row = getAvailableRow(colNumber, board)
    if (playerTurn) { //red player
      board(row)(colNumber) = 'R'
    } else { //yellow player
      board(row)(colNumber) = 'Y'
    }
    (board, !playerTurn)
  }

  private def printboard(state: (Array[Array[Char]], Any)): Unit = {
    val board = state._1
    for (i <- 0 to 5) {
      for (j <- 0 to 6) {
        printf("%c\t", board(i)(j))
      }
      printf("\n-----------------------------------------\n")
    }
    printf("1\t2\t3\t4\t5\t6\t7\n")
  }

  def main(args: Array[String]): Unit = {
    var state = stateInit()
    val board = Array(
      Array('-', '-', 'R', '-', '-', '-', '-'),
      Array('-', '-', 'R', '-', '-', '-', '-'),
      Array('-', '-', 'R', '-', '-', '-', '-'),
      Array('-', '-', 'R', '-', '-', '-', '-'),
      Array('-', '-', 'R', '-', '-', '-', '-'),
      Array('-', '-', 'R', '-', '-', '-', '-')
    )
    state = (board,true)
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
