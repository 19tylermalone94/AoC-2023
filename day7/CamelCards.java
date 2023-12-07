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
            put('J', 0);
            put('2', 1);
            put('3', 2);
            put('4', 3);
            put('5', 4);
            put('6', 5);
            put('7', 6);
            put('8', 7);
            put('9', 8);
            put('T', 9);
            put('Q', 10);
            put('K', 11);
            put('A', 12);
        }
    };

    static class Hand implements Comparable<Hand> {
        ArrayList<Character> hand = new ArrayList<>();
        int handRank;
        int bid;
        int maxCount;
        int maxCountIndex;
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
            for (char card : hand) {
                counts.set(map.get(card), counts.get(map.get(card)) + 1);
            }
            for (int i = 1; i < counts.size(); ++i) {
                if (counts.get(i) > maxCount) {
                    maxCount = counts.get(i);
                    maxCountIndex = i;
                }
            }
        }

        void evaluate() {
            if (fiveKind()) {
                handRank = 7;
            } else if (fourKind()) {
                handRank = 6;
            } else if (fullHouse()) {
                handRank = 5;
            } else if (threeKind()) {
                handRank = 4;
            } else if (twoPair()) {
                handRank = 3;
            } else if (onePair()) {
                handRank = 2;
            } else {
                handRank = 1;
            }
        }

        int numJoker() {
            return counts.get(0);
        }

        boolean fiveKind() {
            return counts.contains(5) || (maxCount + numJoker() == 5);
        }

        boolean fourKind() {
            return counts.contains(4) || (maxCount + numJoker() == 4);
        }

        boolean fullHouse() {
            return (counts.contains(3) && counts.contains(2))
                || numJoker() == 3
                || (numJoker() == 2 && counts.subList(1, counts.size()).contains(2))
                || (numJoker() == 1 && counts.contains(3))
                || (numJoker() == 1 && counts.contains(2) && counts.subList(counts.indexOf(2) + 1, counts.size()).contains(2));
        }

        boolean threeKind() {
            return counts.contains(3) || (maxCount + numJoker() >= 3);
        }

        boolean twoPair() {
            return numJoker()  == 2 || counts.contains(2) && numJoker() == 1
            ||counts.contains(2) && counts.subList(counts.indexOf(2) + 1, counts.size()).contains(2);
        }

        boolean onePair() {
            return numJoker() >= 1 || counts.contains(2);
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
            winnings += (long)hands.get(i).bid * (i + 1);
        }
        System.out.println(winnings);
    }
}