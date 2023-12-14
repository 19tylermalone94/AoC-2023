import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Cards {

    static List<List<Integer>> myNums = new ArrayList<>();
    static List<List<Integer>> winners = new ArrayList<>();

    static int max = 250;

    static int cardCount = 0;

    public static void main(String[] args) {
        loadCards();
        countCards();
        System.out.println(cardCount);
    }

    static void countCards() {
        for (int i = 0; i < myNums.size(); ++i) {
            countCards(i);
        }
    }

    static void countCards(int i) {
        if (i == max) return;

        int count = 0;
        for (int j = 0; j < myNums.get(i).size(); ++j) {
            if (winners.get(i).contains(myNums.get(i).get(j))) {
                ++count;
            }
        }
        for (int k = i + 1; k < max && k < i + 1 + count; ++k) {
            countCards(k);
        }
        cardCount++;
    }

    static void loadCards() {
        try {
            Scanner scan = new Scanner(new File("input.txt"));
            // int total = 0;
            scan.next(); // remove first 'Game'
            while (scan.hasNext()) {


                scan.next(); // remove '1:'

                List<Integer> w = new ArrayList<>();
                String num = scan.next();
                while (!num.equals("|")) {
                    w.add(Integer.parseInt(num));
                    num = scan.next();
                }
                List<Integer> m = new ArrayList<>();
                num = scan.next();
                while(scan.hasNext() && !num.equals("Card")) {
                    m.add(Integer.parseInt(num));
                    num = scan.next();
                }
                if (!num.equals("Card")) m.add(Integer.parseInt(num));
                winners.add(w);
                myNums.add(m);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}