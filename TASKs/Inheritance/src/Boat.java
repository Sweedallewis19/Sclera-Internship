public class Boat extends Vehicle {

    String name;

    Boat(String name, int passengers, String color) {
        super("Water", passengers, color);
        this.name = name;
        this.hasWheels = false;
    }
}
