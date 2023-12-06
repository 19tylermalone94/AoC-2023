import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Engine {

    int width;
    char[] grid;

    Engine(int width, String fileName) {
        this.width = width;
        grid = new char[width * width];
        processFile(fileName);
    }

    void processFile(String fileName) {
        try {
            Scanner scan = new Scanner(new File(fileName));
            for (int i = 0; i < width; ++i) {
                String line = scan.nextLine();
                for (int j = 0; j < width; ++j) {
                    grid[(i * width) + j] = line.charAt(j);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    boolean adjacentSymbol(int row, int col) {
        for (int row_offset = -1; row_offset <= 1; ++row_offset) {
            for (int col_offset = -1; col_offset <= 1; ++col_offset) {
                if (row_offset == 0 && col_offset == 0)
                    continue;
                int neighbor_row = row + row_offset;
                int neighbor_col = col + col_offset;
                if (neighbor_row >= 0 && neighbor_row < width && neighbor_col >= 0 && neighbor_col < width) {
                    char neighbor_cell = grid[neighbor_row * width + neighbor_col];
                    if (!Character.isDigit(neighbor_cell) && neighbor_cell != '.') {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    boolean isPartNumber(int row, int start, int end) {
        for (int i = start; i < end; ++i) {
            if (adjacentSymbol(row, i)) return true;
        }
        return false;
    }

    int partSum() {
        int sum = 0;
        for (int row = 0; row < width; ++row) {
            for (int col = 0; col < width; ++col) {
                if (Character.isDigit(grid[row * width + col])) {
                    int partNum = 0;
                    int j = col;
                    while (j < width && Character.isDigit(grid[row * width + j])) {
                        partNum = partNum * 10 + (grid[row * width + j++] - '0');
                    }
                    if (isPartNumber(row, col, j)) {
                        sum += partNum;
                        col = j;
                    }
                }
            }
        }
        return sum;
    }

    private int getNumberToLeft(int row, int col) {
        if (col == 0 || !Character.isDigit(grid[row * width + col - 1])) {
            return -1; // No number to the left or at the edge
        }
        int number = 0;
        int multiplier = 1;
        for (int i = col - 1; i >= 0 && Character.isDigit(grid[row * width + i]); --i) {
            number += (grid[row * width + i] - '0') * multiplier;
            multiplier *= 10;
        }
        return number;
    }

    private int getNumberToRight(int row, int col) {
        if (col == width - 1 || !Character.isDigit(grid[row * width + col + 1])) {
            return -1; // No number to the right or at the edge
        }
        int number = 0;
        for (int i = col + 1; i < width && Character.isDigit(grid[row * width + i]); ++i) {
            number = number * 10 + (grid[row * width + i] - '0');
        }
        return number;
    }

    private ArrayList<Integer> getNumbersInRowSegment(String segment) {
        ArrayList<Integer> numbers = new ArrayList<>();
        String[] parts = segment.split("\\.");
        for (String part : parts) {
            if (!part.isEmpty()) {
                numbers.add(Integer.parseInt(part));
            }
        }
        return numbers;
    }
    
    private ArrayList<Integer> getNumbersAboveBelow(int row, int col, boolean isAbove) {
        int targetRow = isAbove ? row - 1 : row + 1;
        if (targetRow < 0 || targetRow >= width) {
            return new ArrayList<>(); // Edge of grid, no numbers above/below
        }
    
        ArrayList<Integer> numbers = new ArrayList<>();
        int targetCol = col;
    
        // Check if the cell directly above/below is a digit or a dot
        if (Character.isDigit(grid[targetRow * width + targetCol]) || 
            (targetCol > 0 && Character.isDigit(grid[targetRow * width + targetCol - 1])) ||
            (targetCol < width - 1 && Character.isDigit(grid[targetRow * width + targetCol + 1]))) {
    
            // Find the start and end indices of the number segment
            int start = targetCol, end = targetCol;
            if (grid[targetRow * width + targetCol] == '.') start -= 1;
            while (start > 0 && (Character.isDigit(grid[targetRow * width + start]))) {
                start--;
            }
            while (end < width - 1 && (Character.isDigit(grid[targetRow * width + end + 1]))) {
                end++;
            }
    
            String segment = new String(grid, targetRow * width + start, end - start + 1);
            numbers.addAll(getNumbersInRowSegment(segment));
        }
    
        return numbers;
    }
    
    
    
    ArrayList<Integer> getNeighbors(int row, int col) {
        ArrayList<Integer> neighbors = new ArrayList<>();
    
        // Left and right checks remain the same
        int leftNum = getNumberToLeft(row, col);
        if (leftNum != -1) neighbors.add(leftNum);
    
        int rightNum = getNumberToRight(row, col);
        if (rightNum != -1) neighbors.add(rightNum);
    
        // Above and below checks
        ArrayList<Integer> aboveNumbers = getNumbersAboveBelow(row, col, true);
        neighbors.addAll(aboveNumbers);
    
        ArrayList<Integer> belowNumbers = getNumbersAboveBelow(row, col, false);
        neighbors.addAll(belowNumbers);
    
        return neighbors;
    }

    int gearRatio(int row, int col) {
        ArrayList<Integer> neibs = getNeighbors(row, col);
        System.out.println(neibs.toString());
        if (neibs.size() > 2 || neibs.size() < 2) return 0;
        return neibs.get(0) * neibs.get(1);
    }

    long gearSum() {
        long sum = 0;
        for (int row = 0; row < width; ++row) {
            for (int col = 0; col < width; ++col) {
                if (grid[row * width + col] == '*') {
                    sum += gearRatio(row, col);
                }
            }
        }
        return sum;
    }


    public static void main(String[] args) {
        Engine t = new Engine(140, "input.txt");
        Engine e = new Engine(10, "test.txt");
        System.out.println(t.gearSum());
        System.out.println(e.gearSum());
    }

}