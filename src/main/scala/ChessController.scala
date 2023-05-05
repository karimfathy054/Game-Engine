
import scala.annotation.tailrec
import scala.io.StdIn.readLine

class ChessController extends Controller {
  def stateInit():(Array[Array[Char]],Boolean)={
    val board = Array(
      Array('R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R'),
      Array('P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'),
      Array('-', '-', '-', '-', '-', '-', '-', '-'),
      Array('-', '-', '-', '-', '-', '-', '-', '-'),
      Array('-', '-', '-', '-', '-', '-', '-', '-'),
      Array('-', '-', '-', '-', '-', '-', '-', '-'),
      Array('p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'),
      Array('r', 'n', 'b', 'q', 'k', 'b', 'n', 'r'),
    )
    val playerTurn = true
    (board,playerTurn)
  }
  private def validateInputForm(rawInput:String): Boolean ={
    if(rawInput.matches("""^[a-h]+[1-8]+-\>[a-h]+[1-8]+$""")){
      return true
    }
    println("wrong input form")
    false
  }
  private def parseInput(input:String): (Int, Int, Int, Int) ={
    val fromRow = '8' - input.charAt(1)
    val fromCol = input.charAt(0)- 'a'
    val toRow = '8' - input.charAt(input.length-1)
    val toCol = input.charAt(input.length-2) - 'a'
    (fromRow,fromCol,toRow,toCol)
  }

  private def validateMove(move:(Int,Int,Int,Int), state:(Array[Array[Char]],Boolean)): Boolean ={
    val board = state._1
    val playerTurn = state._2
    val fromRow = move._1
    val fromCol = move._2
    val toRow = move._3
    val toCol = move._4

    if (fromRow < 0 || fromRow > 7 || fromCol < 0 || fromCol > 7 || toRow < 0 || toRow > 7 || toCol < 0 || toCol > 7) {
      println("out of bounds")
      return false
    }
    if(board(fromRow)(fromCol) == '-'){
      println("Clicked on empty space.")
      return false
    }

    if(playerTurn){
      if(board(fromRow)(fromCol).isUpper){
        println("Player 1 Tried to move Player 2's piece")
        return false
      }
      if(board(toRow)(toCol).isLower){
        println("Player 1 released on his own pieces")
        return false
      }
    }
    else{
      if(board(fromRow)(fromCol).isLower){
        println("Player 2 Tried to move Player 1's piece")
        return false
      }
      if(board(toRow)(toCol).isUpper){
        println("Player 2 released on his own pieces")
        return false
      }
    }
    //return checkRules
    checkRules(move,state)
  }
  private def whitePawnMove(move:(Int,Int,Int,Int), state:(Array[Array[Char]],Boolean)): Boolean ={
    val board = state._1
    val fromRow = move._1
    val fromCol = move._2
    val toRow = move._3
    val toCol = move._4
    var valid = false
    //move forward
    if(toCol==fromCol && toRow-fromRow == -1 && board(toRow)(toCol)=='-'){//one cell forward
      valid = true
    }else if(fromRow == 6 && toRow == 4 && toCol ==fromCol && board(5)(toCol)=='-'){//two cells at the start
      valid=true
    }else if(toRow-fromRow == -1 && Math.abs(fromCol-toCol)==1 && board(toRow)(toCol)!='-'){//pawn takes
      valid = true
    }
    if(toRow==0 && valid){
      //return promotion
    }
    valid
  }
  private def blackPawnMove(move:(Int,Int,Int,Int), state:(Array[Array[Char]],Boolean)): Boolean ={
    val board = state._1
    val fromRow = move._1
    val fromCol = move._2
    val toRow = move._3
    val toCol = move._4
    var valid = false
    //move forward
    if(toCol==fromCol && toRow-fromRow==1 && board(toRow)(toCol)=='-'){//one cell forward
      valid = true
    }else if(fromRow == 1 && toRow == 3 && toCol ==fromCol && board(2)(toCol)=='-'){//two cells at the start
      valid=true
    }else if(toRow-fromRow==1 && Math.abs(fromCol-toCol)==1 && board(toRow)(toCol)!='-'){//pawn takes
      valid = true
    }
    if(toRow==7 && valid){
      //return promotion
    }
    valid
  }
  private def checkPawn(move:(Int,Int,Int,Int), state:(Array[Array[Char]],Boolean)): Boolean ={
    val playerTurn = state._2 //white==true black==false
    if(playerTurn){
      whitePawnMove(move,state)
    }else{
      blackPawnMove(move,state)
    }
  }
  private def checkRook(move:(Int,Int,Int,Int), state:(Array[Array[Char]],Boolean)): Boolean ={
    val board = state._1
    //white==true black==false
    val fromRow = move._1
    val fromCol = move._2
    val toRow = move._3
    val toCol = move._4

    val isHorizontalMove = fromRow == toRow
    val isVerticalMove = fromCol == toCol
    if(!isVerticalMove && !isHorizontalMove){
      return false
    }
    @tailrec
    def clearPath(row: Int, col: Int, board: Array[Array[Char]]): Boolean = {
      if (row == toRow && col == toCol) {
        return true
      }
      if (row < 0 || row >= board.length || col < 0 || col >= board(0).length) {
        println("1")
        return false
      }
      if (board(row)(col) != '-' && row != fromRow && col != fromCol) {
        println("2")
        return false
      }
      (toRow - row, toCol - col) match {
        case x if (x._1 > 0 && x._2 == 0) => clearPath(row + 1, col, board)
        case x if (x._1 < 0 && x._2 == 0) => clearPath(row - 1, col, board)
        case x if (x._1 == 0 && x._2 > 0) => clearPath(row, col + 1, board)
        case x if (x._1 == 0 && x._2 < 0) => clearPath(row, col - 1, board)
      }
    }
    clearPath(fromRow,fromCol,board)
  }

