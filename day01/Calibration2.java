import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Calibration2 {

    static ArrayList<String> numbers = new ArrayList<>(List.of("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"));
    public static void main(String[] args) {
        try {
            Scanner scan = new Scanner(new File("test2.txt"));
            int sum = 0;
            String line;
            while (scan.hasNext()) {
                line = scan.nextLine();
                int code = 0;
                int first = 9, last = 0;
                int firstNum = 0, lastNum = 0;
                for (String num : numbers) {
                    if (line.indexOf(num) == -1) continue;
                    if (line.indexOf(num) < first) {
                        first = line.indexOf(num);
                        firstNum = numbers.indexOf(num);
                    }
                    if (line.indexOf(num) > last) {
                        last = line.indexOf(num);
                        lastNum = numbers.indexOf(num);
                    }
                }
                System.out.println(firstNum + " " + lastNum);
                code = first * 10 + last;
                sum += code;
            }
            System.out.println(sum);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}