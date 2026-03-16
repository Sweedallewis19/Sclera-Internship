public class Vehicle {

    String modeOfTransportation;
    int numberOfPassengers;
    String color;
    boolean hasWheels = true;

    Vehicle(String mode, int passengers, String color) {
        this.modeOfTransportation = mode;
        this.numberOfPassengers = passengers;
        this.color = color;
    }

    void printDetails() {
        System.out.println("Mode of Transportation: " + modeOfTransportation);
        System.out.println("Number of Passengers: " + numberOfPassengers);
        System.out.println("Color: " + color);
        System.out.println("Has Wheels: " + hasWheels);
    }
}
