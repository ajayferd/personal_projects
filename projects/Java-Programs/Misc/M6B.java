import java.util.*;

public class M6B {
    public static Scanner sc = new Scanner(System.in);

    public static void fillArray(int array[])
    {
        System.out.println("Enter " +  array.length + " integers separated by spaces:");
        for(int i = 0; i < array.length; i++)
        {
            array[i] = sc.nextInt(); 
        }
    }
    public static void printArray(int array[])
    {
        for(int i = 0; i < array.length; i++)
        {
            
        }
    }

    public static double[] averageArrays(int array1[], int array2[])
    {
        System.out.println("Your output array is");
        double[] array3 = new double[array1.length];
        for(int i = 0; i < array1.length; i++)
        {
            array3[i] = (array1[i] + array2[i]) / 2;
        }
        System.out.println(Arrays.toString(array3));
        return array3;
    }
    public static void main(String[] args)
    {
        System.out.println("How many elements in your arrays [up to 25]: ");
        
        int size = sc.nextInt();
        int[] array1 = new int[size];
        int[] array2 = new int[size];
        fillArray(array1);
        fillArray(array2);
        System.out.println(Arrays.toString(array1));
        System.out.println(Arrays.toString(array2));
        averageArrays(array1, array2);

        
    }
    
}
