//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.Scanner;

public class PrimeNumbers {

    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter N: ");
        int N=sc.nextInt();
        for(int i=2;i<=N;i++) {
            if(isPrime(i)) {
                System.out.print(i +" ");
            }
        }
    }

    static boolean isPrime(int num) {
        if(num<=1){
            return false;
        }

        for (int i=2;i<= Math.sqrt(num);i++) {
            if(num % i==0)
                return false;
        }
        return true;
    }
}

