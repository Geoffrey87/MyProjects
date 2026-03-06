package org.example;

public class Main {
    public static void main(String[] args) {
        String str = "radar";

        boolean isPalindrome = checkPalidrome(str);
        if (isPalindrome){
            System.out.println( str + " is palindrome");
        }
        else {
            System.out.println(str + " is not palindrome");
        }
    }

    public static boolean checkPalidrome (String str){
        char[] array = str.toCharArray();

        int left = 0;
        int right = array.length - 1;

        while (left < right) {
            if (array[left] != array[right]) {
                return false;
            } else {
                left++;
                right--;
            }
        }
        return true;

    }
}