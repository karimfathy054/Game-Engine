import java.awt._
import javax.swing._
class SudokuDrawer extends Drawer{
  override def draw(frame: JFrame, board: Array[Array[Char]]): JFrame = {
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
            cell.setText(board(y + 3 * i)(x + 3 * j).toString)
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
}
