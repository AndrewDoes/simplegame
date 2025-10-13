package model;

public class Fighter extends Hero {
    public Fighter(String name){
        super(name, 20, 300);
    }
    @Override
    public void attack(Enemy enemy) {
        System.out.println(this.getName() + "slashed the enemy with " + this.getDamage() + "damage!");
        enemy.setHealth(enemy.getHealth()-this.getDamage());
    }
}
