package model;

public abstract class Hero {
    private String name;
    private int damage;
    private int health;
    
    public Hero(String name, int damage, int health) {
        this.name = name;
        this.damage = damage;
        this.health = health;
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

    public abstract void attack(Enemy enemy);
}
