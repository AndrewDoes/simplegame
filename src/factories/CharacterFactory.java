package  factories;

public interface CharacterFactory {
    HeroFactory getHeroFactory(int type);
    EnemyFactory getEnemyFactory();
}
