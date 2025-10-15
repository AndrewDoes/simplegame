package model;

public class Fighter extends Hero{
    public Fighter(String name){
        super(name, 20, 400);
    }
    @Override
    public void attack(Enemy enemy) {
        System.out.println(this.getName() + " slashed the enemy with " + this.getDamage() + " damage!");
        enemy.setHealth(enemy.getHealth()-this.getDamage());
    }
    @Override
    public String toString() {
        return "Hero " + this.getName() + " | HP: " + this.getHealth() + "| Damage: " + this.getDamage();
    }
}
