package Scenes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import Display.Display;
import Objects.BrickBlock;
import Objects.CoverBlock;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

public class EditorScene extends Scene {

	Objects.Grid grid;

	final double menuBarSize = 50;
	final double cellSize = 20;

	int selectedBlockType = 1;

	public void initChildScene() {
		backGround = Color.DARKSLATEGRAY;
		buttonList.add(new Button(new Image("menuItems/save.gif"), menuBarSize / 2, Display.HEIGHT - (menuBarSize / 2),
				menuBarSize, menuBarSize, "save"));
		buttonList.add(new Button(new Image("menuItems/load.png"), 3 * (menuBarSize / 2),
				Display.HEIGHT - (menuBarSize / 2), menuBarSize, menuBarSize, "load"));

		buttonList.add(new Button(new Image("objectAssets/brick.gif"), Display.WIDTH - (menuBarSize / 2),
				Display.HEIGHT - (menuBarSize / 2), menuBarSize, menuBarSize, "brick"));
		buttonList.add(new Button(new Image("objectAssets/cover.gif"), Display.WIDTH - 3 * (menuBarSize / 2),
				Display.HEIGHT - (menuBarSize / 2), menuBarSize, menuBarSize, "cover"));

		grid = new Objects.Grid((int) (Display.WIDTH / cellSize), (int) ((Display.HEIGHT - menuBarSize) / cellSize),
				cellSize);
		world = new Objects.World();
		world.populateWorld(grid);
		System.out.println(world.getSize());

	}

	public void loadLevel() {
		System.out.println("Loading...");
		FileChooser dialog = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Sniper Level File(*.sniperLvl)",
				"*.sniperLvl");

		dialog.getExtensionFilters().add(extFilter);
		dialog.setTitle("Load Level File");
		dialog.setInitialDirectory(new File(System.getProperty("user.dir") + "/Levels"));
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

	public void saveLevel() {
		FileChooser dialog = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Sniper Level File(*.sniperLvl)",
				"*.sniperLvl");
		dialog.getExtensionFilters().add(extFilter);
		dialog.setTitle("Save Level File");
		dialog.setInitialDirectory(new File(System.getProperty("user.dir") + "/Levels"));
		File file = dialog.showSaveDialog(Display.theStage);
		if (file != null) {
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(file));
				String levelString = grid.rows + "," + grid.cols + "\n";
				for (int i = 0; i < grid.cols; i++) {
					for (int j = 0; j < grid.rows; j++) {
						levelString += (grid.gridCode[j][i]);
					}
					levelString += '\n';
				}
				writer.write(levelString);
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void handleClick(double x, double y) {
		int c = (int) (y / cellSize);
		int r = (int) (x / cellSize);
		grid.gridCode[r][c] = selectedBlockType;
		world.addObject(grid, r, c);
	}

	public void handleMessage(String message) {
		System.out.println(message);
		if (message.equals("save"))
			saveLevel();
		if (message.equals("load"))
			loadLevel();
		if (message.equals("brick"))
			selectedBlockType = BrickBlock.gridCode;
		if (message.equals("cover"))
			selectedBlockType = CoverBlock.gridCode;
	}

}
