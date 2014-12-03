Reddit Daily Programmer - Halloween Challenge #2 - The Coding Dead
http://www.reddit.com/r/dailyprogrammer/comments/2kwfqr/10312014_challenge_186_special_code_or_treat/

There is a 20x20 map with zombies, hunters, and victims that are randomly generated.
Because of the map size, the number of zombies, hunters, and victims combined must not exceed 400.

Entity Behavior and Order:
  Zombie moves if able
  Hunter moves if able
  Hunter kills at most 2 zombies that they are 1 space away from
  Victim checks for zombie(all directions) and moves in a random direction if one is 1 space away
  Zombie eats a near human - 1 space away, not diagonally.  This turns them into a zombie.

TODO
-------
Idea Suggestions
  Make victims into hunters if they survive zombie encounters
  Decide on Human v Human interactions (Groups? Enemies?)
  Periodically eliminate people (due to starving, etc)
  Periodically eliminate zombies faster than humans (due to weather, etc)

Refactoring Suggestions
  Make zombieMap class with the functions as methods for the class.
  Split up zombieMap and game loop into different files.

Need to add data collection:
  Stumble Unit Data
    Zombie Stumble Units - number of spots zombies moved too
    Victim Flee Units - number of spots victims moved too
    Hunter Seek Units - number of spots hunters moved too.

  Population and Event Data 
    Number of "Single" kills by hunter (killing only 1 zombie a turn)
    Number of "Double" kills by a hunter (killing 2 zombies a turn)
    Total zombies killed by Hunters
    Number of Victims bitten
    Number of Hunters bitten
    Total number of non-zombies bitten

  Final (Copied directly from Reddit)
    With all this data we can compute a decay rate. Either the zombie 
    population is decaying or the non-zombie population is decaying. If the 
    decay difference is within 5 then the population is a balance. So for 
    example if 100 zombies are killed but 95 are created it is a balance. (The
    difference between killed zombies and bites was 5 or less) However if the 
    decay difference is more than 5 in favor of bites the Zombies Win. If the 
    decay difference is more than 5 in favor of the Hunters then the Humans win.

    You will decide who wins the simulation. Humans, Zombies or a tie.

Optional Things/Tidying Up
  Print map nicer
