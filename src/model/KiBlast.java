package model;

import base.GameObject;
import javafx.scene.image.Image;
import base.BlasterState;
import application.MediaManager;
public class KiBlast extends GameObject {
	private static final double LEFT_KI_WIDTH = 84;
	private static final double LEFT_KI_HEIGHT = 42;
	private static final double RIGHT_KI_WIDTH = 56;
	private static final double RIGHT_KI_HEIGHT = 42;
	private static final double LEFT_KI_ULTIMATE_SPEED = 47;
	private static final double LEFT_KI_NORMAL_SPEED = 23;
	private static final int LEFT_KI_SUPER_DAMAGE = 20;
	private static final int LEFT_KI_NORMAL_DAMAGE = 13;
    private int direction; // 1 for right, -1 for left
    private double speed;
    private int damage = 0;
    
    //blasting rightward
    public KiBlast(double x, double y, int direction, int damage, double speed, BlasterState s) {
        super(x, y, RIGHT_KI_WIDTH, RIGHT_KI_HEIGHT);
        this.direction = direction;
        this.speed = speed;
        this.damage = damage;
        try {
            // Load sprite from resources
        	if (direction == 1) {
        		switch (s) {
        			case BlasterState.NORM_MAIN:
        				sprite = new Image(MediaManager.WHITEBLAST_URL);
        				break;
        			case BlasterState.SUPER_MAIN:
        				sprite = new Image(MediaManager.GRAYBLAST_URL);
        				break;
        			case BlasterState.NORM_ENEMY:
        				sprite = new Image(MediaManager.LIGHTPURPLEBLAST_URL);
        				break;
        			case BlasterState.ULTRA_ENEMY:
        				sprite = new Image(MediaManager.PURPLEBLAST_URL);
        				break;
        			default:
        				break;
        		}
        	} else {
        		switch (s) {
    			case BlasterState.NORM_MAIN:
    				break;
    			case BlasterState.SUPER_MAIN:
    				sprite = new Image(MediaManager.GRAYBLAST_URL);
    				break;
				default:
					break;
        		}
        	}
        } catch (Exception e) {
            System.out.println("Could not load ki blast sprite: " + e.getMessage());
        }
    }
    
    //blasting leftward
    public KiBlast(double x, double y, int direction, BlasterState s, boolean ultimate) {
    	super(x, y, LEFT_KI_WIDTH, LEFT_KI_HEIGHT); //longer than rightward blast
    	if (ultimate) {
    		this.setWidth(this.getWidth() * 1.3); // *1.3 for bigger ki
    		this.setHeight(this.getHeight() * 1.3);
    		this.speed = LEFT_KI_ULTIMATE_SPEED;
    		this.damage = 999; //guarantee to kill instantly
    	} else {
    		this.speed = LEFT_KI_NORMAL_SPEED;
    		this.damage = (s == BlasterState.NORM_MAIN) ?
    				LEFT_KI_NORMAL_DAMAGE : LEFT_KI_SUPER_DAMAGE; 
    	}
        this.direction = direction;
        try {
            // Load sprite from resources
        	if (direction == -1) {
        		switch (s) {
        			case BlasterState.NORM_MAIN:
        				sprite = ultimate ? new Image(MediaManager.LIGHTBLUELBLAST_URL)
        						: new Image(MediaManager.LIGHTYELLOWBLAST_URL);
        				break;
        			case BlasterState.SUPER_MAIN:
        				sprite = ultimate ? new Image(MediaManager.BLUELBLAST_URL) 
        						: new Image(MediaManager.YELLOWBLAST_URL);
        				break;
        			default:
        				break;
        		}
        	}
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

	public double getSpeed() {
		return speed;
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public int getDamage() {
		return damage;
	}
    
}