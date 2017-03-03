package Scenes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import Display.Display;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

public class LevelSelect extends Scene {
	ArrayList<String> files;

	public final static double PADDING = 25;

	public void initChildScene() {
		backGround = Color.DARKOLIVEGREEN;
		files = findLevels();
		double x = Display.WIDTH / 10 + PADDING;
		double y = Display.HEIGHT / 14 + PADDING;
		for (String s : findLevels()) {
			buttonList.add(new Button(s.substring(0, s.indexOf('.')), x, y, s, Display.WIDTH / 5, Display.HEIGHT / 7));
			System.out.println(s.substring(0, s.indexOf('.')));
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

	public void loadLevel() {
		Objects.Grid grid = new Objects.Grid();

		System.out.println("Loading...");
		FileChooser dialog = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Sniper Level File(*.sniperLvl)",
				"*.sniperLvl");

		dialog.getExtensionFilters().add(extFilter);
		dialog.setTitle("Load Level File");
		File file = dialog.showOpenDialog(Display.theStage);
		if (file != null) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String levelString = "";
				String str;
				while ((str = reader.readLine()) != null) {
					levelString += str + '\n';
				}
				grid.stringToGrid(levelString);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		world = new Objects.World();
		world.populateWorld(grid);
	}
}
