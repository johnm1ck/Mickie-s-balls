package model;

import base.BlasterState;
import base.Enemy;
import base.Transformable;
import javafx.scene.image.Image;

public class SuperDog extends Enemy implements Transformable {
    private static final int SUPER_DOG_MAX_HP = 69;
    
    public SuperDog(double x, double y) {
        super(x, y, 93, 93, SUPER_DOG_MAX_HP, 2.3);
        try {
            // Load sprite from resources
            this.sprite = new Image("file:resource/superdog.png");
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