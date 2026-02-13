import java.util.*;
import java.util.stream.*;

public class Main {

    public static void main(String[] args) {

        List<Integer> numbers = List.of(1, 2, 2, 3, 4, 5, 6);

        // Creating a Stream
        System.out.println("Original Numbers:");
        numbers.stream().forEach(System.out::println);

        // Filtering + Mapping + Sorting + Distinct
        System.out.println("\nFiltered, Mapped, Sorted, Distinct:");
        List<Integer> processed = numbers.stream()
                .filter(n -> n > 2)      // Filtering
                .map(n -> n * 2)         // Mapping
                .distinct()              // Unique elements
                .sorted()                // Sorting
                .toList();

        processed.forEach(System.out::println);

        // Slicing (limit & skip)
        System.out.println("\nSlicing (limit 3):");
        numbers.stream()
                .limit(3)
                .forEach(System.out::println);

        // Peek (debugging)
        System.out.println("\nUsing Peek:");
        numbers.stream()
                .filter(n -> n > 3)
                .peek(n -> System.out.println("Filtered: " + n))
                .toList();

        // Simple Reducer (count)
        long count = numbers.stream().count();
        System.out.println("\nCount: " + count);

        // Reduce (sum)
        int sum = numbers.stream()
                .reduce(0, (a, b) -> a + b);
        System.out.println("Sum: " + sum);

        // Collectors (toList)
        List<Integer> collected = numbers.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());
        System.out.println("\nCollected Even Numbers: " + collected);

        // Grouping
        Map<String, List<Integer>> grouped = numbers.stream()
                .collect(Collectors.groupingBy(n -> n % 2 == 0 ? "Even" : "Odd"));
        System.out.println("\nGrouped: " + grouped);

        // Partitioning
        Map<Boolean, List<Integer>> partitioned = numbers.stream()
                .collect(Collectors.partitioningBy(n -> n > 3));
        System.out.println("\nPartitioned (>3): " + partitioned);

        //  Primitive Streams
        System.out.println("\nPrimitive IntStream:");
        IntStream.range(1, 5)
                .forEach(System.out::println);
    }
}
