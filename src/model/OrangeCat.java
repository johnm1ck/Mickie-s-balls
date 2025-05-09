package model;

import application.MediaManager;
import base.MainCharacter;
import base.Transformable;
import javafx.scene.image.Image;

public class OrangeCat extends MainCharacter implements Transformable {
    private long blastingPeriod;
    private static final long START_BLASTING_PERIOD = 284_000_000;
    private static final long SUPER_BLASTING_PERIOD = 125_000_000;
    private static final int DAMAGE = 1;
    private static final double KI_SPEED = 13;
    private static final int ORANGE_CAT_MAX_HP = 10;
    
    public OrangeCat(double x, double y) {
        super(x, y, BODY_SIZE, BODY_SIZE, ORANGE_CAT_MAX_HP);
        this.isSuperSaiyan = false;
        this.blastingPeriod = START_BLASTING_PERIOD;
        this.damage = DAMAGE;
        this.kiSpeed = KI_SPEED;
        
        try {
            // Load sprite from resources
            this.sprite = new Image(MediaManager.ORANGECAT_URL);
        } catch (Exception e) {
            System.out.println("Could not load orange cat sprite: " + e.getMessage());
        }
    }
    
    @Override
	public void boom() {
    	super.boom();
		this.isSuperSaiyan = true;
		this.blastingPeriod = SUPER_BLASTING_PERIOD;
		try {
            // Load sprite from resources
			this.sprite = new Image(MediaManager.SUPERORANGECAT_URL);
        } catch (Exception e) {
            System.out.println("Could not load white cat sprite: " + e.getMessage());
        }
	}
	
	public long getBlastingSpeed() {
		return blastingPeriod;
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