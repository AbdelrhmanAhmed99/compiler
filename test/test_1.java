package test;

import java.util.Scanner;

public class test_1 {
    public static void main(String[] args) {

        int year;    // holds a year

        // Create a Scanner object for keyboard input.

        // Get the year.
        year = 2020;

        // Determine whether the year is leap year.
        if (year % 4 == 0)
        {
            if (year % 100 == 0)
            {
                if (year % 400 == 0)
                {
                    System.out.println("A leap year");
                }
                else
                {
                    System.out.println("Not a leap year");
                }
            }
            else
            {
                System.out.println("A leap year");
            }
        }
        else
        {
            System.out.println("Not a leap year");
        }
    }
}
