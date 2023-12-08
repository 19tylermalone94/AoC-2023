import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Paths {

    static ArrayList<Node> nodes = new ArrayList<>();
    static String seq;

    static class Node {
        String name;
        Node left;
        Node right;

        Node(String name, Node left, Node right) {
            this.name = name;
            this.left = left;
            this.right = right;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Node other = (Node) obj;
            return name.equals(other.name);
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }
    }

    static void readFile(String fileName) {
        try {
            Scanner scan = new Scanner(new File(fileName));
            seq = scan.nextLine();
            scan.nextLine();
            while (scan.hasNext()) {
                String line = scan.nextLine();
                Scanner token = new Scanner(line);
                String name = token.next();
                token.next();
                String leftName = token.next().substring(1, 4);
                String rightName = token.next().substring(0, 3);
    
                Node node = findOrCreateNode(name);
                Node L = findOrCreateNode(leftName);
                Node R = findOrCreateNode(rightName);
    
                node.left = L;
                node.right = R;
            }
    
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    static Node findOrCreateNode(String name) {
        for (Node n : nodes) {
            if (n.name.equals(name)) {
                return n;
            }
        }
        Node newNode = new Node(name, null, null);
        nodes.add(newNode);
        return newNode;
    }

    static int pathLength(Node node) {
        ArrayList<Node> path = new ArrayList<>();
        long index = 0;
        while (true) {
            path.add(node);
            if (seq.charAt((int)(index % seq.length())) == 'L') {
                node = node.left;
            } else {
                node = node.right;
            }
            ++index;
            if (node.name.endsWith("Z")) {
                break;
            }
        }
        return path.size();
    }

    public static long findLCM(ArrayList<Integer> numbers) {
        long result = numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
            result = lcm(result, numbers.get(i));
        }
        return result;
    }

    private static long lcm(long a, long b) {
        return a * (b / gcd(a, b));
    }

    private static long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public static void main(String[] args) {
        readFile("input.txt");

        // part one: simply following the path and counting the steps

        // Node node = nodes.get(nodes.indexOf(new Node("AAA", null, null)));
        // long index = 0;
        // long steps = 0;
        // while (!node.name.equals("ZZZ")) {
        //     if (seq.charAt((int)(index % seq.length())) == 'L') {
        //         node = node.left;
        //     } else {
        //         node = node.right;
        //     }
        //     ++index;
        //     ++steps;
        // }
        // System.out.println(steps);

        // part 2: find the length of each path from A to Z, then find the LCM of all path lengths to find the intersection at Z
        ArrayList<Node> startNodes = new ArrayList<>();
        for (Node node : nodes) {
            if (node.name.endsWith("A")) {
                startNodes.add(node);
            }
        }
        ArrayList<Integer> pathSizes = new ArrayList<>();
        for (Node node : startNodes) {
            System.out.println(node.name + " " + pathLength(node));
            pathSizes.add(pathLength(node));
        }
        System.out.println(findLCM(pathSizes));
    }

}