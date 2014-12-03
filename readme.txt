Reddit Daily Programmer - Halloween Challenge #2 - The Coding Dead
http://www.reddit.com/r/dailyprogrammer/comments/2kwfqr/10312014_challenge_186_special_code_or_treat/

Zombie lore has been very popular in the recent years. We are entertained by the stories of the dead coming back to life as a zombie and the struggle of human to survive the zombie horde. In Zombie lore it is common that if you are bitten by a zombie you become a zombie. This behavior works much like a plague. The zombie grow in numbers and the living must deal with them by usually do a fatal wound to the zombie's brain/spinal cord.

We will explore this plague of zombies by creating zombie simulator. This simulator will randomly populate a map and have 3 types of entities: Zombies, Victims and hunters.

    Zombies -- The walking dead back to life. They will roam looking to bite victims to turn them into zombies.
    Victims -- Innocent humans who are not trained in Zombie lore and have no skills or knowledge to fight back.
    Hunters -- Trained humans in Zombie lore who seek out to destroy Zombies to save the planet.

Specs:
There is a 20x20 map with zombies, hunters, and victims that are randomly generated.
Because of the map size, the number of zombies, hunters, and victims combined must not exceed 400.

Entity Behavior and Order:
  Zombie moves if able
  Hunter moves if able
  Hunter kills at most 2 zombies that they are 1 space away from
  Victim checks for zombie(all directions) and moves in a random direction if one is 1 space away
  Zombie eats a near human - 1 space away, not diagonally.  This turns them into a zombie.
