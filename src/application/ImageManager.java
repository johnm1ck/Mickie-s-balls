package application;

public class ImageManager {

	private static final String IMAGE_PATH = "resource/image/";

	private static String loadImagePath(String fileName) {
		return ClassLoader.getSystemResource(IMAGE_PATH + fileName).toString();
	}

	public static final String WHITEBLAST_URL = loadImagePath("whiteblast.png");
	public static final String GRAYBLAST_URL = loadImagePath("grayblast.png");
	public static final String LIGHTPURPLEBLAST_URL = loadImagePath("lightpurpleblast.png");
	public static final String PURPLEBLAST_URL = loadImagePath("purpleblast.png");
	public static final String LIGHTYELLOWBLAST_URL = loadImagePath("lightyellowblast.png");
	public static final String YELLOWBLAST_URL = loadImagePath("yellowblast.png");
	public static final String LIGHTBLUELBLAST_URL = loadImagePath("lightblueblast.png");
	public static final String BLUELBLAST_URL = loadImagePath("blueblast.png");

	public static final String BIGDOG_URL = loadImagePath("bigdog.png");
	public static final String ORANGECAT_URL = loadImagePath("orangecat.png");
	public static final String SUPERORANGECAT_URL = loadImagePath("superorangecat.png");
	public static final String WHITECAT_URL = loadImagePath("whitecat.png");
	public static final String SUPERWHITECAT_URL = loadImagePath("superwhitecat.png");
	public static final String PUPPY_URL = loadImagePath("puppy.png");
	public static final String STONE_URL = loadImagePath("stone.png");
	public static final String SUPERDOG_URL = loadImagePath("superdog.png");
	public static final String ULTRADOG_URL = loadImagePath("ultradog.png");
}
