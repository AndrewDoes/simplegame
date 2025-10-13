package baseline.model;

public class Enemy {
    private int level;
    private int damage = 10;
    private int health = 50;

    public Enemy(int level) {
        this.level = level;
        this.damage = damage*level;
        this.health = health*level;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }  

    public void attack (Hero hero) {
        hero.setHealth(hero.getHealth() - this.damage);
    }
}
