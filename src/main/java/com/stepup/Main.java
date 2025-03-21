package com.stepup;

import ru.courses.other.Fraction;

public class Main {
    public static double sumAll(Number... numbers) {
        double sum = 0;
        for (Number num : numbers) {
            sum += num.doubleValue();
        }
        return sum;
    }

    public static void main(String[] args) {
        double result1 = sumAll(3.6, new Fraction(49, 12), 3, new Fraction(3, 2));
        System.out.println(result1);

        double result2 = sumAll(new Fraction(1, 3), 1);
        System.out.println(result2);

        double result3 = sumAll(2, new Fraction(3, 5), 2.3);
        System.out.println(result3);
    }
}
