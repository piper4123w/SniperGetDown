/*
 * Author: Kyle Lawson
 * 
 * Description: Subclass for robber actor. Handles movement based on keyboard inputs. As well as collisions with items in game
 */

package Actor;

import java.util.ArrayList;

import Objects.BrickBlock;
import Objects.GameObject;
import Objects.World;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.BoundingBox;

public class Robber extends Actor {
	ArrayList<Image> sprites;
	String[] uris = { "idle_blink.png", "idle.png", "running1.png", "running2.png", "running3.png" };

	String UP, DOWN, LEFT, RIGHT;
	double headX, headY, headR;
	int playerNumber;
	boolean touchingGround = false;
	boolean touchingLeftWall = false;
	boolean touchingRightWall = false;

	boolean ducking = false;
	boolean sliding = false;
	boolean crawling = false;

	ArrayList<GameObject> collisions;

	BoundingBox futureBox;
	BoundingBox bodyHitBox;

	public final static int TERMINAL_VELOCITY = 15;

	public Robber(int playerNum, double x, double y, double s) {
		sprites = new ArrayList<Image>(5);
		playerNumber = playerNum;
		switch (playerNumber) { // blue or green character
		case 1:
			loadSprites("G_");
			UP = "W";
			DOWN = "S";
			LEFT = "A";
			RIGHT = "D";
			break;
		case 2:
			loadSprites("B_");
			UP = "NUMPAD8";
			DOWN = "NUMPAD5";
			LEFT = "NUMPAD4";
			RIGHT = "NUMPAD6";
			break;
		}
		this.x = x;
		this.y = Math.round(y);

		this.width = s;
		this.height = 2 * s;

		// body bounding box used for detecting walking/jumping/colliding with
		// game objects
		boundingBox = new BoundingBox(this.x, this.y - (height / 2), this.width, this.height);

		this.scalef = 1;
	}

	// updates robber based on player inputs, collisions, and velocities
	public void update(ArrayList<String> input, World world, GraphicsContext gc) {
		setCollisions(world);
		// sets robber collision booleans for movement logic

		img = sprites.get(0);
		ducking = false;
		if (input.contains(UP) && !input.contains(DOWN) && touchingGround)
			jump();
		else if (input.contains(DOWN) && !input.contains(UP))
			duck();
		if (input.contains(RIGHT) && !input.contains(LEFT) && !touchingRightWall)
			right();
		else if (input.contains(LEFT) && !input.contains(RIGHT) && !touchingLeftWall)
			left();
		else
			stop();
		if (!touchingGround && dy < TERMINAL_VELOCITY)
			dy++;
		super.update();

		if (!ducking) {
			boundingBox = new BoundingBox(x - width / 4, y - (height / 2), width / 2, height);
			futureBox = new BoundingBox((x - width / 4) + dx, (y - (height / 2)) + dy, width / 2, height);
		} else {
			boundingBox = new BoundingBox(x - width / 4, y, width / 2, height / 2);
			futureBox = new BoundingBox((x - width / 4) + dx, y + dy, width / 2, height / 2);
		}
		if (Display.Display.debug) {
			if (boundingBox != null) {
				gc.setStroke(Color.BLACK);
				gc.strokeRect(boundingBox.getMinX(), boundingBox.getMinY(), boundingBox.getWidth(),
						boundingBox.getHeight());
				gc.setStroke(Color.RED);
				gc.strokeLine(boundingBox.getMinX(), boundingBox.getMaxY(), boundingBox.getMaxX(),
						boundingBox.getMaxY());

				gc.setStroke(Color.BLUE);
				gc.strokeRect(futureBox.getMinX(), futureBox.getMinY(), futureBox.getWidth(), futureBox.getHeight());
			}
		}
		super.render(gc);
	}

	// sets collision booleans for game logic
	private void setCollisions(World world) {
		collisions = new ArrayList<GameObject>();
		ArrayList<GameObject> futureCollisions = new ArrayList<GameObject>();
		// list of game objects robber is touching or going to touch at current
		// speed
		for (GameObject o : world.worldArray) {
			if (o.boundingBox.intersects(boundingBox))
				collisions.add(o);
			if (o.boundingBox.intersects(futureBox))
				futureCollisions.add(o);
		}
		// System.out.println(collisions.toString());
		// determine logic booleans
		touchingGround = false;
		touchingRightWall = false;
		touchingLeftWall = false;
		for (GameObject o : futureCollisions) {
			// if future box will collide with the ground
			if (o instanceof BrickBlock && o.boundingBox.getMinY() >= boundingBox.getMaxY()
					&& o.boundingBox.getMinY() <= futureBox.getMaxY()) {
				if (!ducking)
					y = (o.boundingBox.getMinY() - boundingBox.getHeight() / 2);
				else
					y = (o.boundingBox.getMinY() - boundingBox.getHeight());
				dy = 0;
				touchingGround = true;
			}
			if (o instanceof BrickBlock && o.boundingBox.getMaxY() <= boundingBox.getMinY()
					&& o.boundingBox.getMaxY() >= futureBox.getMinY()) {
				y = (o.boundingBox.getMaxY() + boundingBox.getHeight() / 2);
				dy = 0;
			}
			if (o instanceof BrickBlock && o.boundingBox.getMinX() >= boundingBox.getMaxX()
					&& o.boundingBox.getMinX() <= futureBox.getMaxX()
					&& o.boundingBox.getMinY() != boundingBox.getMaxY()
					&& o.boundingBox.getMaxY() != boundingBox.getMinY()) {
				x = o.boundingBox.getMinX() - (width / 4);
				dx = 0;
				touchingRightWall = true;
			}
			if (o instanceof BrickBlock && o.boundingBox.getMaxX() <= boundingBox.getMinX()
					&& o.boundingBox.getMaxX() >= futureBox.getMinX()
					&& o.boundingBox.getMinY() != boundingBox.getMaxY()
					&& o.boundingBox.getMaxY() != boundingBox.getMinY()) {
				x = o.boundingBox.getMaxX() + (width / 4);
				dx = 0;
				touchingLeftWall = true;
			}

		}

	}

	private void stop() {
		if (dx > 0) {
			if (dx - 1 > 0)
				dx--;
			else
				dx = 0;
		}
		if (dx < 0) {
			if (dx + 1 < 0)
				dx++;
			else
				dx = 0;
		}
	}

	private void left() {
		if (!ducking) {
			if (dx > -10)
				dx--;
		} else {
			if (dx > -2) {
				dx--;
				sliding = false;
				crawling = true;
			} else {
				dx += 0.25;
				sliding = true;
				crawling = false;
			}
		}

	}

	private void right() {
		if (!ducking) {
			if (dx < 10)
				dx++;
		} else {
			if (dx < 2) {
				dx++;
				sliding = false;
				crawling = true;
			} else {
				sliding = true;
				crawling = false;
				dx -= 0.25;
			}
		}
	}

	private void duck() {
		ducking = true;
	}

	private void jump() {
		if (touchingGround)
			dy -= 10;
	}

	public void loadSprites(String s) {
		for (String u : uris) {
			sprites.add(new Image("CharacterAssets/" + s + u));
		}
	}

	public double getHeadX() {
		return headX;
	}

	public double getHeadY() {
		return headY;
	}

	public double getHeadR() {
		return headR;
	}

}
