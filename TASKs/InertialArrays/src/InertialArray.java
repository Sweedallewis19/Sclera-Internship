import java.util.Scanner;
public class InertialArray {

    public static int isInertial(int[] arr) {
        int max=arr[0];
        boolean Odd=false;

        for(int num:arr) {
            if(num>max)
                max=num;
            if(num % 2!=0)
                Odd =true;
        }

        if(!Odd || max % 2!= 0)
            return 0;

        for(int odd : arr) {
            if(odd % 2 != 0) {
                for(int even : arr) {
                    if(even % 2 == 0 && even !=max && even > odd)
                        return 0;
                }
            }
        }
        return 1;
    }

    public static void main(String[] args) {
        System.out.println("Enter the size of  array:");
        Scanner sc=new Scanner(System.in);
        int n=sc.nextInt();
        int[] arr1=new int[n];
        System.out.println("Enter the values");
        for(int i=0;i<n;i++){
            arr1[i]=sc.nextInt();
        }
        System.out.println(isInertial(arr1));

    }
}
