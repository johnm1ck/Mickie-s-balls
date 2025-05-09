package model;

import application.MediaManager;
import base.BlasterState;
import base.Enemy;
import base.Transformable;
import javafx.scene.image.Image;

public class SuperDog extends Enemy implements Transformable {
    private static final int SUPER_DOG_MAX_HP = 69;
    private static final int SUPER_DOG_BODY_SIZE = 93;
    private static final double SUPER_DOG_MOVE_SPEED = 2.3;
    
    public SuperDog(double x, double y) {
        super(x, y, SUPER_DOG_BODY_SIZE, SUPER_DOG_BODY_SIZE, SUPER_DOG_MAX_HP, SUPER_DOG_MOVE_SPEED);
        try {
            // Load sprite from resources
            this.sprite = new Image(MediaManager.SUPERDOG_URL);
        } catch (Exception e) {
            System.out.println("Could not load super dog sprite: " + e.getMessage());
        }
    }
    
    @Override
    public KiBlast shootKiBlast() {
        return new KiBlast(x + width, y + height/2, 1, 1, 11, BlasterState.NORM_ENEMY); // Right direction toward player
    }

	public void boom() {
		
	}
}