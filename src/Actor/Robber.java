/*
 * Author: Kyle Lawson
 * 
 * Description: Subclass for robber actor. Handles movement based on keyboard inputs. As well as collisions with items in game
 */

package Actor;

import java.util.ArrayList;

import Objects.BrickBlock;
import Objects.GameObject;
import Objects.Van;
import Objects.World;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.BoundingBox;

public class Robber extends Actor {
	ArrayList<Image> sprites;
	String[] uris = { "idle.png", "jump.png", "running1.png", "running2.png", "running3.png", "crawl1.png",
			"crawl2.png", "dive.png", "idle_blink.png", "ducking.png", "dead.png" };
	int spriteCycler = 4;
	int health = 2;

	String UP, DOWN, LEFT, RIGHT;
	double headX, headY, headR, standingH, standingW;
	int playerNumber;
	boolean touchingGround = false;
	boolean touchingLeftWall = false;
	boolean touchingRightWall = false;

	boolean ducking = false;
	boolean sliding = false;
	boolean crawling = false;
	public boolean dead = false;
	public boolean escaped = false;

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

		standingW = s * 0.9;
		standingH = 2 * s * 0.9;
		headR = standingW;

		// body bounding box used for detecting walking/jumping/colliding with
		// game objects
		boundingBox = new BoundingBox(this.x, this.y - (standingH / 2), this.standingW, this.standingH);

