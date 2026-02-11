import java.util.*;

public class Main {

    public static void main(String[] args) {

        List<String> students = new ArrayList<>();
        students.add("Alice");
        students.add("Bob");
        students.add("Alice"); // duplicate allowed

        System.out.println("List:");
        for (String name : students)
            System.out.println(name);

        System.out.println();

        Set<String> uniqueStudents = new HashSet<>(students);

        System.out.println("Set:");
        for (String name : uniqueStudents)
            System.out.println(name);

        System.out.println();

        Map<String, Integer> marks = new HashMap<>();
        marks.put("Alice", 90);
        marks.put("Bob", 85);

        System.out.println("Map:");
        for (Map.Entry<String, Integer> entry : marks.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}
