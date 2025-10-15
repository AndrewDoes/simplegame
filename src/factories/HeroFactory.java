package factories;

import model.Hero;

public interface  HeroFactory {
    public abstract Hero createHero(String name);
}