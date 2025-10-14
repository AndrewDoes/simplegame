package model;

public class Enemy {
    private String name;
    private int level;
    private int damage = 10;
    private int health = 50;
    private String dungeon;

    public Enemy(String name, String dungeon, int level, int damage, int health) {
        this.level = level;
        this.name = name;
        this.dungeon = dungeon;
        this.damage = damage;
        this.health = health;
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
        System.out.println("Enemy attacked " + hero.getName() + " with " + this.damage + " damage!");
        hero.setHealth(hero.getHealth() - this.damage);
    }

        public boolean isAlive(){
        return health > 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDungeon() {
        return dungeon;
    }

    public void setDungeon(String dungeon) {
        this.dungeon = dungeon;
    }

    public String toString(){
        return "Enemy name: " + this.getName() + " | Enemy dungeon: " + this.getDungeon()
                        + " | Enemy Level: " + this.getLevel() + " | HP: " + this.getHealth();
    }
}
