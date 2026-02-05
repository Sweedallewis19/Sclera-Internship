public class Car extends Vehicle {

    String modelName;

    Car(String modelName, int passengers, String color) {
        super("Road", passengers, color);
        this.modelName = modelName;
    }
}
