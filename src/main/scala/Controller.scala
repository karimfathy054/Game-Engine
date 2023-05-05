abstract class Controller {
  def stateInit():(Array[Array[Char]],Boolean)
  def EightQStateInit():(Array[Array[Char]], List[(Int, Int)])
  def EightQvalidateInput(input:String,state:(Array[Array[Char]],List[(Int, Int)])):Boolean
  def EightQapplyAction(input:String,state:(Array[Array[Char]],List[(Int, Int)])):(Array[Array[Char]],List[(Int, Int)])

  def SudokuStateInit():Array[Array[Char]]
  def SudokuvalidateInput(input:String,state:(Array[Array[Char]])):Boolean
  def SudokuapplyAction(input:String,state:(Array[Array[Char]])):(Array[Array[Char]])


  def validateInput(input:String,state:(Array[Array[Char]],Boolean)):Boolean
  def applyAction(input:String,state:(Array[Array[Char]],Boolean)):(Array[Array[Char]],Boolean)
}
