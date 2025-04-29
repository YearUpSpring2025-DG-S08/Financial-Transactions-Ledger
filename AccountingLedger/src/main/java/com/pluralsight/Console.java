package com.pluralsight;

import java.util.Scanner;

public class Console {
    Scanner scanner = new Scanner(System.in);

    public int promptForInt(String prompt){

        int result;
        while(true){
            try{
                System.out.print(prompt);
                result = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (Exception e) {
                System.out.println("Not a valid entry. Please try again!");
                scanner.next();
            }
        }
        return result;
    }

    public double promptForDouble(String prompt){
        double result;
        while(true){
            try{
                System.out.print(prompt);
                result = scanner.nextDouble();
                scanner.nextLine();
                break;
            } catch (Exception e) {
                System.out.println("Not a valid entry. Please try again!");
                scanner.next();
            }
        }
        return result;
    }

    public String promptForString(String prompt){
        System.out.println(prompt);
        String input = scanner.nextLine().trim().toLowerCase();
        // edits the return input so that the first letter in the input would be capitalized
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}
