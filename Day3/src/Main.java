public class Main {

    public static void main(String[] args) {

        Car car = new Car("Tesla", 5, "Gray");
        Boat boat = new Boat("Titanic 2.0", 10, "Black");

        System.out.println("Car Details:");
        System.out.println("Model: " + car.modelName);
        car.printDetails();

        System.out.println("\nBoat Details:");
        System.out.println("Name: " + boat.name);
        boat.printDetails();
    }
}
