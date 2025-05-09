package base;
import model.KiBlast;
import model.Stone;
import application.SoundManager;

public abstract class MainCharacter extends Character {
	
	protected boolean isSuperSaiyan;
	protected int damage;
	protected double kiSpeed;
	private static final double START_SPEED = 5.3;
	private static final double SUPER_SPEED = 6.7;
	protected static final double BODY_SIZE = 69;
    
    public MainCharacter(double x, double y, double width, double height, int maxHp) {
        super(x, y, width, height, maxHp, START_SPEED);
    }
    
    // Right direction
    public KiBlast shootKiBlast(int damage, double speed) {
    	SoundManager.playKiSound();
    	BlasterState s = this.isSuperSaiyan ? BlasterState.SUPER_MAIN : BlasterState.NORM_MAIN;
        return new KiBlast(x + width, y + height/2, 1, damage, speed, s);
    }
    
    // Left direction
    public KiBlast shootKiBlast(boolean ultimate) {
    	SoundManager.playKiSound();
    	BlasterState s = this.isSuperSaiyan ? BlasterState.SUPER_MAIN : BlasterState.NORM_MAIN;
        return new KiBlast(x, y + height/2, -1, s, ultimate);  
    }
    
    @Override
    public void takeDamage() {
        super.takeDamage();
        SoundManager.playHitSound();
        
        // Play death sound if health reaches 0
        if (currentHp <= 0) {
            SoundManager.playDeathSound();
        }
    }
    
    public void takeDamage(Obstacle o) {
    	if(this.isSuperSaiyan) {
    		if(!(o instanceof Stone)) {
    			takeDamage();
    		}
    	} else {
    		takeDamage();
    	}
    }
    
    public boolean isSuperSaiyan() {
    	return isSuperSaiyan;
    }
    
	public static double getBodySize() {
		return BODY_SIZE;
	}
	public void boom() {
    	this.moveSpeed = SUPER_SPEED;
    }
    
    
    public abstract double getKiSpeed();
    
    public abstract int getDamage();
    
}