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
	String[] uris = { "idle.png", "jump.png", "running1.png", "running2.png", "running3.png", "crawl1.png",
			"crawl2.png", "dive.png", "idle_blink.png" };
	int spriteCycler = 4;

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

		img = sprites.get(1);

		this.x = x;
		this.y = Math.round(y);

		this.width = s * 0.9;
		this.height = 2 * s * 0.9;

		// body bounding box used for detecting walking/jumping/colliding with
		// game objects
		boundingBox = new BoundingBox(this.x, this.y - (height / 2), this.width, this.height);

		this.scalef = 1;
	}

	// updates robber based on player inputs, collisions, and velocities
	public void update(ArrayList<String> input, World world, GraphicsContext gc) {
		setCollisions(world);
		// sets up a list of current and future collisions

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
			if (sliding || crawling) {
				boundingBox = new BoundingBox(x - height / 2, y, height, height / 2);
				futureBox = new BoundingBox((x - height / 2) + dx, y + dy, height, height / 2);
			} else {
				boundingBox = new BoundingBox(x - width / 4, y, width / 2, height / 2);
				futureBox = new BoundingBox((x - width / 4) + dx, y + dy, width / 2, height / 2);
			}

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

		for (GameObject o : world.worldArray) {
			// currently hitting or going to be hitting on next update
			if (o.boundingBox.intersects(boundingBox) || o.boundingBox.intersects(futureBox))
				collisions.add(o);
		}
		// System.out.println(collisions.toString());
		// determine logic booleans
		touchingGround = false;
		touchingRightWall = false;
		touchingLeftWall = false;
		for (GameObject o : collisions) {
			// TODO: figure out how to make robbers not be able to climb walls

			// if future box will collide with the ground
			if (o instanceof BrickBlock && o.boundingBox.getMinY() >= boundingBox.getMaxY()
					&& o.boundingBox.intersects(futureBox)) {
				if (!ducking)
					y = (o.boundingBox.getMinY() - boundingBox.getHeight() / 2);
				else
					y = (o.boundingBox.getMinY() - boundingBox.getHeight());
				dy = 0;
				touchingGround = true;
			}

			// if future box hits ceiling
			if (o instanceof BrickBlock && o.boundingBox.getMaxY() <= boundingBox.getMinY()
					&& o.boundingBox.intersects(futureBox)) {
				y = (o.boundingBox.getMaxY() + boundingBox.getHeight() / 2);
				dy = 0;
			}

			// right wall
			if (o instanceof BrickBlock && o.boundingBox.getMinX() >= boundingBox.getMaxX()
					&& o.boundingBox.getMinX() <= futureBox.getMaxX()
					&& o.boundingBox.getMinY() != boundingBox.getMaxY()
					&& o.boundingBox.getMaxY() != boundingBox.getMinY()) {
				x = o.boundingBox.getMinX() - (width / 4);
				dx = 0;
				touchingRightWall = true;
			}

			// left wall
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

	// handles logic when coming to a stop/no movement inputs
	private void stop() {
		if (dx > 0) {
			if (dx - 1 > 0) {
				dx--;
				runningSprite();
			} else {
				dx = 0;
				img = sprites.get(0);
			}
		} else if (dx < 0) {
			if (dx + 1 < 0) {
				dx++;
				runningSprite();
			} else {
				dx = 0;
				if (dy == 0)
					img = sprites.get(0);
			}
		} else {
			if (dy == 0)
				img = sprites.get(0);
		}
		if (ducking && dx == 0) {
			crawling = sliding = false;
		}
	}

	private void left() {
		dir = "left";
		if (!ducking) {
			runningSprite();
			if (dx > -10)
				dx--;
		} else {
			if (dx > -2) {
				dx--;
				sliding = false;
				crawling = true;
				crawlingSprite();
			} else {
				dx += 0.25;
				sliding = true;
				crawling = false;
			}
		}

	}

	private void right() {
		dir = "right";
		if (!ducking) {
			runningSprite();
			if (dx < 10)
				dx++;
		} else {
			if (dx < 2) {
				dx++;
				sliding = false;
				crawling = true;
				crawlingSprite();
			} else {
				sliding = true;
				crawling = false;
				dx -= 0.25;
			}
		}
	}

	private void crawlingSprite() {
		spriteCycler++;
		if (spriteCycler / 2 > 1)
			spriteCycler = 0;
		img = sprites.get(5 + spriteCycler / 2);

	}

	private void runningSprite() {
		spriteCycler++;
		if (spriteCycler / 2 > 2)
			spriteCycler = 0;
		img = sprites.get(2 + spriteCycler / 2);

	}

	private void duck() {
		ducking = true;
	}

	private void jump() {
		if (touchingGround) {
			dy -= 10;
			img = sprites.get(1);
		}
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
