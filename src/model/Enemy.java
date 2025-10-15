package model;

public class Enemy implements Cloneable{
    private String name;
    private int level;
    private int damage = 10;
    private int health = 50;
    private String dungeon;

    private Enemy(EnemyBuilder builder) {
        this.level = builder.level;
        this.name = builder.name;
        this.dungeon = builder.dungeon;
        this.damage = builder.damage;
        this.health = builder.health;
    }

    public Enemy(String name, String dungeon, int level, int health, int damage) {
        this.name = name;
        this.level = level;
        this.damage = damage;
        this.health = health;
        this.dungeon = dungeon;
    }

    public static class EnemyBuilder{
        private String name;
        private int level;
        private int damage = 10;
        private int health = 50;
        private String dungeon;

        public EnemyBuilder(String name){
            this.name = name;
        }
        public EnemyBuilder setLevel(int level){
            this.level = level;
            return this;
        }
        public EnemyBuilder setDamage(int damage){
            this.damage = damage;
            return this;
        }
        public EnemyBuilder setHealth(int health){
            this.health = health;
            return this;
        }
        public EnemyBuilder setDungeon(String dungeon){
            this.dungeon = dungeon;
            return this;
        }
        public Enemy build(){
            return new Enemy(this);
        }
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
    @Override
    public Object clone() throws CloneNotSupportedException {
        return (Enemy) super.clone();
    }

    @Override
    public String toString(){
        return "Enemy name: " + this.getName() + " | Enemy dungeon: " + this.getDungeon()
                        + " | Enemy Level: " + this.getLevel() + " | HP: " + this.getHealth();
    }
}
