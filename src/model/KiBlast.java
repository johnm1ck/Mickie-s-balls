package model;

import base.GameObject;
import javafx.scene.image.Image;
import base.BlasterState;
import application.ImageManager;
public class KiBlast extends GameObject {
    private int direction; // 1 for right, -1 for left
    private double speed;
    private int damage = 0;
    
    //blasting rightward
    public KiBlast(double x, double y, int direction, int damage, double speed, BlasterState s) {
        super(x, y, 56, 42);
        this.direction = direction;
        this.speed = speed;
        this.damage = damage;
        try {
            // Load sprite from resources
        	if (direction == 1) {
        		switch (s) {
        			case BlasterState.NORM_MAIN:
        				sprite = new Image(ImageManager.WHITEBLAST_URL);
        				break;
        			case BlasterState.SUPER_MAIN:
        				sprite = new Image(ImageManager.GRAYBLAST_URL);
        				break;
        			case BlasterState.NORM_ENEMY:
        				sprite = new Image(ImageManager.LIGHTPURPLEBLAST_URL);
        				break;
        			case BlasterState.ULTRA_ENEMY:
        				sprite = new Image(ImageManager.PURPLEBLAST_URL);
        				break;
        			default:
        				break;
        		}
        	} else {
        		switch (s) {
    			case BlasterState.NORM_MAIN:
    				break;
    			case BlasterState.SUPER_MAIN:
    				sprite = new Image(ImageManager.GRAYBLAST_URL);
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
    	super(x, y, 84, 42); //longer than rightward blast
    	if (ultimate) {
    		this.setWidth(this.getWidth() * 1.2);
    		this.setHeight(this.getHeight() * 1.2);
    		this.speed = 43;
    		this.damage = 999;
    	} else {
    		this.speed = 13;
    		this.damage = (s == BlasterState.NORM_MAIN) ? 13 : 20; 
    	}
        this.direction = direction;
        try {
            // Load sprite from resources
        	if (direction == -1) {
        		switch (s) {
        			case BlasterState.NORM_MAIN:
        				sprite = ultimate ? new Image(ImageManager.LIGHTBLUELBLAST_URL)
        						: new Image(ImageManager.LIGHTYELLOWBLAST_URL);
        				break;
        			case BlasterState.SUPER_MAIN:
        				sprite = ultimate ? new Image(ImageManager.BLUELBLAST_URL) 
        						: new Image(ImageManager.YELLOWBLAST_URL);
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