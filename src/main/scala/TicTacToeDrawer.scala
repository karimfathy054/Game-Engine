import javax.swing._
import java.awt._
import java.io.File
import javax.imageio.ImageIO
class TicTacToeDrawer extends Drawer {
  override def draw(frame: JFrame, board: Array[Array[Char]]): Unit = {
    frame.setSize(800, 800)
    frame.setTitle("chess")
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

  }

  def getGamePieces(row: Int, column: Int, board: Array[Array[Char]]): ImageIcon = {
    board(row)(column) match {
      case 'O' => {
        val image = ImageIO.read(new File("src/main/scala/tic-tac-toe-images/O-removebg-preview.png")).getScaledInstance(256, 256, Image.SCALE_DEFAULT)
        new ImageIcon(image)
      }
      case 'X' => new ImageIcon("src/main/scala/tic-tac-toe-images/X-removebg-preview.png")
      case '-' => null
    }

  }
}
