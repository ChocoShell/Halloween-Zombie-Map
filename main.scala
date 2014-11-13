//Reddit Daily Programmer - Halloween Challenge #2 - The Coding Dead

import scala.collection.mutable.ArrayBuffer

object Base {
  def main(args: Array[String]) {
    // Map is 20x20, meaning 400 spots.
    var area = Array.ofDim[String](20, 20)
    for(x <- 0 to 19; y <- 0 to 19) {
      area(x)(y) = "_"
    }
    val rnd = new scala.util.Random

    // Get empty points from the map
    def getEmptyPoints(area : Array[Array[String]]) = {
      for{
        x <- 0 to (area.length -1); 
        y <- 0 to (area(0).length -1) 
        if area(x)(y) == "_"
      } yield (x,y)
    }

    def populateMap(things: Int, symbol: String, 
                    area: Array[Array[String]],
                    emptyPoints: IndexedSeq[(Int, Int)]
                    ): Array[Array[String]] = {
      if(things == 0 || emptyPoints.isEmpty)
        area
      else
        populateMap(things - 1, symbol, area(x) update(y, symbol),
                    emptyPoints diff Seq(emptyPoints(randomPoint))
                   )
    }

    // If we have more than 400 guys, we retry the function.
    def generateGuys(): (Int, Int, Int) = {
      val range = 0 to 200
      val first = range(rnd.nextInt(range length))
      val second = range(rnd.nextInt(range length))
      val third = range(rnd.nextInt(range length))
      if(first + second + third > 400)
        generateGuys()
      else 
        (first, second, third)
    }

    val (zombies, hunters, victims) = generateGuys()

    area = populateMap(zombies, "Z", area, getEmptyPoints(area))
    area = populateMap(hunters, "H", area, getEmptyPoints(area))
    area = populateMap(victims, "V", area, getEmptyPoints(area))

    println(area.deep.mkString("\n"))

    // Check for empty spaces around a zombie
    def getZombieEmptySpaces(x : Int, y : Int, area : Array[Array[String]]) = {
      val xrange = if(x == 0) 0 to 1 else if (x == 19) 18 to 19 else x-1 to x+1
      val yrange = if(y == 0) 0 to 1 else if (y == 19) 18 to 19 else y-1 to y+1
      //(x == newx || y == newy) makes sure that zombies either move in the x or y direction not both
      for{
        newx <- xrange; 
        newy <- yrange 
        if area(x)(y) == "_" && (x == newx || y == newy)
      } yield (newx, newy)
    }

    // Check for empty spaces around a human
    def getHumanEmptySpaces(x : Int, y : Int, area : Array[Array[String]]) = {
      val xrange = if(x == 0) 0 to 1 else if (x == 19) 18 to 19 else x-1 to x+1
      val yrange = if(y == 0) 0 to 1 else if (y == 19) 18 to 19 else y-1 to y+1

      for{
        newx <- xrange;
        newy <- yrange 
        if area(x)(y) == "_"
      } yield (newx, newy)
    }    

    val ticks = 100

    def zombieMovement(x : Int, y : Int, area : Array[Array[String]]) = {
      // Zombies move up, down, left or right 1 space, not diagonally.
      // First, check available spaces.
      getZombieEmptySpaces(x, y, area)
      val newx = xrange(rnd.nextInt(xrange length))
      val newy = yrange(rnd.nextInt(yrange length))
    }
  }
}