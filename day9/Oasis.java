import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Oasis {

    static ArrayList<ArrayList<Integer>> readFile(String fileName) {
        try {
            ArrayList<ArrayList<Integer>> res = new ArrayList<>();
            Scanner scan = new Scanner(new File(fileName));
            while (scan.hasNext()) {
                Scanner tokenize = new Scanner(scan.nextLine());
                ArrayList<Integer> sequence = new ArrayList<>();
                while (tokenize.hasNext()) {
                    sequence.add(tokenize.nextInt());
                }
                res.add(sequence);
            }
            return res;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    static int speculatePrevious(ArrayList<Integer> seq) {
        if (allZero(seq)) return 0;
        return seq.get(0) - speculatePrevious(seqOfDifferences(seq));
    }

    static int predictNext(ArrayList<Integer> seq) {
        if (allZero(seq)) return 0;
        return seq.get(seq.size() - 1) + predictNext(seqOfDifferences(seq));
    }

    static boolean allZero(ArrayList<Integer> seq) {
        ArrayList<Integer> drop = new ArrayList<>(seq);
        drop.removeIf(n -> n == 0);
        return drop.size() == 0;
    }

    static ArrayList<Integer> seqOfDifferences(ArrayList<Integer> seq) {
        ArrayList<Integer> diffs = new ArrayList<>();
        for (int i = 0; i < seq.size() - 1; ++i) {
            diffs.add(seq.get(i + 1) - seq.get(i));
        }
        return diffs;
    }

    public static void main(String[] args) {
        ArrayList<ArrayList<Integer>> sequences = readFile("input.txt");
        long sumNext = 0;
        long sumPrev = 0;
        for (ArrayList<Integer> seq : sequences) {
            sumNext += predictNext(seq);
            sumPrev += speculatePrevious(seq);
        }
        System.out.println(sumNext);
        System.out.println(sumPrev);
    }

} 