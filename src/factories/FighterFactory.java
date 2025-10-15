package factories;

import model.Fighter;
import model.Hero;

public class FighterFactory implements HeroFactory {
    @Override
    public Hero createHero(String name) {
        return new Fighter(name);
    }
}
