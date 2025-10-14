package factories;

import model.Archer;
import model.Hero;

public class ArcherFactory implements HeroFactory {
    @Override
    public Hero createHero(String name) {
        return new Archer(name);
    }
}
