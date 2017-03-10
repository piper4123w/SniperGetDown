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
	double headX, headY, headR;
	int playerNumber;
	boolean touchingGround = false;
	boolean touchingLeftWall = false;
	boolean touchingRightWall = false;
	boolean touchingCieling = false;

	ArrayList<GameObject> collisions;

	public final static int TERMINAL_VELOCITY = 15;

	public Robber(int playerNum, double x, double y, double s) {
		sprites = new ArrayList<Image>(5);
		playerNumber = playerNum;
		switch (playerNumber) {
		case 1:
			loadSprites("G_");
			break;
		case 2:
			loadSprites("B_");
			break;
		}
		this.x = x;
		this.y = y;

		this.width = s;
		this.height = 2 * s;
		boundingBox = new BoundingBox(this.x, this.y + (this.height / 2), this.width, this.height / 2);

		this.scalef = 1;
	}

	public void update(ArrayList<String> input, World world, GraphicsContext gc) {
		setCollisions(world);

		if (playerNumber == 1) {
			img = sprites.get(0);
			if (input.contains("W"))
				jump();
			else if (input.contains("S"))
				slide();
			else if (input.contains("D"))
				right();
			else if (input.contains("A"))
				left();
			else
				stop();
		}
		super.update();
		boundingBox = new BoundingBox(x - width / 2, y, width, height / 2);
		if (Display.Display.debug) {
			if (boundingBox != null) {
				gc.setStroke(Color.BLACK);
				gc.strokeRect(boundingBox.getMinX(), boundingBox.getMinY(), boundingBox.getWidth(),
						boundingBox.getHeight());
			}
		}
		super.render(gc);
	}

	private void setCollisions(World world) {
		collisions = new ArrayList<GameObject>();
		for (GameObject o : world.worldArray) {
			if (o.boundingBox.intersects(boundingBox))
				collisions.add(o);
		}

		// determine if on ground
		touchingGround = false;
		touchingLeftWall = false;
		for (GameObject o : collisions) {
			if (o.boundingBox.getMinY() >= boundingBox.getMaxY() && o instanceof BrickBlock)
				touchingGround = true;
			if (o.boundingBox.getMaxX() <= boundingBox.getMinX() && o instanceof BrickBlock)
				touchingLeftWall = true;
		}
		if (touchingGround)
			dy = 0;
		else {
			if (dy < TERMINAL_VELOCITY) // terminal velocity falling
				dy++;
		}
	}

	private void stop() {
		if (dx > 0)
			dx--;
		if (dx < 0)
			dx++;
	}

	private void left() {
		if (dx > -10)
			dx--;
	}

	private void right() {
		if (dx < 10)
			dx++;
	}

	private void slide() {
		System.out.println("Slide");
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
