import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class BoatRace {

    static class Race {
        long duration;
        long record;

        Race(long duration, long record) {
            this.duration = duration;
            this.record = record;
        }

        long numWinsFast() {
            long secsHold = duration / 2;
            long left = 0;
            long right = duration;
            while (left < right) {
                long minWin = 0;
                for (long i = 0; i < duration - secsHold; ++i) {
                    minWin += secsHold;
                }
                long maxWin = 0;
                for (long i = 0; i < duration - secsHold - 1; ++i) {
                    maxWin += secsHold + 1;
                }
                if (maxWin <= record && minWin > record) {
                    return minWin - maxWin;
                } else if (maxWin > record) {
                    // go right
                    left = secsHold;
                    secsHold = (secsHold + right) / 2;
                } else {
                    // left
                    right = secsHold;
                    secsHold = (left + secsHold) / 2;
                }
            }
            return -1;
        }

        long numWins() {
            long numWins = 0;
            for (long secsHold = 0; secsHold < duration; ++secsHold) {
                long speed = secsHold;
                 long distance = 0;
                for (long i = 0; i < duration - secsHold; ++i) {
                    distance += speed;
                }
                if (distance > record) ++numWins;
            }
            return numWins;
        }
    }

    public static void main(String[] args) {
        // List<Race> races = readFile("input.txt");
        // long product = 1;
        // for (Race race : races) {
        //     product *= race.numWins();
        // }
        // System.out.println(product);

        //System.out.println(new Race(54817088, 446129210351007l).numWinsFast());
        System.out.println(new Race(71530, 940200).numWinsFast());
    }

    public static ArrayList<Race> readFile(String fileName) {
        try {
            ArrayList<Race> races = new ArrayList<>();
            Scanner scan1 = new Scanner(new File(fileName));
            String durations = scan1.nextLine();
            String records = scan1.nextLine();

            scan1 = new Scanner(durations);
            Scanner scan2 = new Scanner(records);
            scan1.next();
            scan2.next();
            while (scan1.hasNext()) {
                races.add(new Race(scan1.nextInt(), scan2.nextInt()));
            }
            return races;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
} 