package application;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import base.Enemy;
import base.MainCharacter;
import base.Obstacle;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import model.*;

public class GameController {
    private String characterType;
    private MainCharacter mainCharacter;
    private Enemy currentEnemy;
    private List<Obstacle> obstacles;
    private List<KiBlast> playerKiBlasts;
    private List<KiBlast> enemyKiBlasts;
    private int score;
    private Random random;
    private AnimationTimer gameLoop;
    private long lastObstacleTime;
    private long lastEnemyAttackTime;
    private long lastAutomaticBlastTime;
    private long lastEnemyMoveTime;
    private long obstaclePeriod;
    private boolean gameRunning;
    private boolean gamePaused;
    private boolean enemyMovingUp;
    private int enemiesDefeated;
    private boolean gameWon;
    
    private final static double height = 750;
    private final static double width = 1500;
    
    public GameController(String characterType) {
        this.characterType = characterType;
        this.obstacles = new ArrayList<>();
        this.playerKiBlasts = new ArrayList<>();
        this.enemyKiBlasts = new ArrayList<>();
        this.score = 0;
        this.random = new Random();
        this.gameRunning = false;
        this.gamePaused = false;
        this.enemiesDefeated = 0;
        this.gameWon = false;
        
        // Initialize game entities
        initializeEntities();
    }
    
    private void initializeEntities() {
        // Create main character based on selection
        if (characterType.equals("orange")) {
            mainCharacter = new OrangeCat(width/2, height/2);
        } else {
            mainCharacter = new WhiteCat(width/2, height/2);
        }
        
        // Create initial enemy (Super Dog)
        currentEnemy = new SuperDog(100, height/2);
        
        lastObstacleTime = 0;
        lastEnemyAttackTime = 0;
        lastAutomaticBlastTime = 0;
        lastEnemyMoveTime = 0;
        obstaclePeriod = 790_000_000;
        enemyMovingUp = random.nextBoolean();
    }
    
    public void startGame() {
        if (!gameRunning) {
            gameRunning = true;
            gamePaused = false;
            gameWon = false;
            
            gameLoop = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    if (!gamePaused) {
                        update(now);
                    }
                }
            };
            
