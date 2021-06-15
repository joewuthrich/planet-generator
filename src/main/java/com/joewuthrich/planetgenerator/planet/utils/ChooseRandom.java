package com.joewuthrich.planetgenerator.planet.utils;

import java.util.ArrayList;
import java.util.List;

public class ChooseRandom {
    public static List<String> chooseRandom(List<String> args, int num) {
        List<String> finals = new ArrayList<>();

        List<String> options = new ArrayList<>();
        List<Double> chances = new ArrayList<>();

        double totalChance = 0d;
        double unchanced = 0d, leftChances = 0d;

        String[] split;
        for (String arg : args) {
            split = arg.split("%");

            if (split.length == 1)
                unchanced++;
            else
                totalChance += Double.parseDouble(split[1]);

            options.add(split[0]);
            chances.add(split.length == 1 ? 0d : Double.parseDouble(split[1]));
        }

        if (unchanced != 0)
            leftChances = (100d - totalChance) / unchanced;

        for (int i = 0; i < chances.size(); i++) {
            if (chances.get(i) == 0) {
                chances.set(i, leftChances);
            }
        }

        int x = 0;

        while (num > 0) {
            double rand = Math.random() * 100;

            for (int i = 0; i < chances.size(); i++) {
                int chance = 0;
                for (int j = i - 1; j >= 0; j--)
                    chance += chances.get(j);

                if (chance <= rand && rand < chance + chances.get(i)) {
                    finals.add(options.get(i));

                    for (int j = 0; j < chances.size(); j++) {
                        if (j != i)
                            chances.set(j, chances.get(j) / (100-chances.get(i)) * 100);
                    }

                    chances.remove(i);
                    options.remove(i);
                    args.remove(i);

                    num--;
                    break;
                }
            }

            if (x++ == 50) {
                return finals;
            }
        }

        return finals;
    }
}
