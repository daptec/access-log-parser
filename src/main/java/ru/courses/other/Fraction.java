package ru.courses.other;

public class Fraction extends Number {
    private int num, denum;

    public Fraction(int num, int denum) {
        this.num = num;
        this.denum = denum;
    }

    @Override
    public int intValue() {
        return num / denum;
    }

    @Override
    public long longValue() {
        return (long) num / denum;
    }

    @Override
    public float floatValue() {
        return (float) num / denum;
    }

    @Override
    public double doubleValue() {
        return (double) num / denum;
    }

    @Override
    public String toString() {
        return num + "/" + denum;
    }
}
