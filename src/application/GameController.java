package application;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
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
    private boolean gameRunning;
    private boolean gamePaused;
    private boolean enemyMovingUp;
    private int enemiesDefeated;
    private boolean gameWon;
    
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
        if (characterType.equals("black")) {
            mainCharacter = new BlackCat(400, 300);
        } else {
            mainCharacter = new WhiteCat(400, 300);
        }
        
        // Create initial enemy (Super Dog)
        currentEnemy = new SuperDog(100, 300);
        
        lastObstacleTime = 0;
        lastEnemyAttackTime = 0;
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
    
    private void update(long now) {
        // Spawn obstacles periodically
        if (now - lastObstacleTime > 1_000_000_000) { // 1 second in nanoseconds
            spawnRandomObstacle();
            lastObstacleTime = now;
        }
        
        // Enemy random movement
        updateEnemyMovement();
        
        // Enemy random attack
        if (now - lastEnemyAttackTime > (1_000_000_000 + random.nextLong(3_000_000_000L))) {
            enemyShoot();
            lastEnemyAttackTime = now;
        }
        
        // Update all game entities
        updateEntities();
        
        // Check for collisions
        checkCollisions();
    }
    
    private void updateEnemyMovement() {
        if (currentEnemy == null) return;
        
        // Change direction occasionally
        if (random.nextInt(100) < 2) { // 2% chance to change direction each update
            enemyMovingUp = !enemyMovingUp;
        }
        
        // Move enemy
        if (enemyMovingUp) {
            currentEnemy.moveUp();
            // Keep enemy on screen
            if (currentEnemy.getY() < 50) {
                enemyMovingUp = false;
            }
        } else {
            currentEnemy.moveDown();
            // Keep enemy on screen
            if (currentEnemy.getY() > 550) {
                enemyMovingUp = true;
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
                obstacle = new Stone(800, 100 + random.nextInt(400));
                break;
            case 1:
                obstacle = new Puppy(800, 100 + random.nextInt(400));
                break;
            case 2:
                obstacle = new BigDog(800, 100 + random.nextInt(400));
                break;
            default:
                obstacle = new Stone(800, 100 + random.nextInt(400));
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
            if (blast.getX() > 800 || blast.getX() < 0) {
                playerBlastIterator.remove();
            }
        }
        
        // Update enemy ki blasts
        Iterator<KiBlast> enemyBlastIterator = enemyKiBlasts.iterator();
        while (enemyBlastIterator.hasNext()) {
            KiBlast blast = enemyBlastIterator.next();
            blast.update();
            
            // Remove blasts that are off-screen
            if (blast.getX() > 800 || blast.getX() < 0) {
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
                        obstacle.takeDamage();
                        hitObstacle = true;
                        playerBlastIterator.remove();
                        
                        // Check if obstacle is destroyed
                        if (obstacle.isDestroyed()) {
                            score += obstacle.getPoints();
                            obstacleIterator.remove();
                        }
                        
                        break;
                    }
                }
            } else if (currentEnemy != null) { // Left-moving blasts check against enemy
                if (blast.intersects(currentEnemy)) {
                    // Special attack logic
                    int damage = (blast instanceof SpecialKiBlast) ? 
                        ((SpecialKiBlast)blast).getDamage() : 1;
                    
                    currentEnemy.takeDamage(damage);
                    playerBlastIterator.remove();
                    
                    // Check if enemy is defeated
                    if (currentEnemy.isDefeated()) {
                        enemiesDefeated++;
                        
                        // Change to Ultra Dog after defeating Super Dog
                        if (enemiesDefeated == 1) {
                            currentEnemy = new UltraDog(100, 300);
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
                    mainCharacter.takeDamage();
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
            KiBlast blast = mainCharacter.shootKiBlast();
            playerKiBlasts.add(blast);
        }
    }
    
    public void shootSpecialKiBlastLeft(boolean ultimate) {
        if (mainCharacter != null && currentEnemy != null) {
            int requiredPoints = ultimate ? currentEnemy.getMaxHp() : 10;
            
            if (score >= requiredPoints) {
                int damage = ultimate ? currentEnemy.getMaxHp() : 10;
                SpecialKiBlast blast = mainCharacter.shootSpecialKiBlast(damage);
                playerKiBlasts.add(blast);
                score -= requiredPoints;
            }
        }
    }
    
    public void render(GraphicsContext gc) {
        // Draw a simple background
        gc.setFill(javafx.scene.paint.Color.LIGHTBLUE);
        gc.fillRect(0, 0, 800, 600);
        
        // Draw ground
        gc.setFill(javafx.scene.paint.Color.GREEN);
        gc.fillRect(0, 550, 800, 50);
        
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
        gc.fillText("Score: " + score, 700, 20);
        
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
}