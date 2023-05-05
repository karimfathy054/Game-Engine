import java.awt._
import javax.swing._

class ChessDrawer {
  def draw(frame:JFrame , board:Array[Array[Char]]):Unit={
    frame.setSize(800, 800)
    frame.setTitle("chess")
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
    frame.add(indexCol, BorderLayout.WEST)
    frame.add(chessBoard, BorderLayout.CENTER)
    frame.add(indexRow, BorderLayout.SOUTH)

    frame.setVisible(true)
  }

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
}
