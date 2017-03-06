package Scenes;

import java.io.File;

import javafx.scene.paint.Color;

public class PlayScene extends Scene {

	Objects.Grid grid;

	public PlayScene(String substring) {
		File file = new File(System.getProperty("user.dir") + "/Levels/" + substring);
		System.out.println(file.getAbsolutePath());
		grid = new Objects.Grid();
		grid.fileToGrid(file);
		world = new Objects.World();
		world.populateWorld(grid);
	}

	public PlayScene(Objects.Grid g) {
		grid = g;
		world = new Objects.World();
		world.populateWorld(grid);
	}

	public void initChildScene() {
		backGround = Color.AQUAMARINE;
	}

}
