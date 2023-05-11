import javax.swing.JFrame
import scala.io.StdIn.readLine

@main
def main(): Unit = {
  println("choose a game\n" +
    "[1]Chess\n" +
    "[2]Checkers\n" +
    "[3]Connect4\n" +
    "[4]TicTacToe\n" +
    "[5]EightQueens\n" +
    "[6]Sudoku")
  val game = readLine()
  val gameNumber = game.toInt
  val (controller, drawer) = getControllerAndDrawer(gameNumber)
  val initialState = getInitialState(gameNumber)
  GameEngine(initialState , controller , drawer)

}