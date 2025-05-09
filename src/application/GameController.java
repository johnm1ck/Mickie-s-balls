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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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

	// Track if super saiyan transformation has occurred
	// private boolean superSaiyanTransformed = false;

	private final static double HEIGHT = 750;
	private final static double WIDTH = 1500;
	private final static long OBSTACLE_PERIOD = 710_000_000;
	private final static long SUPER_OBSTACLE_PERIOD = 190_000_000;

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
			mainCharacter = new OrangeCat(WIDTH / 2, HEIGHT / 2);
		} else {
			mainCharacter = new WhiteCat(WIDTH / 2, HEIGHT / 2);
		}

		// Create initial enemy (Super Dog)
		currentEnemy = new SuperDog(100, HEIGHT / 2);

		lastObstacleTime = 0;
		lastEnemyAttackTime = 0;
		lastAutomaticBlastTime = 0;
		lastEnemyMoveTime = 0;
		obstaclePeriod = OBSTACLE_PERIOD;
		enemyMovingUp = random.nextBoolean();

		// Reset super saiyan transformation flag
		// superSaiyanTransformed = false;

		// Reset sound manager's boom sound flag
		SoundManager.resetBoomSound();
	}

	public void startGame() {
		if (!gameRunning) {
			gameRunning = true;
			gamePaused = false;
			gameWon = false;

			// Start background music
			SoundManager.startInGameBackgroundMusic();

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
		// Stop background music
		SoundManager.stopBackgroundMusic();
	}

	public void togglePause() {
		gamePaused = !gamePaused;
		if (gamePaused) {
			SoundManager.pauseBackgroundMusic();
		} else {
			SoundManager.resumeBackgroundMusic();
		}
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
			if (now - lastAutomaticBlastTime > ((OrangeCat) mainCharacter).getBlastingSpeed()) {
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
		if (currentEnemy == null)
			return;
		
		// 41% chance to change direction every 0.5 seconds
		if (now - lastEnemyMoveTime > 500_000_000) {
			lastEnemyMoveTime = now;
			if (random.nextInt(100) < 41) {  
				enemyMovingUp = !enemyMovingUp;
			}
		}

		// Move enemy
		if (enemyMovingUp) {
			currentEnemy.moveUp();
			// Keep enemy on screen
			if (currentEnemy.getY() < 50) {
				enemyMovingUp = false;
				lastEnemyMoveTime = now;
				currentEnemy.moveDown();
			}
		} else {
			currentEnemy.moveDown();
			// Keep enemy on screen
			if (currentEnemy.getY() > HEIGHT - 50) {
				enemyMovingUp = true;
				lastEnemyMoveTime = now;
				currentEnemy.moveUp();
			}
		}
	}

	private void enemyShoot() {
		if (currentEnemy == null)
			return;
		KiBlast blast = currentEnemy.shootKiBlast();
		enemyKiBlasts.add(blast);
	}

	private void spawnRandomObstacle() {
		int type = random.nextInt(5);
		Obstacle obstacle;
		
		// 3 in 5 chance to spawn stone; 1 in 5 chance to spawn puppy or big dog
		switch (type) {
		case 0:
			obstacle = new Stone(WIDTH, 50 + random.nextInt((int) HEIGHT - 100));
			break;
		case 1:
			obstacle = new Puppy(WIDTH, 50 + random.nextInt((int) HEIGHT - 100));
			break;
		case 2:
			obstacle = new BigDog(WIDTH, 50 + random.nextInt((int) HEIGHT - 100));
			break;
		case 3:
			obstacle = new Stone(WIDTH, 50 + random.nextInt((int) HEIGHT - 100));
			break;
		case 4:
			obstacle = new Stone(WIDTH, 50 + random.nextInt((int) HEIGHT - 100));
			break;
		default:
			obstacle = new Stone(WIDTH, 50 + random.nextInt((int) HEIGHT - 100));
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
			if (blast.getX() > WIDTH || blast.getX() < 0) {
				playerBlastIterator.remove();
			}
		}

		// Update enemy ki blasts
		Iterator<KiBlast> enemyBlastIterator = enemyKiBlasts.iterator();
		while (enemyBlastIterator.hasNext()) {
			KiBlast blast = enemyBlastIterator.next();
			blast.update();

			// Remove blasts that are off-screen
			if (blast.getX() > WIDTH || blast.getX() < 0) {
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
							if (obstacle instanceof Puppy ||
								obstacle instanceof BigDog) {
								SoundManager.playDogCrySound();
							}
							// 25% chance to heal after destroying obstacle
							int toHeal = random.nextInt(4);
							if (toHeal == 1)
								mainCharacter.healHp();
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
							((SuperDog) currentEnemy).boom();
							SoundManager.startSuperBackgroundMusic();
							// Only transform main character if not already transformed
							if (!mainCharacter.isSuperSaiyan()) {
								mainCharacter.boom();
								SoundManager.playBoomSound();
							}

							obstaclePeriod = SUPER_OBSTACLE_PERIOD;
							currentEnemy = new UltraDog(currentEnemy.getX(), currentEnemy.getY());
						} else {
							// Game victory condition
							currentEnemy = null;
							gameWon = true;
							SoundManager.playVictorySound();
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
			KiBlast blast = mainCharacter.shootKiBlast(mainCharacter.getDamage(), mainCharacter.getKiSpeed());
			playerKiBlasts.add(blast);
		}
	}

	public void shootKiBlastLeft(boolean ultimate) {
		if (mainCharacter != null && currentEnemy != null) {
			int requiredPoints = ultimate ? currentEnemy.getCurrentHp() : 10;

			if (score >= requiredPoints) {
				KiBlast blast = mainCharacter.shootKiBlast(ultimate);
				playerKiBlasts.add(blast);
				score -= requiredPoints;
			}
		}
	}

	public void render(GraphicsContext gc) {
		// Draw a simple background(Change to background vdo)
//        gc.setFill(javafx.scene.paint.Color.LIGHTBLUE); 
//        gc.fillRect(0, 0, width, height);

		// Draw ground
		//gc.setFill(Color.GREEN.deriveColor(0, 1, 1, 0.7)); // Semi-transparent green
		//gc.fillRect(0, height - 50, width, 50);

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
		gc.setFont(new Font("Comic Sans MS", 20));
		gc.setFill(Color.FLORALWHITE);
		gc.setStroke(Color.rgb(69, 47, 7));
		gc.setLineWidth(2);

		// Draw text with outline for better visibility
		String scoreText = "Score: " + score;
		gc.strokeText(scoreText, WIDTH - 140, 30);
		gc.fillText(scoreText, WIDTH - 140, 30);

		if (currentEnemy != null) {
			String enemyHpText = "Enemy HP: " + currentEnemy.getCurrentHp() + "/" + currentEnemy.getMaxHp();
			gc.strokeText(enemyHpText, 20, 30);
			gc.fillText(enemyHpText, 20, 30);
		} else {
			String enemyDefeatedText = "Enemy Defeated!";
			gc.strokeText(enemyDefeatedText, 20, 30);
			gc.fillText(enemyDefeatedText, 20, 30);
		}

		String playerHpText = "Player HP: " + mainCharacter.getCurrentHp() + "/" + mainCharacter.getMaxHp();
		gc.strokeText(playerHpText, 350, 30);
		gc.fillText(playerHpText, 350, 30);
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
		return HEIGHT;
	}

	public static double getWidth() {
		return WIDTH;
	}
	public void setScore(int score) {
		this.score = score;
	}
}