            gameLoop.start();
        }
    }
    
    public void stopGame() {
        gameRunning = false;
        if (gameLoop != null) {
            gameLoop.stop();
        }
    }
    
    public void togglePause() {
        gamePaused = !gamePaused;
    }
    
    public boolean isPaused() {
        return gamePaused;
    }
    
    public boolean isGameWon() {
        return gameWon;
    }
    
    // time measured in nanoseconds
    private void update(long now) {
        // Spawn obstacles periodically
        if (now - lastObstacleTime > obstaclePeriod) { 
            spawnRandomObstacle();
            lastObstacleTime = now;
        }
        
        // Enemy random movement
        updateEnemyMovement(now);
        
        // Enemy random attack
        if (now - lastEnemyAttackTime > (1_000_000_000 + random.nextLong(3_000_000_000L))) {
            enemyShoot();
            lastEnemyAttackTime = now;
        }
        
        // Automatic blasting for OrangeCat
        if (this.characterType == "orange") {
        	if (now - lastAutomaticBlastTime > ((OrangeCat)mainCharacter).getBlastingSpeed()) {
        		this.shootKiBlastRight();
        		lastAutomaticBlastTime = now;
        	}
        }
        
        // Update all game entities
        updateEntities();
        
        // Check for collisions
        checkCollisions();
    }
    
    private void updateEnemyMovement(long now) {
        if (currentEnemy == null) return;
        
        // Change direction occasionally
        // 43% chance to change direction every 1.69 second
        if (now - lastEnemyMoveTime > 1_690_000_000) {
        	lastEnemyMoveTime = now;
        	if (random.nextInt(100) <= 43) {
        		enemyMovingUp = !enemyMovingUp;        		
        	}
        }
        
        // Move enemy
        if (enemyMovingUp) {
            currentEnemy.moveUp();
            // Keep enemy on screen
            if (currentEnemy.getY() < 50) {
                enemyMovingUp = false;
                currentEnemy.moveDown();
            }
        } else {
            currentEnemy.moveDown();
            // Keep enemy on screen
            if (currentEnemy.getY() > height - 50) {
                enemyMovingUp = true;
                currentEnemy.moveUp();
            }
        }
    }
    
    private void enemyShoot() {
        if (currentEnemy == null) return;
        KiBlast blast = currentEnemy.shootKiBlast();
        enemyKiBlasts.add(blast);
    }
    
    private void spawnRandomObstacle() {
        int type = random.nextInt(3); // 0, 1, or 2
        Obstacle obstacle;
        
        switch (type) {
            case 0:
                obstacle = new Stone(width, 50 + random.nextInt((int)height - 100));
                break;
            case 1:
                obstacle = new Puppy(width, 50 + random.nextInt((int)height - 100));
                break;
            case 2:
                obstacle = new BigDog(width, 50 + random.nextInt((int)height - 100));
                break;
            default:
                obstacle = new Stone(width, 50 + random.nextInt((int)height - 100));
        }
        
        obstacles.add(obstacle);
    }
    
    private void updateEntities() {
        // Update main character
        if (mainCharacter != null) {
            mainCharacter.update();
        }
        
        // Update enemy
        if (currentEnemy != null) {
            currentEnemy.update();
        }
        
        // Update obstacles
        Iterator<Obstacle> obstacleIterator = obstacles.iterator();
        while (obstacleIterator.hasNext()) {
            Obstacle obstacle = obstacleIterator.next();
            obstacle.update();
            
            // Remove obstacles that are off-screen
            if (obstacle.getX() < 0) {
                obstacleIterator.remove();
            }
        }
        
        // Update player ki blasts
        Iterator<KiBlast> playerBlastIterator = playerKiBlasts.iterator();
        while (playerBlastIterator.hasNext()) {
            KiBlast blast = playerBlastIterator.next();
            blast.update();
            
            // Remove blasts that are off-screen
            if (blast.getX() > width || blast.getX() < 0) {
                playerBlastIterator.remove();
            }
        }
        
        // Update enemy ki blasts
        Iterator<KiBlast> enemyBlastIterator = enemyKiBlasts.iterator();
        while (enemyBlastIterator.hasNext()) {
            KiBlast blast = enemyBlastIterator.next();
            blast.update();
            
            // Remove blasts that are off-screen
            if (blast.getX() > width || blast.getX() < 0) {
                enemyBlastIterator.remove();
            }
        }
    }
    
    private void checkCollisions() {
        // Check player ki blasts against obstacles
        Iterator<KiBlast> playerBlastIterator = playerKiBlasts.iterator();
        while (playerBlastIterator.hasNext()) {
            KiBlast blast = playerBlastIterator.next();
            boolean hitObstacle = false;
            
            // Only check right-moving blasts against obstacles
            if (blast.getDirection() > 0) {
                Iterator<Obstacle> obstacleIterator = obstacles.iterator();
                while (obstacleIterator.hasNext() && !hitObstacle) {
                    Obstacle obstacle = obstacleIterator.next();
                    
                    if (blast.intersects(obstacle)) {
                        obstacle.takeDamage(mainCharacter.getDamage());
                        hitObstacle = true;
                        playerBlastIterator.remove();
                        
                        // Check if obstacle is destroyed
                        if (obstacle.isDestroyed()) {
                            score += obstacle.getPoints();
                            // 25% chance to heal after destroying obstacle
                            int toHeal = random.nextInt(4);
                            if (toHeal == 1) mainCharacter.healHp();
                            obstacleIterator.remove();
                        }
                        
                        break;
                    }
                }
            } else if (currentEnemy != null) { // Left-moving blasts check against enemy
                if (blast.intersects(currentEnemy)) {
                    // Special attack logic
                    
                    currentEnemy.takeDamage(blast.getDamage());
                    playerBlastIterator.remove();
                    
                    // Check if enemy is defeated
                    if (currentEnemy.isDefeated()) {
                        enemiesDefeated++;
                        
                        // Change to Ultra Dog after defeating Super Dog
                        if (enemiesDefeated == 1) {
                        	((SuperDog)currentEnemy).boom();
                        	if (!mainCharacter.isSuperSaiyan()) mainCharacter.boom();
                        	obstaclePeriod = 190_000_000;
                            currentEnemy = new UltraDog(currentEnemy.getX(), currentEnemy.getY());
                        } else {
                            // Game victory condition
                            currentEnemy = null;
                            gameWon = true;
                        }
                    }
                }
            }
        }
        
        // Check enemy ki blasts against player
        if (mainCharacter != null) {
            Iterator<KiBlast> enemyBlastIterator = enemyKiBlasts.iterator();
            while (enemyBlastIterator.hasNext()) {
                KiBlast blast = enemyBlastIterator.next();
                
                if (blast.intersects(mainCharacter)) {
                    mainCharacter.takeDamage();
                    enemyBlastIterator.remove();
                }
            }
        }
        
        // Check obstacles against player (collision causes damage)
        if (mainCharacter != null) {
            Iterator<Obstacle> obstacleIterator = obstacles.iterator();
            while (obstacleIterator.hasNext()) {
                Obstacle obstacle = obstacleIterator.next();
                
                if (obstacle.intersects(mainCharacter)) {
                    mainCharacter.takeDamage(obstacle);
                    score += mainCharacter.isSuperSaiyan() ? obstacle.getPoints() : 0;
                    obstacleIterator.remove();
                }
            }
        }
    }
    
    public void moveCharacterUp() {
        if (mainCharacter != null) {
            mainCharacter.moveUp();
        }
    }
    
    public void moveCharacterDown() {
        if (mainCharacter != null) {
            mainCharacter.moveDown();
        }
    }
    
    public void stopCharacterMovement() {
        if (mainCharacter != null) {
            mainCharacter.stopMoving();
        }
    }
    
    public void shootKiBlastRight() {
        if (mainCharacter != null) {
            KiBlast blast = mainCharacter.shootKiBlast(
            		mainCharacter.getDamage(), mainCharacter.getKiSpeed());
            playerKiBlasts.add(blast);
        }
    }
    
    public void shootKiBlastLeft(boolean ultimate) {
        if (mainCharacter != null && currentEnemy != null) {
            int requiredPoints = ultimate ? currentEnemy.getMaxHp() : 10;
            
            if (score >= requiredPoints) {
                KiBlast blast = mainCharacter.shootKiBlast(ultimate);
                playerKiBlasts.add(blast);
                score -= requiredPoints;
            }
        }
    }
    
    public void render(GraphicsContext gc) {
        // Draw a simple background
        gc.setFill(javafx.scene.paint.Color.LIGHTBLUE);
        gc.fillRect(0, 0, width, height);
        
        // Draw ground
        gc.setFill(javafx.scene.paint.Color.GREEN);
        gc.fillRect(0, height-50, width, 50);
        
        // Render main character
        if (mainCharacter != null) {
            mainCharacter.render(gc);
        }
        
        // Render current enemy
        if (currentEnemy != null) {
            currentEnemy.render(gc);
        }
        
        // Render obstacles
        for (Obstacle obstacle : obstacles) {
            obstacle.render(gc);
        }
        
        // Render player ki blasts
        for (KiBlast blast : playerKiBlasts) {
            blast.render(gc);
        }
        
        // Render enemy ki blasts
        for (KiBlast blast : enemyKiBlasts) {
            blast.render(gc);
        }
        
        // Render score and enemy HP
        gc.setFill(javafx.scene.paint.Color.BLACK);
        gc.fillText("Score: " + score, width-100, 20);
        
        if (currentEnemy != null) {
            gc.fillText("Enemy HP: " + currentEnemy.getCurrentHp() + "/" + currentEnemy.getMaxHp(), 20, 20);
        } else {
            gc.fillText("Enemy Defeated!", 20, 20);
        }
        
        gc.fillText("Player HP: " + mainCharacter.getCurrentHp() + "/" + mainCharacter.getMaxHp(), 350, 20);
    }
    
    // Getters
    public MainCharacter getMainCharacter() {
        return mainCharacter;
    }
    
    public Enemy getCurrentEnemy() {
        return currentEnemy;
    }
    
    public int getScore() {
        return score;
    }

    public String getCharacterType() {
    	return characterType;
    }
    
	public static double getHeight() {
		return height;
	}

	public static double getWidth() {
		return width;
	}
    
}