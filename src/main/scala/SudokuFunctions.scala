import java.awt.{BorderLayout, Color, GridLayout}
import javax.swing.{BorderFactory, JButton, JFrame, JLabel, JPanel, SwingConstants, WindowConstants}

def sudokuStateInit() = {
  val puzzle = Array(
    Array(('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false)),
    Array(('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false)),
    Array(('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false)),
    Array(('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false)),
    Array(('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false)),
    Array(('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false)),
    Array(('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false)),
    Array(('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false)),
    Array(('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false)),
  )

  // Recursively generate the puzzle.
  def generateRow(row: Int, col: Int): Unit = {
    if (col == 9) {
      // We've reached the end of the row, so return.
      return
    }

    // Generate a random value for the current cell.
    val value = scala.util.Random.between(1, 9).toString.charAt(0)

    // If the value is not present in the row or column, then set it.
    if (isValid(puzzle, row, col, value)) {
      puzzle(row)(col) = (value, true)
    }
  }

  // Generate the rest of the rows.
  for (row <- 0 until 9) {
    for (col <- 0 until 9) {
      generateRow(row, col)
    }
  }

  // Return the puzzle.
  puzzle
}

def validateInputForm(input: String): Boolean = {
  input.matches("""[1-9] [1-9] [1-9]""")
}

private def parserInput(input: String): (Int, Int, Char) = {
  val row = input.charAt(0) - '1'
  val col = input.charAt(2) - '1'
  val value = input.charAt(input.length - 1)
  (row, col, value)
}

def validateInput(rawInput: String, state: Array[Array[(Char, Boolean)]]): Boolean = {
  if (validateInputForm(rawInput)) {
    val input = parserInput(rawInput)
    val row = input._1
    val col = input._2
    val value = input._3
    if (state(row)(col)._2) {
      println("you can't modify that cell as it is a part of the puzzle")
      return false
    }
    if (isValid(state, row, col, value)) {
      true
    } else {
      println("that number is repeated in that row/column/block")
      false
    }
  } else {
    println("check your input form")
    false
  }
}

private def isValid(board: Array[Array[(Char, Boolean)]], row: Int, col: Int, value: Char): Boolean = {
  !board(row).contains((value, false)) && !board(row).contains((value, true)) &&
    !board.map(_(col)).contains((value, false)) && !board.map(_(col)).contains((value, true)) &&
    !getSquare(board, row, col).contains((value, false)) && !getSquare(board, row, col).contains((value, true))
}

private def getSquare(board: Array[Array[(Char, Boolean)]], row: Int, col: Int): Array[(Char, Boolean)] = {
  val rowStart = (row / 3) * 3
  val colStart = (col / 3) * 3
  board.slice(rowStart, rowStart + 3).flatMap(_.slice(colStart, colStart + 3))
}


def applyAction(input: String, state: Array[Array[(Char, Boolean)]]): Array[Array[(Char, Boolean)]] = {
  val move = parserInput(input)
  val row = move._1
  val col = move._2
  val value = move._3
  state(row)(col) = (value, false)

  state
}
def sudokuController(userInput: String, gameState: Array[Array[(Char, Boolean)]]): Array[Array[(Char, Boolean)]] = {
  if (validateInput(userInput, gameState)) {
    return applyAction(userInput, gameState)
  }
  gameState
}
def sudokuDrawer(frame: JFrame, board: Array[Array[(Char, Boolean)]]): JFrame = {
  frame.setSize(800, 800)
  frame.setTitle("Sudoku")
  frame.setLayout(new BorderLayout(5, 5))
  frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
  val bord = new JPanel()
  bord.setLayout(new GridLayout(3, 3))
  for (i <- 0 to 2) {
    for (j <- 0 to 2) {
      val block = new JPanel()
      //        block.setSize(150,150)
      block.setLayout(new GridLayout(3, 3))
      for (y <- 0 to 2) {
        for (x <- 0 to 2) {
          val cell = new JButton()
          //            cell.setSize(50,50)
          cell.setBackground(Color.WHITE)
          cell.setText(board(y + 3 * i)(x + 3 * j)._1.toString)
          block.add(cell)
        }
      }
      block.setBorder(BorderFactory.createLineBorder(Color.BLUE))
      bord.add(block)
    }
  }
  val indexCol = new JPanel()
  indexCol.setLayout(new GridLayout(9, 1))
  for (i <- 1 to 9) {
    val label = new JLabel()
    label.setText(i.toString)
    indexCol.add(label)
  }
  val indexRow = new JPanel()
  indexRow.setLayout(new GridLayout(1, 9))
  for (i <- 1 to 9) {
    val label = new JLabel("label", SwingConstants.CENTER)
    label.setText(i.toString)
    indexRow.add(label)
  }
  frame.add(indexCol, BorderLayout.WEST)
  frame.add(bord, BorderLayout.CENTER)
  frame.add(indexRow, BorderLayout.SOUTH)

  frame.setVisible(true)
  frame
}