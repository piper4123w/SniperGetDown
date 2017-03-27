package Scenes;

import java.io.File;
import java.util.ArrayList;

import Actor.Actor;
import Actor.Explosion;
import Actor.Robber;
import Display.Display;
import Objects.Bank;
import Objects.GameObject;
import Objects.Tnt;
import javafx.scene.paint.Color;

public class PlayScene extends Scene {
	final double menuBarSize = 50;

	public PlayScene(String substring, int i) {
		robberList = new ArrayList<Robber>(i);
		File file = new File(System.getProperty("user.dir") + "/Levels/" + substring);
		System.out.println("Players: " + i);
		grid = new Objects.Grid();
		grid.fileToGrid(file);
		world = new Objects.World();
		world.populateWorld(getGrid());

		addPlayers(i);
	}

	public PlayScene(Objects.Grid g, int i) {
		robberList = new ArrayList<Robber>(i);
		grid = g;
		world = new Objects.World();
		world.populateWorld(g);
		addPlayers(i);
	}

	public void initChildScene() {
		backGround = Color.DARKCYAN;
		buttonList.add(new Button("Quit", Display.WIDTH - ((menuBarSize * 3) / 2), Display.HEIGHT - (menuBarSize / 2),
				"SCENE:main", menuBarSize * 3, menuBarSize));

	}

	public void addPlayers(int i) {
		Bank b = (Bank) world.getFirstObject(Bank.class);
		robberList.add(new Robber(1, b.x, b.y, grid.cellSize));
		if (i > 2)
			robberList.add(new Robber(2, b.x, b.y, grid.cellSize));
		if (i > 3)
			robberList.add(new Robber(3, b.x, b.y, grid.cellSize));
		System.out.println(i + "players created");
	}

	public void handleMessage(String message, boolean dragging) {
		if (Display.debug)
			System.out.println(message);
	}

	public void handleClick(double x, double y) {
		int living = 0;
		boolean missed = false;
		for (GameObject o : world.worldArray) {
			if (o.boundingBox.contains(x, y)) {
				if (o instanceof Tnt) {
					for (Robber r : robberList) {
						double distanceX = Math.pow(r.x - o.x, 2);
						double distanceY = Math.pow(r.y - o.y, 2);
						if (Math.sqrt(distanceX + distanceY) <= ((Tnt) o).blastRadius)
							r.dead = true;
					}
					if (otherActorList == null)
						otherActorList = new ArrayList<Actor>();
					otherActorList.add(new Explosion(o.x, o.y, ((Tnt) o).blastRadius));
					((Tnt) o).blowUp();

				}
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
