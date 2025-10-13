package baseline.model;

public abstract class Hero {
    private String name;
    private int damage;
    private int health;
    private int level;
    
    public Hero(String name, int damage, int health) {
        this.name = name;
        this.damage = damage;
        this.health = health;
        this.level = 1;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isAlive(){
        return health > 0;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public abstract void attack(Enemy enemy);
    public abstract String toString();
}
