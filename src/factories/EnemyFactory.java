package factories;

import model.Enemy;

public class EnemyFactory  {
    public Enemy createEnemy(String name, String dungeon, int level, int health, int damage) {
        return new Enemy.EnemyBuilder(name)
        .setDungeon(dungeon)
        .setLevel(level)
        .setHealth(health)
        .setDamage(damage)
        .build();
    }
}
