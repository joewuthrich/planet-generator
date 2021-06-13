package com.joewuthrich.dungeongenerator.layoutgenerator.objects;

import java.util.List;

public class Pair {
    public List<Edge> a;
    public List<Edge> b;

    public Pair(List<Edge> _a, List<Edge> _b) {
        a = _a;
        b = _b;
    }
}
