trait Controller {
  def stateInit():(Array[Array[Char]],Boolean)
  def validateInput(input:String,state:(Array[Array[Char]],Boolean)):Boolean
  def applyAction(input:String,state:(Array[Array[Char]],Boolean)):(Array[Array[Char]],Boolean)
}
