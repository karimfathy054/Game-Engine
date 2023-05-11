import java.awt.{BorderLayout, Color, GridLayout, Image}
import java.io.File
import javax.imageio.ImageIO
import javax.swing.{ImageIcon, JButton, JFrame, JLabel, JPanel, SwingConstants, WindowConstants}

def XOStateInit() = {
  val board = Array(
    Array('-', '-', '-'),
    Array('-', '-', '-'),
    Array('-', '-', '-')
  )
  val playerTurn = true
  (board, playerTurn)
}
def XOcontroller(userInput: String, gameState: (Array[Array[Char]], Boolean)): (Array[Array[Char]], Boolean) = {
  def validateInputForm(input: String): Boolean = {
    if (input.matches("""[1-3]+\,+[1-3]""")) {
      return true
    }
    println("wrong input form")
    false
  }

  def parseInput(input: String) = {
    val row = input.charAt(0) - '1'
    val col = input.charAt(input.length - 1) - '1'
    (row, col)
  }

  def validateInput(input: String, state: (Array[Array[Char]], Boolean)): Boolean = {
    val board = state._1
    if (validateInputForm(input)) {
      val position = parseInput(input)
      if (board(position._1)(position._2) == '-') {
        return true
      } else {
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
    (board, playerTurn)
  }

  if (validateInput(userInput, gameState)) {
    return applyAction(userInput, gameState)
  }
  println(("your input is not valid"))
  gameState
}

def XOdrawwer(frame: JFrame, board: Array[Array[Char]]): JFrame = {
  def getGamePieces(row: Int, column: Int, board: Array[Array[Char]]): ImageIcon = {
    board(row)(column) match {
      case 'O' => {
        val image = ImageIO.read(new File("src/main/scala/tic-tac-toe-images/O-removebg-preview.png")).getScaledInstance(256, 256, Image.SCALE_DEFAULT)
        new ImageIcon(image)
      }
      case 'X' => {
        val image = ImageIO.read(new File("src/main/scala/tic-tac-toe-images/X-removebg-preview.png")).getScaledInstance(256, 256, Image.SCALE_DEFAULT)
        new ImageIcon(image)
      }
      case '-' => null
    }

  }

  frame.setSize(800, 800)
  frame.setTitle("TicTacToe")
  frame.setLayout(new BorderLayout(5, 5))
  frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
  val chessBoard = new JPanel()
  chessBoard.setLayout(new GridLayout(3, 3))
  for (i <- 0 to 2) {
    for (j <- 0 to 2) {
      val cell = new JButton()
      //        if ((i + j) % 2 == 1) {
      //          cell.setBackground(Color.BLACK)
      //        } else {
      //          cell.setBackground(Color.WHITE)
      //        }
      cell.setBackground(Color.white)
      cell.setSize(50, 50)
      val chessPiece = getGamePieces(i, j, board)
      cell.setIcon(chessPiece)
      chessBoard.add(cell)
    }
  }
  val indexCol = new JPanel()
  indexCol.setLayout(new GridLayout(3, 1))
  for (i <- 1 to 3) {
    val label = new JLabel()
    label.setText(i.toString)
    indexCol.add(label)
  }
  val indexRow = new JPanel()
  indexRow.setLayout(new GridLayout(1, 3))
  for (i <- 1 to 3) {
    val label = new JLabel("label", SwingConstants.CENTER)
    label.setText(i.toString)
    indexRow.add(label)
  }
  frame.add(indexCol, BorderLayout.WEST)
  frame.add(chessBoard, BorderLayout.CENTER)
  frame.add(indexRow, BorderLayout.SOUTH)

  frame.setVisible(true)
  frame
}