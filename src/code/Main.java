package code;

import java.util.Random;

public class Main {

	public static void main(String[] args) {
		Random randomNumber = new Random();
		Route route = new Route(new Point(10, 10));
		Line line = new Line(107, route);
		Bus busOne = new Bus("bus1", line);
		Bus busTwo = new Bus("bus2", line);
		Bus busThree = new Bus("bus3", line);
		Bus busFour = new Bus("bus4", line);
		
		busOne.move(randomNumber.nextInt(10), 0);
		System.out.print("Start: ");
		System.out.println("(" + busOne.getPosition().getX() + ", " + busOne.getPosition().getY() + ")");
		for (int i = 0; i < 60; i++){
			boolean moved = false;
			do {
				float x = randomNumber.nextInt(10);
				float y = randomNumber.nextInt(10);
				moved = busOne.move(x, y);
				if (moved)
					System.out.println(x + ", " + y);
			} while (!moved);
			System.out.print(i + ": ");
			System.out.println("(" + busOne.getPosition().getX() + ", " + busOne.getPosition().getY() + ") - " + busOne.getLocation());
		}
	}

}
