public class Main {

    public static void main(String[] args) {

        GenericBox<Integer> intBox = new GenericBox<>(10);
        System.out.println(intBox.getValue());

        GenericBox<String> strBox = new GenericBox<>("Hello");
        System.out.println(strBox.getValue());
    }
}
