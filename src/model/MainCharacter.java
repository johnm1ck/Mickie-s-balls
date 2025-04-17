package model;

public abstract class MainCharacter extends Character {
    
    public MainCharacter(double x, double y, double width, double height, int maxHp) {
        super(x, y, width, height, maxHp, 5.0);
    }
    
    @Override
    public KiBlast shootKiBlast() {
        return new KiBlast(x + width, y + height/2, 1);  // Right direction
    }
    
    public SpecialKiBlast shootSpecialKiBlast(int damage) {
        return new SpecialKiBlast(x, y + height/2, -1, damage);  // Left direction
    }
}