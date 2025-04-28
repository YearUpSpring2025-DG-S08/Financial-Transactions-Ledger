package com.pluralsight;

import java.util.Scanner;

public class Console {
    Scanner scanner = new Scanner(System.in);

    @SuppressWarnings("unused")
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
        return scanner.nextLine();
    }

}
