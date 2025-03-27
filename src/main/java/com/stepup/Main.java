package com.stepup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int counter = 0;
        while (true) {
            System.out.println("Введите путь к файлу: ");
            String path = new Scanner(System.in).nextLine();
            File file = new File(path);
            boolean fileExists = file.exists();
            boolean isDirectory = file.isDirectory();
            if (!(fileExists || isDirectory)) {
                System.out.println("Указанный файл не существует или указанный путь является путем к папке");
                continue;
            }
            counter++;
            System.out.println("Путь указан верно. \nЭто файл номер: " + counter);

            try (FileReader fileReader = new FileReader(path);
                 BufferedReader reader = new BufferedReader(fileReader)) {
                int totalLines = 0;
                int maxLength = 0;
                int minLength = Integer.MAX_VALUE;
                String line;
                while ((line = reader.readLine()) != null) {
                    int length = line.length();
                    totalLines++;
                    if (length > 1024) {
                        throw new RuntimeException("Строка превышает 1024 символа: " + line);
                    }
                    if (length > maxLength) {
                        maxLength = length;
                    }
                    if (length < minLength) {
                        minLength = length;
                    }
                }
                System.out.println("общее количество строк: " + totalLines +
                        "\nдлина самой длинной строки: " + maxLength +
                        "\nдлина самой короткой строки: " + minLength);
            } catch (RuntimeException ex) {
                System.out.println("Error: " + ex.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}