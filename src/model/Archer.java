package model;

public class Archer extends Hero {
    private int arrows;
    public Archer(String name) {
        super(name, 40, 100);
        this.arrows = 10;
    }
    @Override
    public void attack(Enemy enemy) {
        System.out.println(this.getName() + "shot the enemy with " + this.getDamage() + "damage! (" + this.getArrows() + " arrows left)");
        enemy.setHealth(enemy.getHealth()-this.getDamage());
        this.arrows-=1;
    }
    public int getArrows() {
        return arrows;
    }
    public void setArrows(int arrows) {
        this.arrows = arrows;
    }
}
