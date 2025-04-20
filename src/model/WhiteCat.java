package model;

import base.BlasterState;
import base.MainCharacter;
import base.Transformable;
import javafx.scene.image.Image;

public class WhiteCat extends MainCharacter implements Transformable {
    private static final int CAT_MAX_HP = 10;    
    public WhiteCat(double x, double y) {
        super(x, y, bodySize, bodySize, CAT_MAX_HP);
        this.isSuperSaiyan = false;
        this.damage = 1;
        this.kiSpeed = 13;
        
        try {
            // Load sprite from resources
            this.sprite = new Image("file:resource/whitecat.png");
        } catch (Exception e) {
            System.out.println("Could not load white cat sprite: " + e.getMessage());
        }
    }
    
    // Right direction
    @Override
    public KiBlast shootKiBlast(int damage, double speed) {
    	BlasterState s = this.isSuperSaiyan ? BlasterState.SUPER_MAIN : BlasterState.NORM_MAIN;
        return new KiBlast(x + width, y + height/2, 1, damage, speed, s);
    }

    @Override
    public void boom() {
    	super.boom();
		this.isSuperSaiyan = true;
		this.damage = 2;
		this.kiSpeed = 19;
		this.moveSpeed = 6.7;
		try {
            // Load sprite from resources
			this.sprite = new Image("file:resource/superwhitecat.png");
        } catch (Exception e) {
            System.out.println("Could not load white cat sprite: " + e.getMessage());
        }
	}
    
    @Override
    public double getKiSpeed() {
    	return this.kiSpeed;
    }
    
    @Override
    public int getDamage() {
    	return this.damage;
    }
	
}