package Scenes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import Display.Display;
import Objects.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

public class EditorScene extends Scene {

	final double menuBarSize = 50;
	final double cellSize = 20;

	char selectedBlockType = 'B';

	public void initChildScene() {
		// inits buttons for the scene, as well as background and any necessarry
		// data structs

		backGround = Color.DARKSLATEGRAY;
		buttonList.add(new Button(new Image("menuItems/save.gif"), menuBarSize / 2, Display.HEIGHT - (menuBarSize / 2),
				menuBarSize, menuBarSize, "save"));
		buttonList.add(new Button(new Image("menuItems/load.png"), 3 * (menuBarSize / 2),
				Display.HEIGHT - (menuBarSize / 2), menuBarSize, menuBarSize, "load"));
		buttonList.add(new Button(new Image("menuItems/play.png"), 5 * (menuBarSize / 2),
				Display.HEIGHT - (menuBarSize / 2), menuBarSize, menuBarSize, "SCENE:playLevel,GRID"));

		// -----Buttons from Right-----
		int buttonFromRight = -1;
		buttonList.add(new Button("del", Display.WIDTH - (buttonFromRight += 2) * (menuBarSize / 2),
				Display.HEIGHT - (menuBarSize / 2), "remove", menuBarSize, menuBarSize));
		buttonList.add(new Button(new Image("objectAssets/brick.gif"),
				Display.WIDTH - (buttonFromRight += 2) * (menuBarSize / 2), Display.HEIGHT - (menuBarSize / 2),
				menuBarSize, menuBarSize, "brick"));
		buttonList.add(new Button(new Image("objectAssets/cover.gif"),
				Display.WIDTH - (buttonFromRight += 2) * (menuBarSize / 2), Display.HEIGHT - (menuBarSize / 2),
				menuBarSize, menuBarSize, "cover"));
		buttonList.add(new Button(new Image("objectAssets/Bank.png"),
				Display.WIDTH - (buttonFromRight += 2) * (menuBarSize / 2), Display.HEIGHT - (menuBarSize / 2),
				menuBarSize, menuBarSize, "bank"));
		buttonList.add(new Button(new Image("objectAssets/Van.png"),
				Display.WIDTH - (buttonFromRight += 2) * (menuBarSize / 2), Display.HEIGHT - (menuBarSize / 2),
				menuBarSize, menuBarSize, "van"));

		grid = new Objects.Grid((int) (Display.WIDTH / cellSize), (int) ((Display.HEIGHT - menuBarSize) / cellSize),
				cellSize);
		world = new Objects.World();
		world.populateWorld(grid);
		if (Display.debug)
			System.out.println(world.getSize());

	}

	public void loadLevel() { // opens file dialog for loading a new level
		if (Display.debug)
			System.out.println("Loading...");
		FileChooser dialog = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Sniper Level File(*.sniperLvl)",
				"*.sniperLvl");

		dialog.getExtensionFilters().add(extFilter);
		dialog.setTitle("Load Level File");
		dialog.setInitialDirectory(new File(System.getProperty("user.dir") + "/Levels"));
		File file = dialog.showOpenDialog(Display.theStage);
		grid.fileToGrid(file);
		world = new Objects.World();
		world.populateWorld(grid);
	}

	public void saveLevel() { // opens a file dialog for saving the created
								// level
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
		if (r < grid.rows - 1 && c < grid.cols - 1 && r > 0 && c > 0) {
			if (selectedBlockType == Bank.gridCode && world.constains(Bank.class)) {
				grid.remove(Bank.gridCode);
				world.removeAll(Bank.class);
			}
			if (selectedBlockType == Van.gridCode && world.constains(Van.class)) {
				grid.remove(Van.gridCode);
				world.removeAll(Van.class);
			}
			grid.gridCode[r][c] = selectedBlockType;
			if (selectedBlockType == 0)
				world.remove(grid,r,c);
			else
				world.addObject(grid, r, c);
		}
	}

	public void handleMessage(String message, boolean dragging) {
		if (Display.debug)
			System.out.println(message);
		if (!dragging) {
			if (message.equals("save"))
				saveLevel();
			if (message.equals("load"))
				loadLevel();
		}

		if (message.equals("brick"))
			selectedBlockType = BrickBlock.gridCode;
		if (message.equals("cover"))
			selectedBlockType = CoverBlock.gridCode;
		if (message.equals("empty"))
			selectedBlockType = ' ';
		if (message.equals("bank"))
			selectedBlockType = Bank.gridCode;
		if (message.equals("van"))
			selectedBlockType = Van.gridCode;
		if (message.equals("remove"))
			selectedBlockType = 0;
	}

}
