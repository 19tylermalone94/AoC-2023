import java.util.HashMap;
import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Springs {

    private static long countWays(String line, List<Integer> groups, int lineIndex, int groupIndex, int width, Map<String, Long> memo) {
        String key = lineIndex + "," + groupIndex + "," + width;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        if (lineIndex == line.length()) {
            if (groupIndex == groups.size() && width == 0) {
                return 1;
            } else if (groupIndex == groups.size() - 1 && groups.get(groupIndex).equals(width)) {
                return 1;
            } else {
                return 0;
            }
        }

        long ans = 0;
        for (char option : new char[]{'#', '.'}) {
            if (line.charAt(lineIndex) == option || line.charAt(lineIndex) == '?') {
                if (option == '.' && width == 0) {
                    ans += countWays(line, groups, lineIndex + 1, groupIndex, 0, memo);
                } else if (option == '.' && width > 0 && groupIndex < groups.size() && groups.get(groupIndex).equals(width)) {
                    ans += countWays(line, groups, lineIndex + 1, groupIndex + 1, 0, memo);
                } else if (option == '#') {
                    ans += countWays(line, groups, lineIndex + 1, groupIndex, width + 1, memo);
                }
            }
        }
        memo.put(key, ans);
        return ans;
    }

    public static void main(String[] args) throws Exception {
        String content = new String(Files.readAllBytes(Paths.get("input.txt")));
        String[] lines = content.strip().split("\n");

        // [part 1]
        long totalWays = 0;
        for (String l : lines) {
            String[] parts = l.split(" ");
            String line = parts[0];
            List<Integer> groups = Arrays.stream(parts[1].split(",")).map(Integer::parseInt).toList();

            totalWays += countWays(line, groups, 0, 0, 0, new HashMap<>());
        }
        System.out.println("Part 1: " + totalWays);

        // [part 2]
        totalWays = 0;
        for (String l : lines) {
            String[] parts = l.split(" ");
            String bigLine = String.join("?", parts[0], parts[0], parts[0], parts[0], parts[0]);
            List<Integer> bigGroups = Arrays.stream((parts[1] + "," + parts[1] + "," + parts[1] + "," + parts[1] + "," + parts[1]).split(",")).map(Integer::parseInt).toList();

            totalWays += countWays(bigLine, bigGroups, 0, 0, 0, new HashMap<>());
        }
        System.out.println("Part 2: " + totalWays);
    }
}
