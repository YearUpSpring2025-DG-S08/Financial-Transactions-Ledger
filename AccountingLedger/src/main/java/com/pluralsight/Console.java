package com.pluralsight;

import java.util.Scanner;

public class Console {
    Scanner scanner = new Scanner(System.in);

    public int promptForInt(String prompt){

        int result = -1;
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
        double result = -1;
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
        String result = scanner.nextLine();
        return result;
    }

}
