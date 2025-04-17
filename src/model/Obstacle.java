package model;

public abstract class Obstacle extends GameObject {
    protected int hitPoints;
    protected int maxHitPoints;
    protected int pointValue;
    protected double speed;
    
    public Obstacle(double x, double y, double width, double height, int hitPoints, int pointValue, double speed) {
        super(x, y, width, height);
        this.hitPoints = hitPoints;
        this.maxHitPoints = hitPoints;
        this.pointValue = pointValue;
        this.speed = speed;
    }
    
    @Override
    public void update() {
        x -= speed;
    }
    
    public void takeDamage() {
        hitPoints--;
    }
    
    public boolean isDestroyed() {
        return hitPoints <= 0;
    }
    
    public int getPoints() {
        return pointValue;
    }
}