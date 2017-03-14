package Objects;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.scene.canvas.GraphicsContext;

public class World {
	public ArrayList<GameObject> worldArray; // list of world objects

	public World() {
		worldArray = new ArrayList<GameObject>();
	}

	public void update() {
		for (GameObject o : worldArray) {
			o.update();
		}
	}

	public void populateWorld(Grid grid) {
		for (int i = 0; i < grid.cols; i++) {
			for (int j = 0; j < grid.rows; j++) {
				double x = j * grid.cellSize;
				double y = i * grid.cellSize;
				addToArray(x, y, grid.cellSize, grid.gridCode[j][i]);
			}
		}
		// debugPrint();
	}

	private void addToArray(double x, double y, double cellSize, char code) {
		// TODO Auto-generated method stub
		switch (code) {
		case BrickBlock.gridCode:
			worldArray.add(new BrickBlock(x, y, cellSize));
			break;
		case CoverBlock.gridCode:
			worldArray.add(new CoverBlock(x, y, cellSize));
			break;
		case Bank.gridCode:
			worldArray.add(new Bank(x, y, cellSize));
			break;
		case Van.gridCode:
			worldArray.add(new Van(x, y, cellSize));
			break;
		}

	}

	public void debugPrint() {
		for (GameObject o : worldArray)
			System.out.println(o.getClass().getName() + " " + o.x + "," + o.y);
	}

	public void drawWorld(GraphicsContext gc) {
		for (GameObject o : worldArray) {
			o.render(gc);
		}
	}

	public int getSize() {
		return worldArray.size();
	}

	public void addObject(Grid grid, int r, int c) {
		double y = c * grid.cellSize;
		double x = r * grid.cellSize;
		addToArray(x, y, grid.cellSize, grid.gridCode[r][c]);
	}

	// checks the world for instances of the object
	public boolean constains(Class<?> cls) {
		boolean exists = false;
		for (GameObject o : worldArray) {
			if (o.getClass() == cls)
				exists = true;
		}
		return exists;
	}

	// removes all instances of objects
	public void removeAll(Class<?> cls) {
		Iterator<GameObject> iter = worldArray.iterator();

		while (iter.hasNext()) {
			GameObject o = iter.next();
			if (o.getClass() == cls)
				iter.remove();
		}

	}

	public void remove(Grid g, int r, int c) {
		double x = (r * g.cellSize) + (g.cellSize / 2);
		double y = (c * g.cellSize) + (g.cellSize / 2);
		ArrayList<GameObject> tmp = new ArrayList<GameObject>();
		for (GameObject o : worldArray) {
			if (o.x != x || o.y != y) {
				tmp.add(o);
				System.out.println("kept " + o.x + ',' + o.y + ':' + x + "," + y);
			} else
				System.out.println("removed " + o.x + ',' + o.y + ':' + x + "," + y);
		}
		worldArray = tmp;

	}

	public GameObject getFirstObject(Class<?> class1) {
		for (GameObject o : worldArray)
			if (o instanceof Bank)
				return o;

		return null;
	}

	public void addObject(GameObject obj) {
		worldArray.add(obj);
	}

}
