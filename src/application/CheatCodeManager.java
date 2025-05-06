package application;

import javafx.scene.Scene;
import java.util.Arrays;

import javafx.scene.input.KeyEvent;
import java.util.LinkedList;
import java.util.Queue;

public class CheatCodeManager {
	private static final String[] superSaiyanCode = { "O", "O", "O", "O" };
	private static final String[] scoreCode = { "U", "U", "U", "U" };
	private static final String[] hpCode = { "H", "H", "H", "H" };
	private GameController gameController;

	// Queue to store recent key presses
	private Queue<String> keyPressHistory;
	private int maxCodeLength;

	public CheatCodeManager() {
		this.maxCodeLength = superSaiyanCode.length;
		this.keyPressHistory = new LinkedList<>();
	}

	public void setupCheatCodeListener(Scene scene, GameController gameController) {
		scene.addEventHandler(KeyEvent.KEY_PRESSED, this::handleKeyPress);
		this.gameController = gameController;
	}

	private void handleKeyPress(KeyEvent event) {
		String keyName = event.getCode().toString();
		keyPressHistory.add(keyName);

		// Keep the history size limited to max code length
		if (keyPressHistory.size() > maxCodeLength) {
			keyPressHistory.remove();
		}

		// Check if the cheat code has been entered


		for (String[] code : Arrays.asList(superSaiyanCode, scoreCode, hpCode)) {
		    checkCheatCode(code);
		}

	}

	/**
	 * Check if the recent key presses match the cheat code
	 */
	private void checkCheatCode(String[] cheatCode) {
		if (keyPressHistory.size() < cheatCode.length) {
			return;
		}

		// Convert queue to array for comparison
		String[] recentKeys = keyPressHistory.toArray(new String[0]);
		boolean matched = true;

		for (int i = 0; i < cheatCode.length; i++) {
			if (!recentKeys[i].equals(cheatCode[i])) {
				matched = false;
				break;
			}
		}

		if (matched) {
			activateCheatCode(gameController, cheatCode);
		}
	}

	private void activateCheatCode(GameController gameController, String[] chatCode) {
		if (chatCode.equals(superSaiyanCode)) {
			if (!gameController.getMainCharacter().isSuperSaiyan()) {
				gameController.getMainCharacter().boom();
				SoundManager.playBoomSound();

			}
		} if (chatCode.equals(scoreCode)) {
			gameController.setScore(9999);
			
		}
		 if (chatCode.equals(hpCode)) {
				gameController.getMainCharacter().setCurrentHp(9999);
				gameController.getMainCharacter().setMaxHp(9999);
				
				
			}

	}

}