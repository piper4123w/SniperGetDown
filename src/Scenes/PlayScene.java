package Scenes;

import java.io.File;
import java.util.ArrayList;

import Actor.Robber;
import Display.Display;
import Objects.Bank;
import Objects.BrickBlock;
import Objects.GameObject;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class PlayScene extends Scene {
	final double menuBarSize = 50;

	public PlayScene(String substring) {
		robberList = new ArrayList<Robber>(2);
		File file = new File(System.getProperty("user.dir") + "/Levels/" + substring);
		System.out.println(file.getAbsolutePath());
		grid = new Objects.Grid();
		grid.fileToGrid(file);
		world = new Objects.World();
		world.populateWorld(getGrid());
		addPlayers();
	}

	public PlayScene(Objects.Grid g) {
		robberList = new ArrayList<Robber>(2);
		grid = g;
		world = new Objects.World();
		world.populateWorld(g);
		addPlayers();
	}

	public void initChildScene() {
		backGround = Color.DARKCYAN;
		buttonList.add(new Button("Pause", Display.WIDTH - ((menuBarSize * 3) / 2), Display.HEIGHT - (menuBarSize / 2),
				"pause", menuBarSize * 3, menuBarSize));

	}

	public void addPlayers() {
		Bank b = (Bank) world.getFirstObject(Bank.class);
		robberList.add(new Robber(1, b.x, b.y, grid.cellSize));
		robberList.add(new Robber(2, b.x, b.y, grid.cellSize));
	}

	public void handleMessage(String message, boolean dragging) {
		if (Display.debug)
			System.out.println(message);

		if (!dragging) {
			if (message.equals("pause"))
				while (true)
					;
		}
	}

	public void handleClick(double x, double y) {
		int living = 0;
		boolean missed = false;
		for (GameObject o : world.worldArray) {
			if (o.boundingBox.contains(x, y)) {
				missed = true;
				if (Display.debug)
					System.out.println("Hit: " + o.toString());
			}
		}
		for (Robber r : robberList) {
			if (!missed)
				r.checkShot(x, y);
			if (!r.dead)
				living++;
		}

		if (living == 0) {
			gameOver = true;
			sniperWin = true;
			if (Display.debug)
				System.out.println("GameOver");
		}

	}

}
