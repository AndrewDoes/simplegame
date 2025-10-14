package factories;

import model.Enemy;

public class EnemyFactory  {
    public Enemy createEnemy(String name, String dungeon, int level, int health, int damage) {
        return new Enemy(name,dungeon , level, health, damage);
    }
}
