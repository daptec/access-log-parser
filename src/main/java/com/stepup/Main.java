package com.stepup;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Введите первое число: ");
        int number1 = new Scanner(System.in).nextInt();
        System.out.println("Введите второе число: ");
        int number2 = new Scanner(System.in).nextInt();
        System.out.println("Сумма: " + (number1 + number2));
        System.out.println("Разность: " + (number1 - number2));
        System.out.println("Произведение: " + (number1 * number2));
        double res = (double) number1 / number2;
        System.out.println("Частное: " + res);
    }
}
