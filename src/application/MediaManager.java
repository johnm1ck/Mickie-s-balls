package application;

public class MediaManager {

	private static final String SPRITE_PATH = "resource/sprite/";
	private static final String SOUND_PATH = "resource/sound/";
	private static final String BACKGROUND_PATH = "resource/background/";

	private static String loadSpritePath(String fileName) {
		return ClassLoader.getSystemResource(SPRITE_PATH + fileName).toString();
	}
	
	private static String loadSoundPath(String fileName) {
		return ClassLoader.getSystemResource(SOUND_PATH + fileName).toString();
	}
	
	private static String loadBackgroundPath(String fileName) {
		return ClassLoader.getSystemResource(BACKGROUND_PATH + fileName).toString();
	}

	public static final String WHITEBLAST_URL = loadSpritePath("white_blast.png");
	public static final String GRAYBLAST_URL = loadSpritePath("gray_blast.png");
	public static final String LIGHTPURPLEBLAST_URL = loadSpritePath("lightpurple_blast.png");
	public static final String PURPLEBLAST_URL = loadSpritePath("purple_blast.png");
	public static final String LIGHTYELLOWBLAST_URL = loadSpritePath("lightyellow_blast.png");
	public static final String YELLOWBLAST_URL = loadSpritePath("yellow_blast.png");
	public static final String LIGHTBLUELBLAST_URL = loadSpritePath("lightblue_blast.png");
	public static final String BLUELBLAST_URL = loadSpritePath("blue_blast.png");

	public static final String BIGDOG_URL = loadSpritePath("big_dog.png");
	public static final String ORANGECAT_URL = loadSpritePath("orange_cat.png");
	public static final String SUPERORANGECAT_URL = loadSpritePath("super_orange_cat.png");
	public static final String WHITECAT_URL = loadSpritePath("white_cat.png");
	public static final String SUPERWHITECAT_URL = loadSpritePath("super_white_cat.png");
	public static final String PUPPY_URL = loadSpritePath("puppy.png");
	public static final String STONE_URL = loadSpritePath("stone.png");
	public static final String SUPERDOG_URL = loadSpritePath("super_dog.png");
	public static final String ULTRADOG_URL = loadSpritePath("ultra_dog.png");
	
	public static final String GREEN_SKY_URL = loadBackgroundPath("green_sky.mp4");
	public static final String GREEN_SKY_FIRST_FRAME_URL = loadBackgroundPath("green_sky_first_frame.png");
	public static final String PURPLE_SKY_URL = loadBackgroundPath("purple_sky.mp4");
	public static final String PURPLE_SKY_FIRST_FRAME_URL = loadBackgroundPath("purple_sky_first_frame.png");
	public static final String SPACE_URL = loadBackgroundPath("space.mp4");
	public static final String SPACE_FIRST_FRAME_URL = loadBackgroundPath("space_first_frame.png");
	
	public static final String BGM_URL = loadSoundPath("bgm.mp3");
	public static final String BOOM_URL = loadSoundPath("boom.mp3");
	public static final String DEATH_URL = loadSoundPath("death.mp3");
	public static final String HIT_URL = loadSoundPath("hit.mp3");
	public static final String IN_GAME_BGM_URL = loadSoundPath("in_game_bgm.mp3");
	public static final String KI_URL = loadSoundPath("ki.mp3");
	public static final String SUPER_BGM_URL = loadSoundPath("super_bgm.mp3");
	public static final String VICTORY_URL = loadSoundPath("victory.mp3");
	public static final String X_KI_URL = loadSoundPath("x_ki.mp3");
	public static final String Z_KI_URL = loadSoundPath("z_ki.mp3");
	public static final String DOG_CRY_URL = loadSoundPath("dog_cry.mp3");
}
