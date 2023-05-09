import javax.swing.JFrame
import scala.io.StdIn.readLine

def getControllerAndDrawer(gameNumber:Int)= gameNumber match{
  case 1 => (chessController,chessDrawer)
  case 2 => (checkersController,checkersDrawer)
  case 3 => (connect4Controller,connect4Drawer)
  case 4 => (XOcontroller,XOdrawwer)
  case 5 => (eightQueensController,eightQueensDrawer)
//  case 6 => (sudokuController,sudokuDrawer)
  case _ => (null,null)
}
def getInitialState(gameNumber:Int)=gameNumber match{
  case 1 => chessStateInit()
  case 2 => checkersStateInit()
  case 3 => connect4StateInit()
  case 4 => XOStateInit()
  case 5 => eightQueensStateInit()
//  case 6 => sudokuStateInit()
}

def GameEngine(initialState:(Array[Array[Char]],Boolean),controller:(String,(Array[Array[Char]],Boolean))=>((Array[Array[Char]],Boolean)),drawer:(JFrame,Array[Array[Char]])=>JFrame)={
  var gamestate = initialState
  var frame = new JFrame()
  frame = drawer(frame, gamestate._1)
  while (true){
    println(">>in case of Chess/Checker enter the name of initial and destination cells of the piece you want to move in the form of a2->a4\n" +
      ">>in case of TicTacToe enter the number or row followed by a comma then the number of column with no spaces in between as 2,2\n" +
      ">>in case of Connect4 enter the number of column only\n" +
      ">>in case of EightQueens enter the name of the cell to add/remove a queen in it")
    val input = readLine()
    gamestate = controller(input, gamestate)
    frame = drawer(frame, gamestate._1)
    gamestate = changePlayer(gamestate)
  }
}

def sudokuGameEngine(initialState:Array[Array[(Char,Boolean)]],controller:(String,(Array[Array[(Char,Boolean)]]))=>(Array[Array[(Char,Boolean)]]),drawer:(JFrame,Array[Array[(Char,Boolean)]])=>JFrame)={
  var state = initialState
  var frame = new JFrame()
  frame = drawer(frame, state)
  while (true) {
    println(">>enter the [row column value] to add a value\n" +
      ">>put them in that order [row column value]")
    val input = readLine()
    state = controller(input, state)
    frame = drawer(frame, state)
  }
}

def changePlayer(state:(Array[Array[Char]],Boolean)):(Array[Array[Char]],Boolean)={
  if(state._2){
    println("player2 turn")
  }else{
    println("player1 turn")
  }
  (state._1,!state._2)
}

