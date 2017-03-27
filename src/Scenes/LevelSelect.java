package Scenes;

import java.io.File;
import java.util.ArrayList;

import Display.Display;
import javafx.scene.paint.Color;

public class LevelSelect extends Scene {
	ArrayList<String> files;

	public final static double PADDING = 25;

	public void initChildScene() {
		backGround = Color.DARKOLIVEGREEN;
		files = findLevels();
		double x = Display.WIDTH / 10 + PADDING;
		double y = Display.HEIGHT / 14 + PADDING;
		for (String s : findLevels()) {
			buttonList.add(new Button(s.substring(0, s.indexOf('.')), x, y, "SCENE:playerSelect," + s, Display.WIDTH / 5,
					Display.HEIGHT / 7));
			System.out.println(s);
			x += Display.WIDTH / 5 + PADDING;
			if (x > Display.WIDTH - Display.WIDTH / 10) {
				x = Display.WIDTH / 10;
				y += Display.HEIGHT / 7;
			}
		}
	}

	private ArrayList<String> findLevels() {
		File folder = new File(System.getProperty("user.dir") + "/Levels");
		File[] listOfFiles = folder.listFiles();
		ArrayList<String> levelNames = new ArrayList<String>();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				levelNames.add(listOfFiles[i].getName());
			}
		}
		return levelNames;
	}
}
