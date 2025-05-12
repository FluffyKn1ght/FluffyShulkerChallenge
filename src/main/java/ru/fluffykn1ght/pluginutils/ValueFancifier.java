package ru.fluffykn1ght.pluginutils;

public class ValueFancifier {
    public static String bool(boolean value, String s1, String s2) {
        if (value) {
            return s1;
        }
        else {
            return s2;
        }
    }
}
