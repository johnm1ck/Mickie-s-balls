package model;

public class SpecialKiBlast extends KiBlast {
    private int damage;
    
    public SpecialKiBlast(double x, double y, int direction, int damage) {
        super(x, y, direction);
        this.damage = damage;
        this.width = 30; // Bigger than regular ki blast
        this.height = 15;
        try {
            // Load sprite from resources
            sprite = new javafx.scene.image.Image(getClass().getResourceAsStream("/resource/specialkiblast.png"));
        } catch (Exception e) {
            System.out.println("Could not load special ki blast sprite: " + e.getMessage());
        }
    }
    
    public int getDamage() {
        return damage;
    }
}