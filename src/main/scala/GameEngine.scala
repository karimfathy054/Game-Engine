import javax.swing.JFrame
import scala.io.StdIn.readLine

object GameEngine {
  def getControllerAndDrawer(number :Int):(Controller,Drawer) = number match {
    case 1 => (new ChessController(),new ChessDrawer())
    case 2 => (new CheckersController(),new CheckersDrawer())
    case 3 => (new Connect4Controller(),new Connect4Drawer())
    case 4 => (new TicTacToeController(),new TicTacToeDrawer())
    case 5 => (new EightQueensController(),new EightQueensDrawer())
//    case 6 => (new SudokuController(),new SudokuDrawer())
    case 6 => (null,null)
  }
  def main(args: Array[String]): Unit = {
    println("choose a game\n" +
      "[1]Chess\n" +
      "[2]Checkers\n" +
      "[3]Connect4\n" +
      "[4]TicTacToe\n" +
      "[5]EightQueens\n" +
      "[6]Sudoku")
    val game = readLine()
    val gameNumber = game.toInt
    var (controller, drawer) = getControllerAndDrawer(gameNumber)
    if(gameNumber==5){
      var state = controller.EightQStateInit()
      val frame = new JFrame()
      drawer.draw(frame,state._1)
      while(true){
        println("enter the cell name to add/delete a queen\n" +
          "cell name as example:e4 , b2")
        val input = readLine()
        if(controller.EightQvalidateInput(input, state)){
          state = controller.EightQapplyAction(input, state)
          drawer.draw(frame,state._1)
        }else{
          println("input is not valid \n" +
            "try again")
        }
      }
    }else if (gameNumber == 6) {
      controller = new SudokuController()
      val sudokuDrawer = new SudokuDrawer()
      var state = controller.SudokuStateInit()
      var frame = new JFrame()
      sudokuDrawer.draw(frame, state)
      while (true) {
        println("enter the [row column value] to add a value\n" +
          "put them in that order [row column value]")
        val input = readLine()
        if (controller.SudokuvalidateInput(input, state)) {
          state = controller.SudokuapplyAction(input, state)
          sudokuDrawer.draw(frame, state)
        } else {
          println("input is not valid \n" +
            "try again")
        }
      }
    }else{
      var state = controller.stateInit()
      var frame = new JFrame()
      drawer.draw(frame, state._1)
      while (true) {
        if (state._2){
          println("player1 turn")
        }else{
          println("player2 turn")
        }
        println("in case of chess and checkers enter the name of the cells like the example [e2->e4] with the arrow\n" +
          "in case of tictactoe enter the row and column numbers as the example[1,1] with the comma\n" +
          "in case of connect4 just write the number of the column to add a piece to it")
        val input = readLine()
        if (controller.validateInput(input, state)) {
          state = controller.applyAction(input, state)
          frame = drawer.draw(frame, state._1)
        } else {
          println("input is not valid \n" +
            "try again")
        }
      }
    }
  }
}
