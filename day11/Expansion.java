import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Expansion {

    static ArrayList<ArrayList<Boolean>> universe = readFile("input.txt");
    static ArrayList<ArrayList<Boolean>> bigUniverse = applyExpansion(universe);

    static ArrayList<ArrayList<Boolean>> readFile(String fileName) {
        try {
            ArrayList<ArrayList<Boolean>> grid = new ArrayList<>();
            Scanner scan = new Scanner(new File(fileName));
            while (scan.hasNext()) {
                String line = scan.nextLine();
                ArrayList<Boolean> row = new ArrayList<>();
                for (int j = 0; j < line.length(); ++j) {
                    row.add(line.charAt(j) == '#' ? true : false);
                }
                grid.add(row);
            }
            return grid;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    static ArrayList<ArrayList<Boolean>> applyExpansion(ArrayList<ArrayList<Boolean>> grid) {
        ArrayList<ArrayList<Boolean>> bigGrid = new ArrayList<>();

        for (ArrayList<Boolean> row : grid) {
            bigGrid.add(new ArrayList<>(row));
            if (!row.contains(true)) {
                bigGrid.add(new ArrayList<>(row));
            }
        }

        for (int j = 0; j < bigGrid.get(0).size(); ++j) {
            boolean isEmptyCol = true;
            for (ArrayList<Boolean> row : bigGrid) {
                if (row.get(j)) {
                    isEmptyCol = false;
                    break;
                }
            }
            if (isEmptyCol) {
                for (ArrayList<Boolean> row : bigGrid) {
                    row.add(j, false);
                }
                j++; // increment j to skip over newly added column
            }
        }
        return bigGrid;
    }

    static int shortestPathSum() {
        int pathSum = 0;
        int count = 0;
        // start at 1 sum dists to all other galaxies after, then 2 with all galaxies after itself and so on...
        for (int i = 0; i < bigUniverse.size(); ++i) {
            for (int j = 0; j < bigUniverse.get(i).size(); ++j) {
                if (bigUniverse.get(i).get(j)) {
                    for (int m = i; m < bigUniverse.size(); ++m) {
                        for (int n = (m == i) ? j + 1 : 0; n < bigUniverse.get(m).size(); ++n) {
                            if (bigUniverse.get(m).get(n)) {
                                // System.out.println("from: " + i + ", " + j + ", to: " + m + ", " + n);
                                ++count;
                                pathSum += Math.abs(i - m) + Math.abs(j - n);
                            }
                        }
                    }
                }
            }
        }
        System.out.println("Pair count: " + count);
        return pathSum;
    }

    static long shortestPathSumBigExpansion() {
        long pathSum = 0;
        long expansionFactor = 999999;

        int[] rowExpansionCount = new int[universe.size()];
        int[] colExpansionCount = new int[universe.get(0).size()];

        for (int i = 1; i < universe.size(); i++) {
            rowExpansionCount[i] = rowExpansionCount[i - 1] + (isRowEmpty(i - 1) ? 1 : 0);
        }

        for (int j = 1; j < universe.get(0).size(); j++) {
            colExpansionCount[j] = colExpansionCount[j - 1] + (isColumnEmpty(j - 1) ? 1 : 0);
        }

        for (int i = 0; i < universe.size(); i++) {
            for (int j = 0; j < universe.get(i).size(); j++) {
                if (universe.get(i).get(j)) {
                    for (int m = i; m < universe.size(); m++) {
                        for (int n = (m == i) ? j + 1 : 0; n < universe.get(m).size(); n++) {
                            if (universe.get(m).get(n)) {
                                long rowDistance = Math.abs(i - m) + (rowExpansionCount[m] - rowExpansionCount[i]) * expansionFactor;
                                long colDistance = Math.abs(j - n) + (colExpansionCount[Math.max(j, n)] - colExpansionCount[Math.min(j, n)]) * expansionFactor;
                                pathSum += rowDistance + colDistance;
                            }
                        }
                    }
                }
            }
        }
        return pathSum;
    }

    static boolean isRowEmpty(int rowIndex) {
        return !universe.get(rowIndex).contains(true);
    }

    static boolean isColumnEmpty(int colIndex) {
        for (ArrayList<Boolean> row : universe) {
            if (row.get(colIndex)) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        // for (ArrayList<Boolean> row : universe) {
        //     for (Boolean b : row) {
        //         System.out.print(b ? '#' : '.');
        //     }
        //     System.out.println();
        // }
        // for (ArrayList<Boolean> row : bigUniverse) {
        //     for (Boolean b : row) {
        //         System.out.print(b ? '#' : '.');
        //     }
        //     System.out.println();
        // }
        System.out.println("universe - height: " + universe.size() + " width: " + universe.get(0).size());
        System.out.println("big universe - height: " + bigUniverse.size() + " width: " + bigUniverse.get(0).size());
        System.out.println("Path sum w/ false expansion: " + shortestPathSum());
        System.out.println("Path sum w/ true expansion: " + shortestPathSumBigExpansion());
    }
}