import javax.swing.JFrame

trait Drawer {
  def draw(frame:JFrame,board:Array[Array[Char]]):Unit
}
