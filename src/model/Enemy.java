package model;

public abstract class Enemy extends Character {
    
    public Enemy(double x, double y, double width, double height, int maxHp) {
        super(x, y, width, height, maxHp, 3.0);
    }
    
    @Override
    public KiBlast shootKiBlast() {
        return new KiBlast(x + width, y + height/2, 1); // Right direction toward player
    }
    
    public void takeDamage(int damage) {
        currentHp -= damage;
        if (currentHp < 0) currentHp = 0;
    }
}