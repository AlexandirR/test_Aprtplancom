import java.util.Scanner;

public class ReverseString {

    private String myString;
    private static final int FIRST_MEASURE = 1000;
    private static final int SECOND_MEASURE = 10000;
    private static final int THIRD_MEASURE = 100000;

    public ReverseString(String innerString) {
        this.myString = innerString;
    }

    public String getMyString() {
        return myString;
    }

    public void reverse() {
        StringBuilder reverseString = new StringBuilder(myString);
        Character t = null;
        for (int i = 0; i < reverseString.length() / 2; ++i) {
            t = reverseString.charAt(i);
            reverseString.setCharAt(i, reverseString.charAt(reverseString.length() - i - 1));
            reverseString.setCharAt(reverseString.length() - i - 1, t);
        }
        myString = reverseString.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write your string:");
        String myString = scanner.nextLine();
        ReverseString reverseString = new ReverseString(myString);

        System.out.println("Your string: " + reverseString.myString);
        reverseString.reverse();
        System.out.println("Reverse string: " + reverseString.getMyString());

        long t0 = System.nanoTime();
        for (int i = 0; i < FIRST_MEASURE; ++i) {
            reverseString.reverse();
        }
        long t = System.nanoTime() - t0;
        System.out.println("time of " + FIRST_MEASURE + " repeat in milliseconds: " + t / 1e6);

        t0 = System.nanoTime();
        for (int i = 0; i < SECOND_MEASURE; ++i) {
            reverseString.reverse();
        }
        t = System.nanoTime() - t0;
        System.out.println("time of " + SECOND_MEASURE + " repeat in milliseconds: " + t / 1e6);

        t0 = System.nanoTime();
        for (int i = 0; i < THIRD_MEASURE; ++i) {
            reverseString.reverse();
        }
        t = System.nanoTime() - t0;
        System.out.println("time of " + THIRD_MEASURE + " repeat in milliseconds: " + t / 1e6);
    }
}
