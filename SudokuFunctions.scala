import java.awt.{BorderLayout, Color, GridLayout}
import javax.swing.{BorderFactory, JButton, JFrame, JLabel, JPanel, SwingConstants, WindowConstants}
import scala.io.StdIn.readLine
import org.jpl7.*

import java.awt.event.{ActionEvent, ActionListener}
import java.io.{File, PrintWriter}

def sudokuStateInit() = {
//  val puzzle = Array(
//    Array(('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false)),
//    Array(('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false)),
//    Array(('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false)),
//    Array(('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false)),
//    Array(('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false)),
//    Array(('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false)),
//    Array(('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false)),
//    Array(('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false)),
//    Array(('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false), ('-', false)),
//  )
  val puzzle = Array(
  Array('0','0','0','0','0','0','0','0','0'),
  Array('0','0','0','0','0','0','0','0','0'),
  Array('0','0','0','0','0','0','0','0','0'),
  Array('0','0','0','0','0','0','0','0','0'),
  Array('0','0','0','0','0','0','0','0','0'),
  Array('0','0','0','0','0','0','0','0','0'),
  Array('0','0','0','0','0','0','0','0','0'),
  Array('0','0','0','0','0','0','0','0','0'),
  Array('0','0','0','0','0','0','0','0','0')
  )

  val x = scala.util.Random.between(0, 3)
  val puzzle1 = Array(
    Array('0', ('7' + 20).toChar, '0', '0', ('2' + 20).toChar, '0', '0', ('4' + 20).toChar, ('6' + 20).toChar),
    Array('0', ('6' + 20).toChar, '0', '0', '0', '0', ('8' + 20).toChar, ('9' + 20).toChar, '0'),
    Array(('2' + 20).toChar, '0', '0', ('8' + 20).toChar, '0', '0', ('7' + 20).toChar, ('1' + 20).toChar, ('5' + 20).toChar),
    Array('0', ('8' + 20).toChar, ('4' + 20).toChar, '0', ('9' + 20).toChar, ('7' + 20).toChar, '0', '0', '0'),
    Array(('7' + 20).toChar, ('1' + 20).toChar, '0', '0', '0', '0', '0', ('5' + 20).toChar, ('9' + 20).toChar),
    Array('0', '0', '0', ('1' + 20).toChar, ('3' + 20).toChar, '0', ('4' + 20).toChar, ('8' + 20).toChar, '0'),
    Array(('6' + 20).toChar, ('9' + 20).toChar, ('7' + 20).toChar, '0', '0', ('2' + 20).toChar, '0', '0', ('8' + 20).toChar),
    Array('0', ('5' + 20).toChar, ('8' + 20).toChar, '0', '0', '0', '0', ('6' + 20).toChar, '0'),
    Array(('4' + 20).toChar, ('3' + 20).toChar, '0', '0', ('8' + 20).toChar, '0', '0', ('7' + 20).toChar, '0')
  )

  val puzzle2 = Array(
    Array(('8' + 20).toChar, '0', ('6' + 20).toChar, '0', ('1' + 20).toChar, '0', '0', '0', '0'),
    Array('0', '0', ('3' + 20).toChar, '0', ('6' + 20).toChar, ('4' + 20).toChar, '0',('9' + 20).toChar, '0'),
    Array(('9' + 20).toChar, '0', '0', '0', '0', '0', ('8' + 20).toChar, ('1' + 20).toChar, ('6' + 20).toChar),
    Array('0', ('8' + 20).toChar, '0', ('3' + 20).toChar, ('9' + 20).toChar, ('6' + 20).toChar, '0', '0', '0'),
    Array(('7' + 20).toChar, '0', ('2' + 20).toChar, '0', ('4' + 20).toChar, '0', ('3' + 20).toChar, '0', ('9' + 20).toChar),
    Array('0', '0', '0', ('5' + 20).toChar, ('7' + 20).toChar, ('2' + 20).toChar, '0', ('8' + 20).toChar , '0'),
    Array(('5' + 20).toChar, ('2' + 20).toChar, ('1' + 20).toChar, '0', '0', '0', '0', '0', ('4' + 20).toChar),
    Array('0', ('3' + 20).toChar, '0', ('7' + 20).toChar, ('5' + 20).toChar, '0', ('2' + 20).toChar, '0', '0'),
    Array('0', '0', '0', '0', ('2' + 20).toChar, '0', ('1' + 20).toChar, '0', ('5' + 20).toChar)
  )

  val puzzle3 = Array(
    Array('0', '0', ('1' + 20).toChar, ('3' + 20).toChar, '0', ('2' + 20).toChar, '0', '0', '0'),
    Array('0', '0', ('3' + 20).toChar, '0', '0', ('7' + 20).toChar, '0', ('4' + 20).toChar, ('5' + 20).toChar),
    Array('0', '0', ('7' + 20).toChar, '0', '0', '0', '0', '0', ('9' + 20).toChar),
    Array('0', '0', ('6' + 20).toChar, ('5' + 20).toChar, '0', '0', '0', ('7' + 20).toChar, '0'),
    Array(('2' + 20).toChar, '0', '0', '0', '0', '0', '0', '0', ('1' + 20).toChar),
    Array('0', ('9' + 20).toChar, '0', '0', '0', ('1' + 20).toChar, ('4' + 20).toChar, '0', '0'),
    Array(('5' + 20).toChar, '0', '0', '0', '0', '0', ('9' + 20).toChar, '0', '0'),
    Array(('6' + 20).toChar, ('1' + 20).toChar, '0', ('2' + 20).toChar, '0', '0', ('8' + 20).toChar, '0', '0'),
    Array('0', '0', '0', ('9' + 20).toChar, '0', ('8' + 20).toChar, ('5' + 20).toChar, '0', '0')
  )

  val puzzle4 = Array(
    Array('0', ('7' + 20).toChar, '0', '0', '0', '0', '0', '0', ('4' + 20).toChar),
    Array('0', ('5' + 20).toChar, '0', '0', ('7' + 20).toChar, '0', ('8' + 20).toChar, '0', '0'),
    Array('0', ('8' + 20).toChar, '0', ('9' + 20).toChar, ('5' + 20).toChar, '0', '0', '0', '0'),
    Array('0', ('9' + 20).toChar, '0', ('2' + 20).toChar, '0', '0', ('5' + 20).toChar, '0', ('1' + 20).toChar),
    Array('0', '0', ('1' + 20).toChar, '0', '0', '0', ('4' + 20).toChar, '0', '0'),
    Array(('7' + 20).toChar, '0', ('5' + 20).toChar, '0', '0', ('1' + 20).toChar, '0', ('3' + 20).toChar, '0'),
    Array('0', '0', '0', '0', ('1' + 20).toChar, ('6' + 20).toChar, '0', ('8' + 20).toChar, '0'),
    Array('0', '0', ('2' + 20).toChar, '0', ('3' + 20).toChar, '0', '0', ('4' + 20).toChar, '0'),
    Array(('4' + 20).toChar, '0', '0', '0', '0', '0', '0', ('6' + 20).toChar, '0')
  )


  // Recursively generate the puzzle.
  def generateRow(row: Int, col: Int): Unit = {
    if (col == 9) {
      // We've reached the end of the row, so return.
      return
    }

    // Generate a random value for the current cell.
    var value = scala.util.Random.between(1, 9).toString.charAt(0)

    // If the value is not present in the row or column, then set it.
    if (isValid(puzzle, row, col, value)) {
      puzzle(row)(col) = (value + 20).toChar
    }
  }

  // Generate the rest of the rows.
  for (row <- 0 until 9) {
    for (col <- 0 until 9) {
      generateRow(row, col)
    }
  }
  //  for(i<-0 to 40){
  //
  //      val x = scala.util.Random.between(1, 9).toString.charAt(0)
  //      val y = scala.util.Random.between(1, 9).toString.charAt(0)
  //      puzzle(i)(i) = '0'
  //
  //  }

  val arr = Array(puzzle1,puzzle2,puzzle3,puzzle4,puzzle)
  // Return the puzzle.
  (arr(x),true)
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

def validateInput(rawInput: String, state: Array[Array[(Char)]]): Boolean = {
  if (validateInputForm(rawInput)) {
    val input = parserInput(rawInput)
    val row = input._1
    val col = input._2
    val value = input._3
    if (state(row)(col).toInt > 68 &&state(row)(col).toInt < 78) {
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

private def isValid(board: Array[Array[(Char)]], row: Int, col: Int, value: Char): Boolean = {
  !board(row).contains((value + 10).toChar) && !board(row).contains((value + 20).toChar) &&
    !board.map(_(col)).contains((value + 10).toChar) && !board.map(_(col)).contains((value + 20).toChar) &&
    !getSquare(board, row, col).contains((value + 10).toChar) && !getSquare(board, row, col).contains((value + 20).toChar)
}

private def getSquare(board: Array[Array[(Char)]], row: Int, col: Int): Array[(Char)] = {
  val rowStart = (row / 3) * 3
  val colStart = (col / 3) * 3
  board.slice(rowStart, rowStart + 3).flatMap(_.slice(colStart, colStart + 3))
}


def applyAction(input: String, state: (Array[Array[(Char)]],Boolean)): (Array[Array[(Char)]],Boolean) = {
  val move = parserInput(input)
  val row = move._1
  val col = move._2
  val value = move._3
  state._1(row)(col) = (value + 10).toChar

  state
}
def sudokuController(userInput: String, gameState: (Array[Array[(Char)]],Boolean)): (Array[Array[(Char)]],Boolean) = {
  if (validateInput(userInput, gameState._1)) {
    return applyAction(userInput, gameState)
  }
  gameState
}
def sudokuDrawer(frame: JFrame, board: Array[Array[Char]]): JFrame = {
  frame.setSize(900, 800)
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
          var number = board(y + 3 * i)(x + 3 * j)
          if(number .toInt >68 && number .toInt <78 ){
            number = (number -20).toChar
            cell.setBackground(Color.CYAN)

          }else if(number.toInt > 58 && number.toInt <68){
            number = (number - 10).toChar
            cell.setBackground(Color.WHITE)

          }else{
            number = ' '
            cell.setBackground(Color.WHITE)

          }
          cell.setText(number.toString)
          block.add(cell)
        }
      }
      block.setBorder(BorderFactory.createLineBorder(Color.BLUE))
      bord.add(block)
    }
  }

  val file = new File("src/prolog/sudokuproblem.pl")
  val writer = new PrintWriter(file)
  writer.println("problem(1, P) :-")
  writer.print("P = [")

  for(i <- 0 to 8){
    writer.print("[")
    for(j <- 0 to 8){
      var number = board(i)(j)
      if (number.toInt > 68 && number.toInt < 78) {
        number = (number - 20).toChar

      } else if (number.toInt > 58 && number.toInt < 68) {
        number = (number - 10).toChar

      } else {
        number = '_'

      }
      writer.print(number)
      if(j != 8) {
        writer.print(",")
      }
    }
    writer.print("]")
    if (i != 8) {
      writer.println(",")
    }
  }
  writer.print("].")
  writer.close()
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
  val solveButton = new JButton();
  solveButton.setText("Solve");
  solveButton.addActionListener(new ActionListener(){
    def actionPerformed(e: ActionEvent): Unit = {
      val query = new Query("consult('src/prolog/sudokuproblem.pl'),consult('src/prolog/sudoku.pl').")
      val query2 = new Query("problem(1, Rows), sudoku(Rows),maplist(labeling([ff]), Rows), maplist(portray_clause, Rows).")
      //print solution in terminal or put it on drawer
    }
  })
  frame.add(indexCol, BorderLayout.WEST)
  frame.add(bord, BorderLayout.CENTER)
  frame.add(indexRow, BorderLayout.SOUTH)
  frame.add(solveButton,BorderLayout.SOUTH)
  frame.setVisible(true)
  frame
}

//@main
//def main():Unit = {
////  val x = ('1' + 10).toInt
////  println(x)
//    var state = sudokuStateInit()
//    var frame = new JFrame()
//    sudokuDrawer(frame ,state._1)
//  while (true) {
//    println(">>enter the [row column value] to add a value\n" +
//      ">>put them in that order [row column value]")
//    val input = readLine()
//    state = sudokuController(input, state)
//    frame = sudokuDrawer(frame, state._1)
//  }
//}