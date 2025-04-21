package application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import java.util.LinkedList;
import java.util.Queue;
public class CheatCodeManager {
    private static final String[] superSaiyanCode = {"O", "O", "O", "O"};
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
        checkCheatCode();
    }
    
    /**
     * Check if the recent key presses match the cheat code
     */
    private void checkCheatCode() {
        if (keyPressHistory.size() < superSaiyanCode.length) {
            return;
        }
        
        // Convert queue to array for comparison
        String[] recentKeys = keyPressHistory.toArray(new String[0]);
        boolean matched = true;
        
        for (int i = 0; i < superSaiyanCode.length; i++) {
            if (!recentKeys[i].equals(superSaiyanCode[i])) {
                matched = false;
                break;
            }
        }
        
        if (matched) {
            activateCheatCode(gameController);
        }
    }
    private void activateCheatCode(GameController gameController) {
    	if(!gameController.getMainCharacter().isSuperSaiyan()) {    		
    		gameController.getMainCharacter().boom();
        	SoundManager.playBoomSound();
    	}
    }
}