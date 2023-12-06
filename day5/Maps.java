import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Maps {

    static class Map {
        long source, dest, range;

        Map(long dest, long source, long range) {
            this.source = source;
            this.dest = dest;
            this.range = range;
        }

        boolean inRange(long n) {
            return n >= source && n < source + range;
        }

        long getVal(long key) {
            return dest + (key - source);
        }
    }

    static ArrayList<Long> initSeeds = new ArrayList<>();

    static ArrayList<Map> seedToSoil = new ArrayList<>();
    static ArrayList<Map> soilToFert = new ArrayList<>();
    static ArrayList<Map> fertToWater = new ArrayList<>();
    static ArrayList<Map> waterToLight = new ArrayList<>();
    static ArrayList<Map> lightToTemp = new ArrayList<>();
    static ArrayList<Map> tempToHumid = new ArrayList<>();
    static ArrayList<Map> humidToLocation = new ArrayList<>();

    public static void main(String[] args) {
        loadMaps();
        long minLocation = Long.MAX_VALUE;
        for (int i = 0; i < initSeeds.size(); i += 2) {
            System.out.println("seed: " + (i / 2));
            for (long j = initSeeds.get(i); j < initSeeds.get(i) + initSeeds.get(i + 1); ++j) {
                long seed = j;
                long soil = seed;
            for (Map map : seedToSoil) {
                if (map.inRange(seed)) {
                    soil = map.getVal(seed);
                    break;
                }
            }

            long fert = soil;
            for (Map map : soilToFert) {
                if (map.inRange(soil)) {
                    fert = map.getVal(soil);
                    break;
                }
            }

            long water = fert;
            for (Map map : fertToWater) {
                if (map.inRange(fert)) {
                    water = map.getVal(fert);
                    break;
                }
            }

            long light = water;
            for (Map map : waterToLight) {
                if (map.inRange(water)) {
                    light = map.getVal(water);
                    break;
                }
            }

            long temp = light;
            for (Map map : lightToTemp) {
                if (map.inRange(light)) {
                    temp = map.getVal(light);
                    break;
                }
            }

            long humid = temp;
            for (Map map : tempToHumid) {
                if (map.inRange(temp)) {
                    humid = map.getVal(temp);
                    break;
                }
            }

            long location = humid;
            for (Map map : humidToLocation) {
                if (map.inRange(humid)) {
                    location = map.getVal(humid);
                    break;
                }
            }

            // System.out.println(seed + " " + location);
            minLocation = Math.min(minLocation, location);
            }
        }
        System.out.println(minLocation);
    }

    static void loadMaps() {
        try {
            Scanner scan = new Scanner(new File("input.txt"));
            Scanner token = new Scanner(scan.nextLine());
            token.next();
            while (token.hasNext()) {
                initSeeds.add(token.nextLong());
            }

            scan.nextLine();
            String line = scan.nextLine();
            while (scan.hasNext()) {
                if (line.contains("seed-to-soil")) {
                    line = scan.nextLine();
                    while (!line.equals("")) {
                        token = new Scanner(line);
                        Map map = new Map(token.nextLong(), token.nextLong(), token.nextLong());
                        seedToSoil.add(map);
                        line = scan.nextLine();
                    }
                }
                if (line.contains("soil-to-fertilizer")) {
                    line = scan.nextLine();
                    while (!line.equals("")) {
                        token = new Scanner(line);
                        Map map = new Map(token.nextLong(), token.nextLong(), token.nextLong());
                        soilToFert.add(map);
                        line = scan.nextLine();
                    }
                }
                if (line.contains("fertilizer-to-water")) {
                    line = scan.nextLine();
                    while (!line.equals("")) {
                        token = new Scanner(line);
                        Map map = new Map(token.nextLong(), token.nextLong(), token.nextLong());
                        fertToWater.add(map);
                        line = scan.nextLine();
                    }
                }
                if (line.contains("water-to-light")) {
                    line = scan.nextLine();
                    while (!line.equals("")) {
                        token = new Scanner(line);
                        Map map = new Map(token.nextLong(), token.nextLong(), token.nextLong());
                        waterToLight.add(map);
                        line = scan.nextLine();
                    }
                }
                if (line.contains("light-to-temperature")) {
                    line = scan.nextLine();
                    while (!line.equals("")) {
                        token = new Scanner(line);
                        Map map = new Map(token.nextLong(), token.nextLong(), token.nextLong());
                        lightToTemp.add(map);
                        line = scan.nextLine();
                    }
                }
                if (line.contains("temperature-to-humidity")) {
                    line = scan.nextLine();
                    while (!line.equals("")) {
                        token = new Scanner(line);
                        Map map = new Map(token.nextLong(), token.nextLong(), token.nextLong());
                        tempToHumid.add(map);
                        line = scan.nextLine();
                    }
                }
                if (line.contains("humidity-to-location")) {
                    line = scan.nextLine();
                    while (scan.hasNext()) {
                        token = new Scanner(line);
                        Map map = new Map(token.nextLong(), token.nextLong(), token.nextLong());
                        humidToLocation.add(map);
                        line = scan.nextLine();
                    }
                }

                line = (scan.hasNext()) ? scan.nextLine() : line;
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}