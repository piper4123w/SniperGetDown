package Scenes;

import java.io.File;

import Display.Display;
import Objects.Bank;
import Objects.Robber;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class PlayScene extends Scene {
	final double menuBarSize = 50;

	public PlayScene(String substring) {
		File file = new File(System.getProperty("user.dir") + "/Levels/" + substring);
		System.out.println(file.getAbsolutePath());
		grid = new Objects.Grid();
		grid.fileToGrid(file);
		world = new Objects.World();
		world.populateWorld(getGrid());
		addPlayers();
	}

	public PlayScene(Objects.Grid g) {
		world = new Objects.World();
		world.populateWorld(g);
	}

	public void initChildScene() {
		backGround = Color.DARKCYAN;
		buttonList.add(new Button("Pause", Display.WIDTH - ((menuBarSize * 3) / 2), Display.HEIGHT - (menuBarSize / 2),
				"pause", menuBarSize * 3, menuBarSize));

	}

	public void addPlayers() {
		Bank b = (Bank) world.getFirstObject(Bank.class);
		world.addObject(new Robber(1, b.x, b.y, getGrid().cellSize));
		System.out.println("Player 1 placed at " + b.x + ',' + b.y);
	}

}
