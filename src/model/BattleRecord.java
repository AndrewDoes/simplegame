package model;

public class BattleRecord {
    Enemy enemy;
    Hero hero;

    public BattleRecord(Enemy enemy, Hero hero) {
        this.enemy = enemy;
        this.hero = hero;
    }

    public void printBattleRecord() {
        System.out.println("Battle Record:");
        System.out.println("Enemy: " + enemy.toString());
        System.out.println("Hero: " + hero.toString());
    }
}
