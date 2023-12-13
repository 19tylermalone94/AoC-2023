import java.util.*;
import java.io.*;



public class Pipes {

    static ArrayList<ArrayList<PipeNode>> grid = new ArrayList<>();
    static ArrayList<ArrayList<PipeNode>> doubledGrid = new ArrayList<>();

    static class Point {
        int x, y;
    
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static class  PipeNode {
        boolean inLoop = false;
        boolean isOutside = false;
        char val;
        PipeNode up, down, left, right;
        PipeNode(char val) { this.val = val; }

        @Override
        public boolean equals(Object other) {
            return this == other;
        }
    }

    static void loadGrid(String fileName) {
        ArrayList<ArrayList<Character>> chars = readFile(fileName);

        for (int i = 0; i < chars.size(); ++i) {
            ArrayList<PipeNode> row = new ArrayList<>();
            for (int j = 0; j < chars.get(i).size(); ++j) {
                row.add(new PipeNode(chars.get(i).get(j)));
            }
            grid.add(row);
        }

        for (int i = 0; i < chars.size(); ++i) {
            for (int j = 0; j < chars.get(i).size(); ++j) {
                // assign neighbor nodes if accessible
                if (i - 1 >= 0 && canAccept(chars.get(i).get(j), "up", chars.get(i - 1).get(j))) {
                    grid.get(i).get(j).up = grid.get(i - 1).get(j);
                }
                if (i + 1 < grid.size() && canAccept(chars.get(i).get(j), "down", chars.get(i + 1).get(j))) {
                    grid.get(i).get(j).down = grid.get(i + 1).get(j);
                }
                if (j - 1 >= 0 && canAccept(chars.get(i).get(j), "left", chars.get(i).get(j - 1))) {
                    grid.get(i).get(j).left = grid.get(i).get(j - 1);
                }
                if (j + 1 < grid.get(i).size() && canAccept(chars.get(i).get(j), "right", chars.get(i).get(j + 1))) {
                    grid.get(i).get(j).right = grid.get(i).get(j + 1);
                }
            }
        }
    }

    static ArrayList<ArrayList<Character>> readFile(String fileName) {
        try {
            Scanner scan = new Scanner(new File(fileName));
            ArrayList<ArrayList<Character>> charGrid = new ArrayList<>();
            while (scan.hasNext()) {
                String line = scan.nextLine();
                ArrayList<Character> row = new ArrayList<>();
                for (int j = 0; j < line.length(); ++j) {
                    row.add(line.charAt(j));
                }
                charGrid.add(row);
            }
            return charGrid;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

     static boolean canAccept(char a, String dir, char b) {
        if (a == 'S') {
            if (dir.equals("up")) {
                return b == '|' || b == '7' || b == 'F';
            } else if (dir.equals("down")) {
                return b == '|' || b == 'L' || b == 'J';
            } if (dir.equals("left")) {
                return b == '-' || b == 'L' || b == 'F';
            } else if (dir.equals("right")) {
                return b == '-' || b == 'J' || b == '7';
            }
        } else if (a == '|') {
            if (dir.equals("up")) {
                return b == '|' || b == '7' || b == 'F' || b == 'S';
            } else if (dir.equals("down")) {
                return b == '|' || b == 'L' || b == 'J' || b == 'S';
            }
        } else if (a == '-') {
            if (dir.equals("left")) {
                return b == '-' || b == 'L' || b == 'F' || b == 'S';
            } else if (dir.equals("right")) {
                return b == '-' || b == 'J' || b == '7' || b == 'S';
            }
        } else if (a == 'F') {
            if (dir.equals("down")) {
                return b == 'J' || b == 'L' || b == '|' || b == 'S';
            } else if (dir.equals("right")) {
                return b == '-' || b == '7' || b == 'J' || b == 'S';
            }
        } else if (a == '7') {
            if (dir.equals("down")) {
                return b == 'J' || b == 'L' || b == '|' || b == 'S';
            } else if (dir.equals("left")) {
                return b == '-' || b == 'F' || b == 'L' || b == 'S';
            }
        } else if (a == 'L') {
            if (dir.equals("up")) {
                return b == '|' || b == 'F' || b == '7' || b == 'S';
            } else if (dir.equals("right")) {
                return b == '-' || b == 'J' || b == '7' || b == 'S';
            }
        } else if (a == 'J') {
            if (dir.equals("up")) {
                return b == '|' || b == 'F' || b == '7' || b == 'S';
            } else if (dir.equals("left")) {
                return b == '-' || b == 'L' || b == 'F' || b == 'S';
            }
        }
        return false;
     }

     static ArrayList<PipeNode> findCycle(PipeNode startNode) {
        for (String d : List.of("up", "down", "left", "right")) {
            PipeNode curr = (d.equals("up")) ? startNode.up
            : (d.equals("down")) ? startNode.down
            : (d.equals("left")) ? startNode.left : startNode.right;
            PipeNode prev = startNode;
            ArrayList<PipeNode> path = new ArrayList<>();
            path.add(startNode);
            startNode.inLoop = true;
            while (true) {
                if (curr == null) {
                    while (!path.isEmpty()) {
                        PipeNode n = path.remove(0);
                        n.inLoop = false;
                    }
                    break;
                }
                if (curr.val == 'S') return path;
                path.add(curr);
                curr.inLoop = true;
                PipeNode temp = curr;
                curr = getNextNode(curr, prev);
                prev = temp;
            }
            System.out.println();
        }
    
        return null;
    }
    
    static PipeNode getNextNode(PipeNode curr, PipeNode prev) {
        if (curr.up != null && curr.up != prev) {
            return curr.up;
        }
        if (curr.down != null && curr.down != prev) {
            return curr.down;
        }
        if (curr.left != null && curr.left != prev) {
            return curr.left;
        }
        if (curr.right != null && curr.right != prev) {
            return curr.right;
        }
        return null;
    }

    static char replaceS(int row, int col) {
        PipeNode n = grid.get(row).get(col);
        if (n.up != null) {
            if (n.down != null) return '|';
            if (n.left != null) return 'J';
            if (n.right != null) return 'L';
        }
        if (n.down != null) {
            if (n.left != null) return '7';
            if (n.right != null) return 'F';
        }
        return '-';
    }

    static void doubleGridSize() {
        for (int i = 0; i < grid.size() * 2; ++i) {
            ArrayList<PipeNode> row = new ArrayList<>();
            for (int j = 0; j < grid.get(0).size() * 2; ++j) {
                row.add(new PipeNode('.'));
            }
            doubledGrid.add(row);
        }

        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(i).size(); j++) {
                doubledGrid.get(i * 2).set(j * 2, grid.get(i).get(j)); // Copy node
            }
        }

        // scan horizontally and fill in gaps with '-'
        for (int i = 0; i < doubledGrid.size(); ++i) {
            boolean fill = false;
            for (int j = 0; j < doubledGrid.get(i).size(); ++j) {
                if (doubledGrid.get(i).get(j).inLoop && (doubledGrid.get(i).get(j).val == 'F' || doubledGrid.get(i).get(j).val == 'L')) {
                    fill = true;
                }
                if (doubledGrid.get(i).get(j).inLoop && (doubledGrid.get(i).get(j).val == 'J' || doubledGrid.get(i).get(j).val == '7')) {
                    fill = false;
                }
                if (fill && !doubledGrid.get(i).get(j).inLoop) {
                    doubledGrid.get(i).get(j).val = '-';
                    doubledGrid.get(i).get(j).inLoop = true;
                }
            }
        }

        // scan vertically and fill gaps with '|'
        for (int j = 0; j < doubledGrid.get(0).size(); ++j) {
            boolean fill = false;
            for (int i = 0; i < doubledGrid.size(); ++i) {
                if (doubledGrid.get(i).get(j).inLoop && (doubledGrid.get(i).get(j).val == 'F' || doubledGrid.get(i).get(j).val == '7')) {
                    fill = true;
                }
                if (doubledGrid.get(i).get(j).inLoop && (doubledGrid.get(i).get(j).val == 'J' || doubledGrid.get(i).get(j).val == 'L')) {
                    fill = false;
                }
                if (fill && !doubledGrid.get(i).get(j).inLoop) {
                    doubledGrid.get(i).get(j).val = '|';
                    doubledGrid.get(i).get(j).inLoop = true;
                }
            }
        }
    }

