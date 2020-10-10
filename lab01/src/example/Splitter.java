package src.example;

import java.util.Scanner;
public class Splitter {

    public static void main(String[] args){
        System.out.println("Enter a sentence specified by spaces only: ");
        // Add your code
        Scanner scan = new Scanner(System.in);
        while(scan.hasNextLine()) {
            String str = scan.next();
            System.out.println(str);
        }
        scan.close();
    }
}
