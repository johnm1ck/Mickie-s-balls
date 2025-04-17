package model;

public abstract class Character extends GameObject {
    protected int maxHp;
    protected int currentHp;
    protected double moveSpeed;
    protected boolean isMovingUp;
    protected boolean isMovingDown;
    
    public Character(double x, double y, double width, double height, int maxHp, double moveSpeed) {
        super(x, y, width, height);
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.moveSpeed = moveSpeed;
        this.isMovingUp = false;
        this.isMovingDown = false;
    }
    
    @Override
    public void update() {
        if (isMovingUp && y > 0) {
            y -= moveSpeed;
        }
        if (isMovingDown && y < 600 - height) {
            y += moveSpeed;
        }
    }
    
    public void moveUp() {
        isMovingUp = true;
        isMovingDown = false;
    }
    
    public void moveDown() {
        isMovingDown = true;
        isMovingUp = false;
    }
    
    public void stopMoving() {
        isMovingUp = false;
        isMovingDown = false;
    }
    
    public abstract KiBlast shootKiBlast();
    
    public void takeDamage() {
        currentHp--;
    }
    
    public boolean isDefeated() {
        return currentHp <= 0;
    }
    
    // Getters
    public int getMaxHp() {
        return maxHp;
    }
    
    public int getCurrentHp() {
        return currentHp;
    }
}