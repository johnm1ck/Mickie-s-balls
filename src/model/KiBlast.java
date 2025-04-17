package model;

public class KiBlast extends GameObject {
    protected int direction; // 1 for right, -1 for left
    protected double speed = 10.0;
    
    public KiBlast(double x, double y, int direction) {
        super(x, y, 20, 10);
        this.direction = direction;
        try {
            // Load sprite from resources
            sprite = new javafx.scene.image.Image(getClass().getResourceAsStream("/resource/kiblast.png"));
        } catch (Exception e) {
            System.out.println("Could not load ki blast sprite: " + e.getMessage());
        }
    }
    
    @Override
    public void update() {
        x += direction * speed;
    }
    
    public int getDirection() {
        return direction;
    }
}