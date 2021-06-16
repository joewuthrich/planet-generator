package com.joewuthrich.planetgenerator.planet.utils;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class GenerateName {
    static File f;
    static RandomAccessFile rf;

    public static String generateName() {
        double rand = Math.random();

        if (rand < 0.4d) {
            return getWord() + getRomanNumeral();
        }
        else if (rand < 0.8d) {
            return getWord() + "-" + getWord()+ getRomanNumeral();
        }
        else{
            return getWord() + " " + getWord() + getRomanNumeral();
        }
    }

    /**
     * Get a random word from the dictionary
     * @return the random word
     */
    private static String getWord() {
        try {
            f = new File("F:\\OneDrive\\Desktop\\Servers\\Test2\\plugins\\PlanetGenerator\\words_alpha.txt");
            rf = new RandomAccessFile(f, "r");

            long size = rf.length();
            long randByte = (long) (Math.random() * rf.length());
            long moveByte = randByte;

            rf.seek(moveByte);

            StringBuilder s = new StringBuilder();
            char _char;

            while (true)
            {
                _char = (char) rf.readByte();
                if (moveByte < 0 || _char == '\n' || _char == '\r')
                    break;
                else
                {
                    s.insert(0, _char);
                    moveByte--;

                    if (moveByte >= 0)
                        rf.seek(moveByte);
                    else break;
                }
            }

            moveByte = randByte + 1;
            rf.seek(moveByte);
            for (;;)
            {
                _char = (char) rf.readByte();
                if (moveByte >= size || _char == '\n' || _char == '\r')
                    break;
                else
                {
                    s.append(_char);
                    moveByte++;
                }
            }

            if (s.length() == 0)
                return getWord();

            s.setCharAt(0, Character.toString(s.charAt(0)).toUpperCase().charAt(0));

            return s.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return getWord();
    }

    /**
     * Get a random roman numeral from 1-15 (~40% chance), or a blank string (~60% chance)
     * @return either the roman numeral or a blank string
     */
    private static String getRomanNumeral() {
        if (Math.random() > 0.6) {
            int num = (int) Math.round(15 * Math.random());
            switch (num) {
                case 0:
                    return "";
                case 1:
                    return " I";
                case 2:
                    return " II";
                case 3:
                    return " III";
                case 4:
                    return " IV";
                case 5:
                    return " V";
                case 6:
                    return " VI";
                case 7:
                    return " VII";
                case 8:
                    return " VIII";
                case 9:
                    return " IX";
                case 10:
                    return " X";
                case 11:
                    return " XI";
                case 12:
                    return " XII";
                case 13:
                    return " XIII";
                case 14:
                    return " XIV";
                case 15:
                    return " XV";
            }
        }
        return "";
    }
}