  private def checkKnight(move:(Int,Int,Int,Int), state:(Array[Array[Char]],Boolean)): Boolean ={
    val board = state._1
    val playerTurn = state //white==true black==false
    val fromRow = move._1
    val fromCol = move._2
    val toRow = move._3
    val toCol = move._4
    if((Math.abs(toRow-fromRow)==2 && Math.abs(toCol-fromCol)==1)||(Math.abs(toRow-fromRow)==1 && Math.abs(toCol-fromCol)==2)){
      return true
    }
    false
  }
  private def checkBishop(move:(Int,Int,Int,Int), state:(Array[Array[Char]],Boolean)): Boolean ={
    val board = state._1
    val playerTurn = state //white==true black==false
    val fromRow = move._1
    val fromCol = move._2
    val toRow = move._3
    val toCol = move._4
    val isDiagonalMove = Math.abs(fromCol-toCol) == Math.abs(fromRow-toRow)
    if(!isDiagonalMove){
      return false
    }
    @tailrec
    def clearPath(row: Int, col: Int, board: Array[Array[Char]]): Boolean = {
      if (row == toRow && col == toCol) {
        return true
      }
      if (row < 0 || row >= 8 || col < 0 || col >= 8) {
        return false
      }
      if (board(row)(col) != '-' && row != fromRow && col != fromCol) {
        return false
      }
      (toRow-row,toCol-col) match {
        case x if (x._1<0 && x._2<0) => clearPath(row - 1, col - 1, board)
        case x if (x._1<0 && x._2>0 ) => clearPath(row - 1, col + 1, board)
        case x if (x._1>0 && x._2<0) => clearPath(row + 1, col - 1, board)
        case x if (x._1>0 && x._2>0) => clearPath(row + 1, col + 1, board)
      }
    }
    clearPath(fromRow,fromCol,board)
  }

  private def checkQueen(move:(Int,Int,Int,Int), state:(Array[Array[Char]],Boolean)): Boolean ={
    val board = state._1
    val playerTurn = state //white==true black==false
    val fromRow = move._1
    val fromCol = move._2
    val toRow = move._3
    val toCol = move._4
    return checkBishop(move,state)||checkRook(move,state)
  }
  private def checkKing(move:(Int,Int,Int,Int), state:(Array[Array[Char]],Boolean)): Boolean ={
    val board = state._1
    val playerTurn = state //white==true black==false
    val fromRow = move._1
    val fromCol = move._2
    val toRow = move._3
    val toCol = move._4
    var valid = false
    if (((toRow-fromRow) >= -1 &&(toRow-fromRow)<=1)&&((toCol-fromCol) >= -1 && (toCol-fromCol)<=1)){
      valid = true
      return true
    }
    //check if the king is getting checked in that place !!
    false
  }
  private def checkRules(move:(Int,Int,Int,Int), state:(Array[Array[Char]],Boolean))={
    val board= state._1
    val playerTurn =state._2
    val fromRow = move._1
    val fromCol = move._2
    val toRow = move._3
    val toCol = move._4

    board(fromRow)(fromCol) match{
      case 'p'|'P' => checkPawn(move,state)
      case 'r'|'R' => checkRook(move,state)
      case 'n'|'N' => checkKnight(move,state)
      case 'b'|'B' => checkBishop(move,state)
      case 'q'|'Q' => checkQueen(move,state)
      case 'k'|'K' => checkKing(move,state)
    }

  }

  def validateInput(input:String,state:(Array[Array[Char]],Boolean)): Boolean ={
    if(validateInputForm(input)){
      val move = parseInput(input)
      return validateMove(move, state)
    }else{
      println("wrong input form")
      return false
    }
  }
  def applyAction(input:String,state:(Array[Array[Char]],Boolean)):(Array[Array[Char]],Boolean)={
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
    (board,!playerTurn)
  }
  private def printboard(state:(Array[Array[Char]],Boolean))={
    val board = state._1
    for (i<-0 to 7){
      for(j <-0 to 7){
        printf("%c\t",board(i)(j))
      }
      printf("\n-----------------------------------------\n")
    }
    printf("a\tb\tc\td\te\tf\tg\th\n")
  }

  def main(args: Array[String]): Unit = {
    var state = stateInit();
    val board = Array(
      Array('R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R'),
      Array('P', 'P', 'P', '-', 'P', 'P', 'P', 'P'),
      Array('-', '-', '-', '-', '-', '-', '-', '-'),
      Array('-', '-', '-', '-', '-', '-', '-', '-'),
      Array('-', '-', '-', '-', '-', '-', '-', '-'),
      Array('-', '-', '-', 'P', '-', '-', '-', '-'),
      Array('-', 'p', 'p', 'p', 'q', 'p', 'p', 'p'),
      Array('r', 'n', 'b', '-', 'k', 'b', 'n', 'r'),
    )
    state=(board,true)
    printboard(state)
    val input = readLine()
//    val x =validateInputForm(input)
//    println(x)
    if(validateInput(input, state)){
      state = applyAction(input,state)
      printboard(state)
    }
  }
}