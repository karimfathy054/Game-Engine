import org.jpl7.Query

import java.awt.event.{ActionEvent, ActionListener}
import java.awt.{BorderLayout, Color, GridLayout}
import scala.annotation.tailrec
import javax.swing.*
def eightQueensStateInit(): (Array[Array[Char]],Boolean) = {
  val board = Array(
    Array('-', '-', '-', '-', '-', '-', '-', '-'),
    Array('-', '-', '-', '-', '-', '-', '-', '-'),
    Array('-', '-', '-', '-', '-', '-', '-', '-'),
    Array('-', '-', '-', '-', '-', '-', '-', '-'),
    Array('-', '-', '-', '-', '-', '-', '-', '-'),
    Array('-', '-', '-', '-', '-', '-', '-', '-'),
    Array('-', '-', '-', '-', '-', '-', '-', '-'),
    Array('-', '-', '-', '-', '-', '-', '-', '-'),
  )
  (board, true)
}
def eightQueensController(userInput: String, gameState: (Array[Array[Char]],Boolean)): (Array[Array[Char]],Boolean) = {
  def validateInputForm(rawInput: String): Boolean = {
    if (rawInput.matches("""^[a-h]+[1-8]+$""")) {
      return true
    }
    println("wrong input form")
    false
  }

  def parseInput(input: String): (Int, Int) = {
    val fromRow = '8' - input.charAt(1)
    val fromCol = input.charAt(0) - 'a'
    (fromRow, fromCol)
  }

  def validateMove(pos: (Int, Int), state: (Array[Array[Char]], Boolean)): Boolean = {
    val board = state._1
    val queens = getQueens(board)
    val row = pos._1
    val col = pos._2
    if (row < 0 || row > 7 || col < 0 || col > 7) {
      println("out of bounds")
      return false
    }
    if (board(row)(col) == '-') {
      !conflict(pos, queens)
    }
    else {
      true
    }
  }
  def getQueens(board:Array[Array[Char]]):List[(Int,Int)]={
    val queens = for {
      (row,col) <- board.indices.flatMap(i=>board(i).indices.map(j=>(i,j)))
      if(board(row)(col))=='Q'
    }yield (row,col)
    queens.toList
  }
  def threat(p1: (Int, Int), p2: (Int, Int)): Boolean = {
    (p1._1 + p1._2 == p2._1 + p2._2) || (p1._1 - p1._2 == p2._1 - p2._2) || (p1._1 == p2._1) || (p1._2 == p2._2)
  }

  @tailrec
  def conflict(p: (Int, Int), sol: List[(Int, Int)]): Boolean = sol match {
    case Nil => false
    case q :: qs => threat(p, q) || conflict(p, qs)
  }

  def validateInput(input: String, state: (Array[Array[Char]],Boolean)): Boolean = {
    if (validateInputForm(input)) {
      val position = parseInput(input)
      if (validateMove(position, state)) {
        return true
      } else {
        println("there may be a conflict between queens")
        return false
      }
    }
    false
  }

  def applyAction(input: String, state: (Array[Array[Char]],Boolean)): (Array[Array[Char]],Boolean) = {
    val move = parseInput(input)
    val row = move._1
    val col = move._2
    val board = state._1
    val queens = getQueens(board)

    if (board(row)(col) == '-') {
      board(row)(col) = 'Q'
      val newList = move :: queens
      (board, true)
    } else {
      board(row)(col) = '-'
      val newList = queens.filterNot(_ == move)
      (board, true)
    }
  }

  if (validateInputForm(userInput)) {
    val position = parseInput(userInput)
    if (validateMove(position,gameState )) {
      return applyAction(userInput, gameState)
    }
    else {
      println("there may be a conflict between queens")
    }
  } else {
    println("your input is not valid")
  }
  gameState
}

def eightQueensDrawer(frame: JFrame, board: Array[Array[Char]]): JFrame = {
  def getChessPiece(row: Int, column: Int, board: Array[Array[Char]]): ImageIcon = {
    board(row)(column) match {
      case 'Q' => new ImageIcon("src/main/scala/Chess/BQ.png")
      case '-' => null
    }
  }

  frame.setSize(800, 800)
  frame.setTitle("EightQueens")
  frame.setLayout(new BorderLayout(5, 5))
  frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
  val chessBoard = new JPanel()
  chessBoard.setLayout(new GridLayout(8, 8))
  for (i <- 0 to 7) {
    for (j <- 0 to 7) {
      val cell = new JButton()
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
  val solveButton = new JButton();
  solveButton.setText("Solve");
  solveButton.addActionListener(new ActionListener() {
    def actionPerformed(e: ActionEvent): Unit = {
      val query = new Query("consult(src/prolog/nqueens.pl).")
      val query2 = new Query("n_queens(8, Qs), labeling([ff], Qs).")
      //te
    }
  })
  frame.add(indexCol, BorderLayout.WEST)
  frame.add(chessBoard, BorderLayout.CENTER)
  frame.add(indexRow, BorderLayout.SOUTH)
  frame.add(solveButton, BorderLayout.SOUTH)

  frame.setVisible(true)
  frame
}