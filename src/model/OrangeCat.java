package model;

import base.MainCharacter;
import base.Transformable;
import javafx.scene.image.Image;

public class OrangeCat extends MainCharacter implements Transformable {
    private static final int CAT_MAX_HP = 10;
    private long BLASTING_SPEED;
    
    public OrangeCat(double x, double y) {
        super(x, y, bodySize, bodySize, CAT_MAX_HP);
        this.isSuperSaiyan = false;
        this.BLASTING_SPEED = 291_000_000;
        this.damage = 1;
        this.kiSpeed = 13;
        
        try {
            // Load sprite from resources
            this.sprite = new Image("file:resource/orangecat.png");
        } catch (Exception e) {
            System.out.println("Could not load orange cat sprite: " + e.getMessage());
        }
    }
    
    @Override
	public void boom() {
    	super.boom();
		this.isSuperSaiyan = true;
		this.BLASTING_SPEED = 135_000_000;
		this.moveSpeed = 6.7;
		try {
            // Load sprite from resources
			this.sprite = new Image("file:resource/superorangecat.png");
        } catch (Exception e) {
            System.out.println("Could not load white cat sprite: " + e.getMessage());
        }
	}
	
	public long getBlastingSpeed() {
		return BLASTING_SPEED;
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