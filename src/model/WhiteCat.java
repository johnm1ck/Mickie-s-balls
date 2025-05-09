package model;

import application.MediaManager;
import base.MainCharacter;
import base.Transformable;
import javafx.scene.image.Image;

public class WhiteCat extends MainCharacter implements Transformable { 
	private static final int WHITE_CAT_MAX_HP = 10;
	private static final int NORMAL_DAMAGE = 1;
	private static final int SUPER_DAMAGE = 2;
	private static final int NORMAL_KI_SPEED = 13;
	private static final int SUPER_KI_SPEED = 19;
	
    public WhiteCat(double x, double y) {
        super(x, y, BODY_SIZE, BODY_SIZE, WHITE_CAT_MAX_HP);
        this.isSuperSaiyan = false;
        this.damage = NORMAL_DAMAGE;
        this.kiSpeed = NORMAL_KI_SPEED;
        
        try {
            // Load sprite from resources
            this.sprite = new Image(MediaManager.WHITECAT_URL);
        } catch (Exception e) {
            System.out.println("Could not load white cat sprite: " + e.getMessage());
        }
    }

    @Override
    public void boom() {
    	super.boom();
		this.isSuperSaiyan = true;
		this.damage = SUPER_DAMAGE;
		this.kiSpeed = SUPER_KI_SPEED;
		try {
            // Load sprite from resources
			this.sprite = new Image(MediaManager.SUPERWHITECAT_URL);
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