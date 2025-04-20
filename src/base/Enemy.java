package base;

import model.KiBlast;

public abstract class Enemy extends Character {
    
    public Enemy(double x, double y, double width, double height, int maxHp, double moveSpeed) {
        super(x, y, width, height, maxHp, moveSpeed);
    }
    
    public void takeDamage(int damage) {
        currentHp -= damage;
        if (currentHp < 0) currentHp = 0;
    }
    
    public abstract KiBlast shootKiBlast();
}