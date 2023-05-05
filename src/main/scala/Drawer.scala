import javax.swing.JFrame

trait Drawer {
  def draw(inFrame:JFrame,board:Array[Array[Char]]):Unit
}
