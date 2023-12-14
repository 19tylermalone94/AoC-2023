import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Calibration1 {

    static ArrayList<String> numbers = new ArrayList<>(List.of("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"));

    public static void main(String[] args) {
        try {
            Scanner scan = new Scanner(new File("input.txt"));
            int sum = 0;
            String line;
            while (scan.hasNext()) {
                line = scan.nextLine();
                int code = 0;
                for (int i = 0; i < line.length(); ++i) {
                    if (Character.isDigit(line.charAt(i))) {
                        code = (line.charAt(i) - '0') * 10;
                        break;
                    }
                    boolean done = false;
                    for (String num : numbers) {
                        if (line.substring(i).indexOf(num) == 0) {
                            code = numbers.indexOf(num) * 10;
                            done = true;
                            break;
                        }
                    }
                    if (done) break;
                }
                for (int i = line.length() - 1; i >= 0; --i) {
                    if (Character.isDigit(line.charAt(i))) {
                        code += line.charAt(i) - '0';
                        break;
                    }
                    boolean done = false;
                    for (String num : numbers) {
                        if (line.substring(i).indexOf(num) == 0) {
                            code += numbers.indexOf(num);
                            done = true;
                            break;
                        }
                    }
                    if (done) break;
                }
                sum += code;
            }
            System.out.println(sum);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}