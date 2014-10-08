/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lds;

/**
 *
 * @author enxhi
 */
public class Pair {

    private int line;
    private int pos;
    private int get1;

    public Pair(int line1, int pos1) {
        line = line1;
        pos = pos1;
    }

    public int get1() {
        return line;
    }

    public int get2() {

        return pos;
    }

    public int distance(Pair p) {
        return (int) (2 * Math.sqrt((line - p.get1) * (line - p.get1) + (pos - p.get2()) * (pos - p.get2())));

    }
}