    static void floodFillOutside() {
        // flood top and bottom
        for (int j = 0; j < doubledGrid.get(0).size(); ++j) {
            if (!doubledGrid.get(0).get(j).inLoop) {
                floodFillOutside(0, j);
            }
            if (!doubledGrid.get(doubledGrid.size() - 1).get(j).inLoop) {
                floodFillOutside(doubledGrid.size() - 1, j);
            }
        }

        // flood left and right
        for (int i = 0; i < doubledGrid.size(); ++i) {
            if (!doubledGrid.get(i).get(0).inLoop) {
                floodFillOutside(i, 0);
            }
            if (!doubledGrid.get(i).get(doubledGrid.get(i).size() - 1).inLoop) {
                floodFillOutside(i, doubledGrid.get(i).size() - 1);
            }
        }
    }

    static void floodFillOutside(int startRow, int startCol) {
        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(startRow, startCol));
    
        while (!queue.isEmpty()) {
            Point p = queue.poll();
            int i = p.x;
            int j = p.y;
    
            if (i < 0 || i >= doubledGrid.size() || j < 0 || j >= doubledGrid.get(0).size() || 
                doubledGrid.get(i).get(j).isOutside || doubledGrid.get(i).get(j).inLoop) {
                continue;
            }
    
            doubledGrid.get(i).get(j).isOutside = true;
    
            queue.add(new Point(i - 1, j));
            queue.add(new Point(i + 1, j));
            queue.add(new Point(i, j - 1));
            queue.add(new Point(i, j + 1));
        }
    }
    
    static int countEnclosedAreaInDoubledGrid() {
        int enclosedArea = 0;
        for (int i = 0; i < doubledGrid.size(); i += 2) {
            for (int j = 0; j < doubledGrid.get(i).size(); j += 2) {
                if (!doubledGrid.get(i).get(j).isOutside && !doubledGrid.get(i).get(j).inLoop) {
                    doubledGrid.get(i).get(j).val = '*';
                    ++enclosedArea;
                }
            }
        }
        return enclosedArea;
    }

    public static void main(String[] args) {
        loadGrid("input.txt");

        int row = 0, col = 0;
        for (int i = 0; i < grid.size(); ++i) {
            for (int j = 0; j < grid.get(i).size(); ++j) {
                if (grid.get(i).get(j).val == 'S') {
                    row = i;
                    col = j;
                    grid.get(i).get(j).inLoop = true;
                    break;
                }
            }
        }
       
        ArrayList<PipeNode> path = findCycle(grid.get(row).get(col));
        System.out.println("cycle length: " + path.size() / 2);
        replaceS(row, col);

     
        doubleGridSize();
        
        floodFillOutside();
        System.out.println(countEnclosedAreaInDoubledGrid());

    }

}