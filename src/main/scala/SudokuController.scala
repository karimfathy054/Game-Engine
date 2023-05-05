

class SudokuController extends Controller {

  def SudokuStateInit(): Array[Array[Char]] = {
    // Create an empty puzzle of the specified size.
    val puzzle = Array(
      Array('-', '-', '-', '-', '-', '-', '-', '-', '-'),
      Array('-', '-', '-', '-', '-', '-', '-', '-', '-'),
      Array('-', '-', '-', '-', '-', '-', '-', '-', '-'),
      Array('-', '-', '-', '-', '-', '-', '-', '-', '-'),
      Array('-', '-', '-', '-', '-', '-', '-', '-', '-'),
      Array('-', '-', '-', '-', '-', '-', '-', '-', '-'),
      Array('-', '-', '-', '-', '-', '-', '-', '-', '-'),
      Array('-', '-', '-', '-', '-', '-', '-', '-', '-'),
      Array('-', '-', '-', '-', '-', '-', '-', '-', '-')
    )

    // Recursively generate the puzzle.
    def generateRow(row: Int, col: Int): Unit = {
      if (col == 9) {
        // We've reached the end of the row, so return.
        return
      }

      // Generate a random value for the current cell.
      val value = scala.util.Random.between(1,9).toString.charAt(0)

      // If the value is not present in the row or column, then set it.
      if(isValid(puzzle,row, col, value)){
        puzzle(row)(col) = value
      }
    }

    // Generate the rest of the rows.
    for (row <- 0 until 9 ) {
      for(col <- 0 until 9){
        generateRow(row, col)
      }
    }

    // Return the puzzle.
    puzzle
  }

  def validateInputForm(input: String): Boolean = {
    input.matches("""[1-9] [1-9] [1-9]""")
  }

  private def parserInput(input:String): (Int, Int, Char) ={
    val row = input.charAt(0) - '1'
    val col = input.charAt(2) - '1'
    val value = input.charAt(input.length-1)
    (row,col,value)
  }

  def SudokuvalidateInput(rawInput:String, state:Array[Array[Char]]):Boolean={
    if(validateInputForm(rawInput)){
      val input = parserInput(rawInput)
      val row = input._1
      val col = input._2
      val value = input._3
      if(isValid(state,row, col, value)){
        true
      }else{
        println("that number is repeated in that row/column/block")
        false
      }
    }else{
      println("check your input form")
      false
    }
  }

  private def isValid(board: Array[Array[Char]], row: Int, col: Int, value: Char): Boolean = {
      !board(row).contains(value) &&
      !board.map(_(col)).contains(value) &&
      !getSquare(board, row, col).contains(value)
  }

  private def getSquare(board: Array[Array[Char]], row: Int, col: Int): Array[Char] = {
    val rowStart = (row / 3) * 3
    val colStart = (col / 3) * 3
    board.slice(rowStart, rowStart + 3).flatMap(_.slice(colStart, colStart + 3))
  }

  def SudokuapplyAction(input:String, state:Array[Array[Char]]): Array[Array[Char]] ={
    val move = parserInput(input)
    val row = move._1
    val col = move._2
    val value = move._3
    state(row)(col) = value

    state
  }

  private def print(board:Array[Array[Char]]): Unit ={
    for(i<-0 to 8){
      for (j<-0 to 8){
        printf(" %c | ",board(i)(j))
      }
      printf("\n---------------------------------------------------------\n")
    }
  }

//  def main(args: Array[String]): Unit = {
//    var state = stateInit()
//    print(state)
//    val input = readLine()
//    if (validateInput(input, state)) {
//      state = applyAction(input, state)
//      print(state)
//    }
//  }

  override def stateInit(): (Array[Array[Char]], Boolean) = ???

  override def EightQStateInit(): (Array[Array[Char]], List[(Int, Int)]) = ???

  override def EightQvalidateInput(input: String, state: (Array[Array[Char]], List[(Int, Int)])): Boolean = ???

  override def EightQapplyAction(input: String, state: (Array[Array[Char]], List[(Int, Int)])): (Array[Array[Char]], List[(Int, Int)]) = ???

  override def validateInput(input: String, state: (Array[Array[Char]], Boolean)): Boolean = ???

  override def applyAction(input: String, state: (Array[Array[Char]], Boolean)): (Array[Array[Char]], Boolean) = ???
}
