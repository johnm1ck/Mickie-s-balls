package model;

import application.ImageManager;
import base.BlasterState;
import base.Enemy;
import javafx.scene.image.Image;

public class UltraDog extends Enemy {
    private static final int ULTRA_DOG_MAX_HP = 100;
    
    public UltraDog(double x, double y) {
        super(x, y, 108, 108, ULTRA_DOG_MAX_HP, 6.9);
        try {
            // Load sprite from resources
        	this.sprite = new Image(ImageManager.ULTRADOG_URL);
        } catch (Exception e) {
            System.out.println("Could not load ultra dog sprite: " + e.getMessage());
        }
    }
    
    @Override
    public KiBlast shootKiBlast() {
        return new KiBlast(x + width, y + height/2, 1, 2, 13, BlasterState.ULTRA_ENEMY); // Right direction toward player
    }
}