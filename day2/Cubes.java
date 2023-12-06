import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Cubes {

    static class Game {
        int id;

        HashMap<String, Integer> map = new HashMap<>() {
            {
                put("red", 0);
                put("blue", 0);
                put("green", 0);
            }
        };

        Game(String input) {
            process(input);
        }

        void process(String input) {
            Scanner scan = new Scanner(input);
            scan.next(); // remove 'Game'
            String idIn = scan.next();
            id = Integer.parseInt(idIn.substring(0, idIn.length() - 1));


            while (scan.hasNext()) {
                int num = scan.nextInt();
                String color = scan.next();
                color = (color.charAt(color.length() - 1) == ',' || color.charAt(color.length() - 1) == ';') ? color.substring(0, color.length() - 1) : color;
                map.replace(color, Math.max(num, map.get(color)));

            }
        }

    }

    public static void main(String[] args) {
        ArrayList<Game> games = readFile("input.txt");
        int sum = 0;
        long powerSum = 0;
        for (Game game : games) {
            //12 red cubes, 13 green cubes, and 14 blue cubes
            if (game.map.get("red") <= 12 && game.map.get("green") <= 13 && game.map.get("blue") <= 14) 
                sum += game.id;
            powerSum += (game.map.get("red") * game.map.get("green") * game.map.get("blue"));
            System.out.println(game.id + " " + (game.map.get("red") <= 12 && game.map.get("green") <= 13 && game.map.get("blue") <= 14));
        }
        System.out.println("ID sum: " + sum);
        System.out.println("Sum of powers: " + powerSum);
    }

    static ArrayList<Game> readFile(String fileName) {
        try {
            Scanner scan = new Scanner(new File(fileName));
            ArrayList<Game> games = new ArrayList<Game>();
            while (scan.hasNext()) {
                games.add(new Game(scan.nextLine()));
            }
            return games;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}