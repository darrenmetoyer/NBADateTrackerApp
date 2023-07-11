package org.example;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Hello, NBA and Darren!\n");
        Scanner sc = new Scanner(System.in);
        System.out.println("Please Enter A Date (MM/DD/YYYY))\n");
        String date = "12/4/2022";
        String matchesOnAGivenDay = BasketballAPI.matchesOnGivenDate(date);
//        System.out.println(matchesOnAGivenDay);
        ArrayList<String> nbaMatches = BasketballAPI.nbaMatchesFilter(matchesOnAGivenDay);
        System.out.println(BasketballAPI.matchesStringToMap(nbaMatches));
    }
}
