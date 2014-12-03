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