		this.scalef = 1;
	}

	// updates robber based on player inputs, collisions, and velocities
	public void update(ArrayList<String> input, World world, GraphicsContext gc) {
		setCollisions(world);
		// sets up a list of current and future collisions

		if (!dead && !escaped) {
			if (input.contains(UP) && !input.contains(DOWN) && touchingGround)
				jump();
			else if (input.contains(DOWN) && !input.contains(UP) && !ducking)
				ducking = true;

			if (ducking && !input.contains(DOWN)) {
				ducking = false;
				y -= height / 2; // keeps player from falling through floor
			}

			if (input.contains(RIGHT) && !input.contains(LEFT) && !touchingRightWall)
				right();
			else if (input.contains(LEFT) && !input.contains(RIGHT) && !touchingLeftWall)
				left();
			else
				stop();

			// handles height and width of hit boxes based on current stance
			if (!ducking) {
				width = standingW;
				height = standingH;
			} else {
				height = standingW;
				if (sliding || crawling) {
					width = standingH;
				}

				else {
					width = standingW;
					img = sprites.get(9);
				}
			}
		} else {

			if (dead) {
				height = standingW;
				width = standingH;
				img = sprites.get(10);
			} else {
				width = 0;
				height = 0;
				dx = dy = 0;
				x = y = 0;
			}
		}
		if (!touchingGround && dy < TERMINAL_VELOCITY)
			dy++;
		super.update();

		setHeadSphere();

		boundingBox = new BoundingBox(x - width / 2, y - (height / 2), width, height);
		futureBox = new BoundingBox((x + dx) - width / 2, (y + dy) - height / 2, width, height);

		bodyHitBox = new BoundingBox(x - width / 4, y, width / 2, height / 3);

		// debug used for visualizing hit boxes
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

				gc.setStroke(Color.RED);
				gc.strokeOval(headX, headY, headR, headR);
				gc.strokeRect(bodyHitBox.getMinX(), bodyHitBox.getMinY(), bodyHitBox.getWidth(),
						bodyHitBox.getHeight());
			}
		}

		super.render(gc);
	}

	// sets the headSphere location for headshot detection
	private void setHeadSphere() {
		headY = y - height / 2;
		headX = x - width / 2;
		if (ducking && (sliding || crawling)) {
			if (dir.equals("right"))
				headX = x;
		}

	}

	// sets collision booleans for game logic
	private void setCollisions(World world) {
		collisions = new ArrayList<GameObject>();

		for (GameObject o : world.worldArray) {
			// currently hitting or going to be hitting on next update
			if (o.boundingBox.intersects(boundingBox) || o.boundingBox.intersects(futureBox))
				collisions.add(o);
		}

		// determine logic booleans
		touchingGround = false;
		touchingRightWall = false;
		touchingLeftWall = false;
		for (GameObject o : collisions) {
			// TODO: figure out how to make robbers not be able to climb walls

			// if future box will collide with the ground
			if (o instanceof BrickBlock && o.boundingBox.getMinY() >= boundingBox.getMaxY()
					&& o.boundingBox.intersects(futureBox)) {
				y = (o.boundingBox.getMinY() - boundingBox.getHeight() / 2);
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
					&& o.boundingBox.getMinX() <= futureBox.getMaxX()) {
				for (double ytmp = futureBox.getMaxY() - 3; ytmp >= o.boundingBox.getMinY(); ytmp -= 0.1) {
					if (o.boundingBox.contains(futureBox.getMaxX(), ytmp)) {
						x = o.boundingBox.getMinX() - (standingW / 2);
						dx = 0;
						touchingRightWall = true;
						sliding = crawling = false;
						break;
					}
				}

			}

			// left wall
			if (o instanceof BrickBlock && o.boundingBox.getMaxX() <= boundingBox.getMinX()
					&& o.boundingBox.getMaxX() >= futureBox.getMinX()) {
				for (double ytmp = futureBox.getMaxY() - 3; ytmp >= o.boundingBox.getMinY(); ytmp -= 0.1) {
					if (o.boundingBox.contains(futureBox.getMinX(), ytmp)) {
						x = o.boundingBox.getMaxX() + (standingW / 2);
						dx = 0;
						touchingLeftWall = true;
						sliding = crawling = false;
						break;
					}
				}
			}

			if (o instanceof Van && o.boundingBox.contains(boundingBox)) {
				escaped = true;
			}

		}

	}

	// handles logic and sprites when coming to a stop/no movement inputs
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
			if (dx >= -3) {
				dx = -3;
				sliding = false;
				crawling = true;
				crawlingSprite();
			} else {
				dx += 0.25;
				sliding = true;
				crawling = false;
				img = sprites.get(7);
			}
		}

	}

	// when moving right
	private void right() {
		dir = "right";
		if (!ducking) {
			runningSprite();
			if (dx < 10)
				dx++;
		} else {
			if (dx <= 3) {
				dx = 3;
				sliding = false;
				crawling = true;
				crawlingSprite();
			} else {
				sliding = true;
				crawling = false;
				dx -= 0.25;
				img = sprites.get(7);
			}
		}
	}

	// cycles through crawling sprites
	private void crawlingSprite() {
		spriteCycler++;
		if (spriteCycler / 2 > 1)
			spriteCycler = 0;
		img = sprites.get(5 + spriteCycler / 2);

	}

	// cycles through running sprites
	private void runningSprite() {
		spriteCycler++;
		if (spriteCycler / 2 > 2)
			spriteCycler = 0;
		img = sprites.get(2 + spriteCycler / 2);

	}

	// handles jump logic
	private void jump() {
		if (touchingGround) {
			dy -= 10;
			img = sprites.get(1);
		}
	}

	// loads in sprite assets
	public void loadSprites(String s) {
		for (String u : uris) {
			sprites.add(new Image("CharacterAssets/" + s + u));
		}
	}

	// checks whether or not the robber was shot and what type of damage they
	// take
	public void checkShot(double shotX, double shotY) {
		double a = Math.pow(shotX - headX, 2);
		double b = Math.pow(shotY - headY, 2);

		if (Math.sqrt(a + b) < headR) {
			if (Display.Display.debug)
				System.out.println("head shot!");
			health = 0;
		}

		if (bodyHitBox.contains(shotX, shotY)) {
			if (Display.Display.debug)
				System.out.println("Body Shot! HEALTH: " + health);
			health--;
		}

		if (health == 0)
			dead = true;

	}

}
