import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class CamelCards {

    static HashMap<Character, Integer> map = new HashMap<>() {
        {
            put('2', 0);
            put('3', 1);
            put('4', 2);
            put('5', 3);
            put('6', 4);
            put('7', 5);
            put('8', 6);
            put('9', 7);
            put('T', 8);
            put('J', 9);
            put('Q', 10);
            put('K', 11);
            put('A', 12);
        }
    };

    static class Hand implements Comparable<Hand> {
        ArrayList<Character> hand = new ArrayList<>();
        int handRank;
        int bid;
        ArrayList<Integer> counts = new ArrayList<>(List.of(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));

        Hand(String cards, int bid) {
            this.bid = bid;
            loadCards(cards);
            evaluate();
        }

        void loadCards(String cards) {
            for (int i = 0; i < cards.length(); ++i) {
                hand.add(cards.charAt(i));
            }
        }

        void evaluate() {
            for (char card : hand) {
                counts.set(map.get(card), counts.get(map.get(card)) + 1);
            }
            if (counts.contains(5)) {
                handRank = 7;
            } else if (counts.contains(4)) {
                handRank = 6;
            } else if (counts.contains(3) && counts.contains(2)) {
                handRank = 5;
            } else if (counts.contains(3)) {
                handRank = 4;
            } else if (counts.indexOf(2) != -1 && counts.subList(counts.indexOf(2) + 1, counts.size()).contains(2)) {
                handRank = 3;
            } else if (counts.contains(2)) {
                handRank = 2;
            } else {
                handRank = 1;
            }
        }

        public int compareTo(Hand o) {
            if (handRank != o.handRank) {
                return (handRank < o.handRank) ? -1 : 1;
            } else {
                for (int i = 0; i < hand.size(); ++i) {
                    if (map.get(hand.get(i)) < map.get(o.hand.get(i))) {
                        return -1;
                    } else if (map.get(hand.get(i)) > map.get(o.hand.get(i))) {
                        return 1;
                    }
                }
            }
            return 0;
        }
    }

    static ArrayList<Hand> readFile(String fileName) {
        try {
            Scanner scan = new Scanner(new File(fileName));
            ArrayList<Hand> hands = new ArrayList<>();
            while (scan.hasNext()) {
                String cards = scan.next();
                int bid = Integer.parseInt(scan.next());
                hands.add(new Hand(cards, bid));
            }
            return hands;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        ArrayList<Hand> hands = readFile("input.txt");
        Collections.sort(hands);
        long winnings = 0;
        for (int i = 0; i < hands.size(); ++i) {
            winnings += hands.get(i).bid * (i + 1);
        }
        System.out.println(winnings);
    }
}