object GameEngine {
  def getController(number :Int):Controller = number match {
    case 0 => new ChessController()
    case 1 => new CheckersController()
  }
  def main(args: Array[String]): Unit = {
    val XXX:Controller = getController(1)
    val state = XXX.stateInit()
  }
}
