import java.awt._
import javax.swing._
class Connect4Drawer extends Drawer{
  override def draw(frame: JFrame, board: Array[Array[Char]]): JFrame = {
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

  def getGamePiece(row: Int, column: Int, board: Array[Array[Char]]): ImageIcon = {
    board(row)(column) match {
      case 'R' => new ImageIcon("src/main/scala/Connect4Images/red-circle.png")
      case 'Y' => new ImageIcon("src/main/scala/Connect4Images/yellow-circle.png")
      case '-' => new ImageIcon("src/main/scala/CheckerImages/WHITE.png")
    }
  }

}
