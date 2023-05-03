trait Controller {
  def stateInit():(Array[Array[Char]],Any)
  def validateInput(input:String,state:(Any,Any)):Boolean
  def applyAction(input:String,state:(Any,Any)):(Any,Any)
}
