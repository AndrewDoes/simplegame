package factories;

import model.Enemy;

public class EnemyFactory{
    public Enemy createEnemy(String name, String dungeon, int level, int damage, int health) {
        return new Enemy(name, dungeon, level, damage, health);
    }
}
