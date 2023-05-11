import javax.swing._
import java.awt._

def checkersStateInit(): (Array[Array[Char]], Boolean) ={
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
  (board, playerTurn)
}
def checkersController(userInput:String,gameState:(Array[Array[Char]],Boolean)): (Array[Array[Char]], Boolean) ={
  def validateInputForm(rawInput: String): Boolean = {
    if (rawInput.matches("""^[a-h]+[1-8]+-\>[a-h]+[1-8]+$""")) {
      //return parse input
      return true
    }
    println("wrong input form")
    false
  }

  def parseInput(input: String): (Int, Int, Int, Int) = {
    val fromRow = '8' - input.charAt(1)
    val fromCol = input.charAt(0) - 'a'
    val toRow = '8' - input.charAt(input.length - 1)
    val toCol = input.charAt(input.length - 2) - 'a'
    //return validate move
    (fromRow, fromCol, toRow, toCol)
  }

  def redCheckerMove(move: (Int, Int, Int, Int), state: (Array[Array[Char]], Boolean)): Boolean = {
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

  def redCheckerEat(move: (Int, Int, Int, Int), state: (Array[Array[Char]], Boolean)): Boolean = {
    val board = state._1
    val fromRow = move._1
    val fromCol = move._2
    val toRow = move._3
    val toCol = move._4
    var valid = false
    //move forward
    if (toRow - fromRow == -2 && Math.abs(fromCol - toCol) == 2 && board(toRow)(toCol) == '-' && board(fromRow - 1)((fromCol + toCol) / 2) == 'B') { //pawn takes
      valid = true
    }
    valid
  }

  def blackCheckerMove(move: (Int, Int, Int, Int), state: (Array[Array[Char]], Boolean)): Boolean = {
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

  def blackCheckerEat(move: (Int, Int, Int, Int), state: (Array[Array[Char]], Boolean)): Boolean = {
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

  def checkRules(move: (Int, Int, Int, Int), state: (Array[Array[Char]], Boolean)): Boolean = {
    val playerTurn = state._2 //white==true black==false
    if (playerTurn) {
      redCheckerMove(move, state) || redCheckerEat(move, state)
    } else {
      blackCheckerMove(move, state) || blackCheckerEat(move, state)
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
    if (Math.abs(fromCol - toCol) == 2) {
      board((toRow + fromRow) / 2)((toCol + fromCol) / 2) = '-'
    }
    (board, playerTurn)
  }
  if(validateInput(userInput,gameState)){
    return applyAction(userInput,gameState)
  }
  println("your input is not valid")
  gameState
}
def checkersDrawer(frame: JFrame, board: Array[Array[Char]]): JFrame = {
  def getChessPiece(row: Int, column: Int, board: Array[Array[Char]]): ImageIcon = {
    board(row)(column) match {
      case 'R' => new ImageIcon("src/main/scala/CheckerImages/WHITE.png")
      case 'B' => new ImageIcon("src/main/scala/CheckerImages/BLAACK.png")
      case '-' => null
    }
  }
  frame.setSize(800, 800)
  frame.setTitle("Checkers")
  frame.setLayout(new BorderLayout(5, 5))
  frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
  val chessBoard = new JPanel()
  chessBoard.setLayout(new GridLayout(8, 8))
  for (i <- 0 to 7) {
    for (j <- 0 to 7) {
      val cell = new JButton()
      if ((i + j) % 2 == 1) {
        cell.setBackground(Color.DARK_GRAY)
      } else {
        cell.setBackground(Color.LIGHT_GRAY)
      }
      cell.setSize(50, 50)
      val chessPiece = getChessPiece(i, j, board)
      cell.setIcon(chessPiece)
      chessBoard.add(cell)
    }
  }
  val indexCol = new JPanel()
  indexCol.setLayout(new GridLayout(8, 1))
  for (i <- 8 to 1 by -1) {
    val label = new JLabel()
    label.setText(i.toString)
    indexCol.add(label)
  }
  val indexRow = new JPanel()
  indexRow.setLayout(new GridLayout(1, 8))
  for (i <- 0 to 7) {
    val label = new JLabel("label", SwingConstants.CENTER)
    label.setText(('a' + i).toChar.toString)
    //      label.setHorizontalTextPosition(SwingConstants.EAST)
    indexRow.add(label)
  }
  //    indexRow.setSize(10,800)
  frame.add(indexCol, BorderLayout.WEST)
  frame.add(chessBoard, BorderLayout.CENTER)
  frame.add(indexRow, BorderLayout.SOUTH)

  frame.setVisible(true)
  frame
}