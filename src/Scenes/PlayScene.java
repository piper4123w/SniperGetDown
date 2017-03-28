/* Author: Kyle Lawson
 * 
 * Description: Scene playing. handles in game physics and controls
 * 
 */

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

	public void updateChild() {
		chainTnt();

		if (otherActorList != null && !otherActorList.isEmpty()) {
			for (Actor a : otherActorList) {
				a.render(gc);
				a.update();
			}
		}

		setGameState();

	}

	private void setGameState() {
		if (robberList != null && !robberList.isEmpty()) {
			int escapeCount = 0;
			int deadCount = 0;
			for (Robber a : robberList) {
				a.update(input, world, gc);
				if (a.escaped)
					escapeCount++;
				if (a.dead)
					deadCount++;
			}

			if (escapeCount + deadCount >= robberList.size()) {
				gameOver = true;
				if (escapeCount == 0) {
					sniperWin = true;
				} else {
					tieGame = true;
				}
			}
		}
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
				if (o instanceof Tnt && !((Tnt) o).dead) {
					handleExplosions((Tnt) o);
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

	// method chains together Tnt explosions
	private void chainTnt() {
		ArrayList<Tnt> bomblist = new ArrayList<Tnt>();
		for (Actor a : otherActorList) {
			if (a instanceof Explosion) {
				Explosion e = (Explosion) a;
				for (GameObject o : world.worldArray) {
					if (o instanceof Tnt && e.life < 1 && !((Tnt) o).dead) {
						double distanceX = Math.pow(e.x - o.x, 2);
						double distanceY = Math.pow(e.y - o.y, 2);
						if (Math.sqrt(distanceX + distanceY) <= ((Tnt) o).blastRadius)
							bomblist.add((Tnt) o);
					}
				}
			}
		}

		for (Tnt t : bomblist) {
			handleExplosions(t);
		}
	}

	private void handleExplosions(Tnt bomb) {
		// kill robbers in vicinity of bomb
		for (Robber r : robberList) {
			double distanceX = Math.pow(r.x - bomb.x, 2);
			double distanceY = Math.pow(r.y - bomb.y, 2);
			if (Math.sqrt(distanceX + distanceY) <= bomb.blastRadius)
				r.kill();
		}

		// add explosion actor to the other actor list to show animation
		if (otherActorList == null)
			otherActorList = new ArrayList<Actor>();
		otherActorList.add(new Explosion(bomb.x, bomb.y, bomb.blastRadius));

		bomb.blowUp();

	}

}
