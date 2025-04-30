package com.pluralsight;

import static com.pluralsight.ColorCodes.*;

public class StyledMenus {

    public static void styledTitle(String title){
        String line = BLUE + "=".repeat(title.length() + 10) + RESET;
        System.out.println(line);
        System.out.printf(GREEN_BACKGROUND + "     %s%n", title + "     " + RESET);
        System.out.println(line);
    }

    public static void styledBoxHeader(String header){

    }
}
