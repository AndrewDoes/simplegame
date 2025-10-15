package factories;


public class StandardCharacterFactory implements CharacterFactory {
    @Override
    public HeroFactory getHeroFactory(int type) {
        switch (type) {
            case 1:
                return new ArcherFactory();
            case 2:
                return new FighterFactory();
            default:
                return null;
        }
    }
}
