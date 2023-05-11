import javax.swing.*
import java.awt.*
import scala.annotation.tailrec

def chessStateInit()={
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
  (board, playerTurn)
}
def chessController(userInput:String,gameState:(Array[Array[Char]],Boolean)): (Array[Array[Char]], Boolean) ={
  def validateInputForm(rawInput: String): Boolean = {
    if (rawInput.matches("""^[a-h]+[1-8]+-\>[a-h]+[1-8]+$""")) {
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
    (fromRow, fromCol, toRow, toCol)
  }

  def validateMove(input: String, move: (String) => (Int, Int, Int, Int), state: (Array[Array[Char]], Boolean)): Boolean = {
    val board = state._1
    val playerTurn = state._2

    val (fromRow, fromCol, toRow, toCol) = move(input)

    if (fromRow < 0 || fromRow > 7 || fromCol < 0 || fromCol > 7 || toRow < 0 || toRow > 7 || toCol < 0 || toCol > 7) {
      println("out of bounds")
      return false
    }
    if (board(fromRow)(fromCol) == '-') {
      println("Clicked on empty space.")
      return false
    }

    if (playerTurn) {
      if (board(fromRow)(fromCol).isUpper) {
        println("Player 1 Tried to move Player 2's piece")
        return false
      }
      if (board(toRow)(toCol).isLower) {
        println("Player 1 released on his own pieces")
        return false
      }
    }
    else {
      if (board(fromRow)(fromCol).isLower) {
        println("Player 2 Tried to move Player 1's piece")
        return false
      }
      if (board(toRow)(toCol).isUpper) {
        println("Player 2 released on his own pieces")
        return false
      }
    }
    //return checkRules
    checkRules(move(input), state)
  }

  def whitePawnMove(move: (Int, Int, Int, Int), state: (Array[Array[Char]], Boolean)): Boolean = {
    val board = state._1
    val fromRow = move._1
    val fromCol = move._2
    val toRow = move._3
    val toCol = move._4
    var valid = false
    //move forward
    if (toCol == fromCol && toRow - fromRow == -1 && board(toRow)(toCol) == '-') { //one cell forward
      valid = true
    } else if (fromRow == 6 && toRow == 4 && toCol == fromCol && board(5)(toCol) == '-') { //two cells at the start
      valid = true
    } else if (toRow - fromRow == -1 && Math.abs(fromCol - toCol) == 1 && board(toRow)(toCol) != '-') { //pawn takes
      valid = true
    }
    if (toRow == 0 && valid) {
      //return promotion
    }
    valid
  }

  def blackPawnMove(move: (Int, Int, Int, Int), state: (Array[Array[Char]], Boolean)): Boolean = {
    val board = state._1
    val fromRow = move._1
    val fromCol = move._2
    val toRow = move._3
    val toCol = move._4
    var valid = false
    //move forward
    if (toCol == fromCol && toRow - fromRow == 1 && board(toRow)(toCol) == '-') { //one cell forward
      valid = true
    } else if (fromRow == 1 && toRow == 3 && toCol == fromCol && board(2)(toCol) == '-') { //two cells at the start
      valid = true
    } else if (toRow - fromRow == 1 && Math.abs(fromCol - toCol) == 1 && board(toRow)(toCol) != '-') { //pawn takes
      valid = true
    }
    if (toRow == 7 && valid) {
      //return promotion
    }
    valid
  }

  def checkPawn(move: (Int, Int, Int, Int), state: (Array[Array[Char]], Boolean)): Boolean = {
    val playerTurn = state._2 //white==true black==false
    if (playerTurn) {
      whitePawnMove(move, state)
    } else {
      blackPawnMove(move, state)
    }
  }

  def checkRook(move: (Int, Int, Int, Int), state: (Array[Array[Char]], Boolean)): Boolean = {
    val board = state._1
    //white==true black==false
    val fromRow = move._1
    val fromCol = move._2
    val toRow = move._3
    val toCol = move._4

    val isHorizontalMove = fromRow == toRow
    val isVerticalMove = fromCol == toCol
    if (!isVerticalMove && !isHorizontalMove) {
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

    clearPath(fromRow, fromCol, board)
  }

  def checkKnight(move: (Int, Int, Int, Int), state: (Array[Array[Char]], Boolean)): Boolean = {
    val board = state._1
    val playerTurn = state //white==true black==false
    val fromRow = move._1
    val fromCol = move._2
    val toRow = move._3
    val toCol = move._4
    if ((Math.abs(toRow - fromRow) == 2 && Math.abs(toCol - fromCol) == 1) || (Math.abs(toRow - fromRow) == 1 && Math.abs(toCol - fromCol) == 2)) {
      return true
    }
    false
  }

  def checkBishop(move: (Int, Int, Int, Int), state: (Array[Array[Char]], Boolean)): Boolean = {
    val board = state._1
    val playerTurn = state //white==true black==false
    val fromRow = move._1
    val fromCol = move._2
    val toRow = move._3
    val toCol = move._4
    val isDiagonalMove = Math.abs(fromCol - toCol) == Math.abs(fromRow - toRow)
    if (!isDiagonalMove) {
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
      (toRow - row, toCol - col) match {
        case x if (x._1 < 0 && x._2 < 0) => clearPath(row - 1, col - 1, board)
        case x if (x._1 < 0 && x._2 > 0) => clearPath(row - 1, col + 1, board)
        case x if (x._1 > 0 && x._2 < 0) => clearPath(row + 1, col - 1, board)
        case x if (x._1 > 0 && x._2 > 0) => clearPath(row + 1, col + 1, board)
      }
    }

    clearPath(fromRow, fromCol, board)
  }

  def checkQueen(move: (Int, Int, Int, Int), state: (Array[Array[Char]], Boolean)): Boolean = {
    val board = state._1
    val playerTurn = state //white==true black==false
    val fromRow = move._1
    val fromCol = move._2
    val toRow = move._3
    val toCol = move._4
    return checkBishop(move, state) || checkRook(move, state)
  }

  def checkKing(move: (Int, Int, Int, Int), state: (Array[Array[Char]], Boolean)): Boolean = {
    val board = state._1
    val playerTurn = state //white==true black==false
    val fromRow = move._1
    val fromCol = move._2
    val toRow = move._3
    val toCol = move._4
    var valid = false
    if (((toRow - fromRow) >= -1 && (toRow - fromRow) <= 1) && ((toCol - fromCol) >= -1 && (toCol - fromCol) <= 1)) {
      valid = true
      return true
    }
    //check if the king is getting checked in that place !!
    false
  }

  def checkRules(move: (Int, Int, Int, Int), state: (Array[Array[Char]], Boolean)) = {
    val board = state._1
    val playerTurn = state._2
    val fromRow = move._1
    val fromCol = move._2
    val toRow = move._3
    val toCol = move._4

    board(fromRow)(fromCol) match {
      case 'p' | 'P' => checkPawn(move, state)
      case 'r' | 'R' => checkRook(move, state)
      case 'n' | 'N' => checkKnight(move, state)
      case 'b' | 'B' => checkBishop(move, state)
      case 'q' | 'Q' => checkQueen(move, state)
      case 'k' | 'K' => checkKing(move, state)
    }

  }

  def validateInput(input: String, state: (Array[Array[Char]], Boolean)): Boolean = {
    if (validateInputForm(input)) {
      //      val move = parseInput(input)
      return validateMove(input, parseInput, state)
    } else {
      println("wrong input form")
      return false
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
    (board, playerTurn)
  }
  if(validateInput(userInput,gameState)){
    return applyAction(userInput,gameState)
  }
  println("your input is not valid")
  gameState
}
def chessDrawer(frame:JFrame , board:Array[Array[Char]])={
  def getChessPiece(row: Int, column: Int, board: Array[Array[Char]]): ImageIcon = {
    board(row)(column) match {
      case 'R' => new ImageIcon("src/main/scala/Chess/BR.png")
      case 'N' => new ImageIcon("src/main/scala/Chess/BN.png")
      case 'B' => new ImageIcon("src/main/scala/Chess/BB.png")
      case 'Q' => new ImageIcon("src/main/scala/Chess/BQ.png")
      case 'K' => new ImageIcon("src/main/scala/Chess/BK.png")
      case 'r' => new ImageIcon("src/main/scala/Chess/WR.png")
      case 'b' => new ImageIcon("src/main/scala/Chess/WB.png")
      case 'n' => new ImageIcon("src/main/scala/Chess/WN.png")
      case 'q' => new ImageIcon("src/main/scala/Chess/WQ.png")
      case 'k' => new ImageIcon("src/main/scala/Chess/WK.png")
      case 'P' => new ImageIcon("src/main/scala/Chess/BP.png")
      case 'p' => new ImageIcon("src/main/scala/Chess/WP.png")
      case '-' => null
    }
  }
  frame.setSize(800, 800)
  frame.setTitle("chess")
  frame.setLayout(new BorderLayout(5, 5))
  frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
  val chessBoard = new JPanel()
  chessBoard.setLayout(new GridLayout(8, 8))
  for (i <- 0 to 7) {
    for (j <- 0 to 7) {
      val cell = new JButton()
      cell.setFocusable(false)
      if ((i + j) % 2 == 1) {
        cell.setBackground(Color.BLACK)
      } else {
        cell.setBackground(Color.WHITE)
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