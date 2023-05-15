import javax.swing._
import java.awt._

def connect4StateInit()={
val board = Array(
  Array('-', '-', '-', '-', '-', '-', '-'),
  Array('-', '-', '-', '-', '-', '-', '-'),
  Array('-', '-', '-', '-', '-', '-', '-'),
  Array('-', '-', '-', '-', '-', '-', '-'),
  Array('-', '-', '-', '-', '-', '-', '-'),
  Array('-', '-', '-', '-', '-', '-', '-')
)
val playerTurn = true //red player==true yellow player==false
(board, playerTurn)
}
def connect4Controller(userInput: String, gameState: (Array[Array[Char]], Boolean)): (Array[Array[Char]], Boolean) = {
  def isColumnFull(board: Array[Array[Char]], column: Int): Boolean = {
    // Check if the column is full
    val columnList = board.map(_(column)).toList
    columnList match {
      case Nil => false
      case head :: tail if head != '-' => true
      case _ => false
    }
  }

  def validateInput(input: String, state: (Array[Array[Char]], Boolean)): Boolean = {
    val board = state._1
    if (input.matches("""[1-7]+$""")) {
      val colNumber = input.charAt(0) - '1'
      if (!isColumnFull(board, colNumber)) {
        true
      } else {
        println("column is full")
        false
      }
    } else {
      println("out of bounds")
      false
    }
  }

  def getAvailableRow(colNumber: Int, board: Array[Array[Char]]): Int = {
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

  if (validateInput(userInput, gameState)) {
    return applyAction(userInput, gameState)
  }
  println("your input is not valid")
  gameState
}
def connect4Drawer(frame: JFrame, board: Array[Array[Char]]): JFrame = {
  def getGamePiece(row: Int, column: Int, board: Array[Array[Char]]): ImageIcon = {
    board(row)(column) match {
      case 'R' => new ImageIcon("src/main/scala/Connect4Images/red-circle.png")
      case 'Y' => new ImageIcon("src/main/scala/Connect4Images/yellow-circle.png")
      case '-' => new ImageIcon("src/main/scala/CheckerImages/WHITE.png")
    }
  }

  frame.setSize(800, 800)
  frame.setTitle("Connect4")
  frame.setLayout(new BorderLayout(5, 5))
  frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
  val gameBoard = new JPanel()
  gameBoard.setLayout(new GridLayout(6, 7))
  for (i <- 0 to 5) {
    for (j <- 0 to 6) {
      val cell = new JButton()
      cell.setBackground(Color.BLUE)
      cell.setSize(50, 50)
      val chessPiece = getGamePiece(i, j, board)
      cell.setIcon(chessPiece)
      gameBoard.add(cell)
    }
  }
  val indexRow = new JPanel()
  indexRow.setLayout(new GridLayout(1, 9))
  for (i <- 1 to 7) {
    val label = new JLabel("label", SwingConstants.CENTER)
    label.setText(i.toString)
    indexRow.add(label)
  }
  frame.add(gameBoard, BorderLayout.CENTER)
  frame.add(indexRow, BorderLayout.SOUTH)

  frame.setVisible(true)
  frame
}