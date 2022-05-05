package abdo;

import java.util.Scanner;

public class abdo_1 {
    public static void main(String[] args) {

        int year;    // holds a year

        // Create a Scanner object for keyboard input.

        // Get the year.
        year = 2020;

        // Determine whether the year is leap year.
        if (year % 4 == 0)
        {
		System.out.println("block_0");
            if (year % 100 == 0)
            {
		System.out.println("block_1");
                if (year % 400 == 0)
                {
		System.out.println("block_2");
                    System.out.println("A leap year");
                }
                else
                {
		System.out.println("block_3");
                    System.out.println("Not a leap year");
                }
            }
            else
            {
		System.out.println("block_4");
                System.out.println("A leap year");
            }
        }
        else
        {
		System.out.println("block_5");
            System.out.println("Not a leap year");
        }
    }
}
