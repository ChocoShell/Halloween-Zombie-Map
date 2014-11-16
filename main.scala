// Reddit Daily Programmer - Halloween Challenge #2 - The Coding Dead
// http://www.reddit.com/r/dailyprogrammer/comments/2kwfqr/10312014_challenge_186_special_code_or_treat/

/* There is a 20x20 map with zombies, hunters, and victims that are randomly generated.
   Entity Behavior:
   Zombie moves
   Hunter moves
   Hunter kills at most 2 zombies that they are 1 space away from
   Victim checks for zombie(all directions) and moves in a random direction if one is 1 space away
   Zombie eats a near human - 1 space away, not diagonally.  This turns them into a zombie.
*/
object Base {
  def main(args: Array[String]) {
    
    // RNG to determine random events like movement, zombie kills, and bites.
    val rnd = new scala.util.Random

    // Map is 20x20, meaning 400 spots.
    var area = Array.ofDim[String](20, 20)
    for(x <- 0 to 19; y <- 0 to 19) {
      area(x)(y) = "_"
    }

    // Map is a string with different symbols representing different Entities
    val zombieChar = "Z"
    val hunterChar = "H"
    val victimChar = "V"

    // Get empty points from the map
    def getEmptyPoints(area : Array[Array[String]]) = {
      for{
        x <- 0 to (area.length -1); 
        y <- 0 to (area(0).length -1) 
        if area(x)(y) == "_"
      } yield (x,y)
    }

    // Populate map with a symbol(Entity) by selecting randomly from the array of empty points.
    def populateMap(things: Int, symbol: String, area: Array[Array[String]], emptyPoints: IndexedSeq[(Int, Int)]): Array[Array[String]] = {
      if(things == 0 || emptyPoints.isEmpty)
        area
      else {
        val (x, y) = emptyPoints(rnd.nextInt(emptyPoints.length))
        area(x) update(y, symbol)
        populateMap(things - 1, symbol, area, emptyPoints diff Seq((x,y)))
      }
    }

    // Generate number of entities
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

    // Check for empty spaces around a zombie
    def getZombieEmptySpaces(x : Int, y : Int, area : Array[Array[String]]): IndexedSeq[(Int, Int)] = {
      val xrange = if(x == 0) 0 to 1 else if (x == 19) 18 to 19 else x-1 to x+1
      val yrange = if(y == 0) 0 to 1 else if (y == 19) 18 to 19 else y-1 to y+1
      //(x == newx || y == newy) makes sure that zombies either move in the x or y direction not both
      for{
        newx <- xrange; 
        newy <- yrange 
        if area(newx)(newy) == "_" && (x == newx || y == newy)
      } yield (newx, newy)
    }

    // Check for empty spaces around a human
    def getHumanEmptySpaces(x : Int, y : Int, area : Array[Array[String]]) = {
      val xrange = if(x == 0) 0 to 1 else if (x == 19) 18 to 19 else x-1 to x+1
      val yrange = if(y == 0) 0 to 1 else if (y == 19) 18 to 19 else y-1 to y+1
      for{
        newx <- xrange;
        newy <- yrange 
        if area(newx)(newy) == "_"
      } yield (newx, newy)
    }    

    // Find indices(coordinates) containing a certain symbol in area and return them
    def findThings(symbol: String, area: Array[Array[String]]): IndexedSeq[(Int, Int)] = {
      (for{
        (row, i) <- area.iterator.zipWithIndex
        (thing, j) <- row.iterator.zipWithIndex
        if( thing == symbol )
      } yield (i, j)).toIndexedSeq
    }

    // Take position of one entity and select an empty space randomly
    def movement(x : Int, y : Int, symbol: String, area : Array[Array[String]], emptySpaces: IndexedSeq[(Int, Int)]) = {
      // Zombies move up, down, left or right 1 space, not diagonally.
      // Hunters and victims can move anywhere 1 space, including diagonally.
      // First, check available spaces.
      if (!(emptySpaces.isEmpty)){
        val (newx, newy) = emptySpaces(rnd.nextInt(emptySpaces length))
        area(x)(y) = "_"
        area(newx)(newy) = symbol
      }
    }

    // Hunter + Victim function - Find zombies surrounding (x,y) in area
    def getNearbyZombies(x: Int, y: Int, area: Array[Array[String]]) = {
      val xrange = if(x == 0) 0 to 1 else if (x == 19) 18 to 19 else x-1 to x+1
      val yrange = if(y == 0) 0 to 1 else if (y == 19) 18 to 19 else y-1 to y+1
      for{
        newx <- xrange;
        newy <- yrange 
        if area(newx)(newy) == zombieChar
      } yield (newx, newy)
    }

    // Zombie Function - Find humans 1 square away from (x,y) in area but not diagonally
    def getNearbyHumans(x: Int, y: Int, area: Array[Array[String]]) = {
      val xrange = if(x == 0) 0 to 1 else if (x == 19) 18 to 19 else x-1 to x+1
      val yrange = if(y == 0) 0 to 1 else if (y == 19) 18 to 19 else y-1 to y+1
      //(x == newx || y == newy) makes sure that zombies either move in the x or y direction not both
      for{
        newx <- xrange; 
        newy <- yrange 
        if (area(newx)(newy) == victimChar || area(newx)(newy) == hunterChar) && (x == newx || y == newy)
      } yield (newx, newy)
    }

    // Hunter function - Find surrounding zombies and replace them with empty space
    def checkAndKillZombies(x: Int, y: Int, area: Array[Array[String]]) {
      val zombies = getNearbyZombies(x, y, area)
      def killZombiesHelper(zombies: IndexedSeq[(Int, Int)], numToKill: Int): Array[Array[String]] = {
        if(numToKill <= 0 || zombies.isEmpty) 
          area
        else {
          val (x, y) = zombies(rnd.nextInt(zombies length))
          area(x)(y) = "_"
          killZombiesHelper(zombies diff Seq((x,y)), numToKill-1)
        }
      }
      killZombiesHelper(zombies, 2)
    }

    // Zombie function - Turn human into zombie
    def checkAndEatHuman(x: Int, y: Int, area: Array[Array[String]]) {
      val humans = getNearbyHumans(x, y, area)
      if (!humans.isEmpty) {
        val (eatX, eatY) = humans(rnd.nextInt(humans length))
        area(eatX)(eatY) = zombieChar
      }
    }

    def gameLoop(ticks: Int): Unit = {
      if(ticks > 0) {
        for((x,y) <- findThings(zombieChar, area)) {
          // Zombies move
          movement(x, y, zombieChar, area, getZombieEmptySpaces(x, y, area))
        }
        for((x,y) <- findThings(hunterChar, area)) {
          // Hunters move
          movement(x, y, hunterChar, area, getHumanEmptySpaces(x, y, area))
          // Hunters kill zombies
          checkAndKillZombies(x, y, area)
        }
        for((x,y) <- findThings(victimChar, area)) {
          // Victims check if they are near a zombie
          if(!getNearbyZombies(x, y, area).isEmpty){
            // Victims move randomly
            movement(x, y, victimChar, area, getHumanEmptySpaces(x, y, area))
          }
        }
        for((x,y) <- findThings(zombieChar, area)) {
          // Zombies eat random human if next to one, but not diagonally.
          checkAndEatHuman(x, y, area)
        }
        gameLoop(ticks - 1)
      }
    }

    // Generate number of entities
    val (zombies, hunters, victims) = generateGuys()

    // Number of times to run game loop
    val ticks = 100

    // Populate map with zombies, hunters, and victims
    populateMap(zombies, zombieChar, area, getEmptyPoints(area))
    populateMap(hunters, hunterChar, area, getEmptyPoints(area))
    populateMap(victims, victimChar, area, getEmptyPoints(area))

    println("Initial State of Map")
    println(area.deep.mkString("\n"))

    gameLoop(ticks)
    println
    println("Final State of Map")
    println(area.deep.mkString("\n"))
    println
    println("Starting Stats")
    println("Zombies: " + zombies)
    println("Hunters: " + hunters)
    println("Victims: " + victims)
    println
    println("Ending Stats")
    println("Zombies: " + (findThings(zombieChar, area) length))
    println("Hunters: " + (findThings(hunterChar, area) length))
    println("Victims: " + (findThings(victimChar, area) length))
  }
}