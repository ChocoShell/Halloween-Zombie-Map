class zombieMap(length: Int, height: Int) {
  var area = Array.ofDim[String](length, height)
  
  for(x <- 0 until length; y <- 0 until height) {
    area(x)(y) = "_"
  }

  def getEmptyPoints = {
    for{
      x <- 0 to (area.length -1); 
      y <- 0 to (area(0).length -1) 
      if area(x)(y) == "_"
    } yield (x,y)
  }

  def print = println(area.deep.mkString("\n"))
}