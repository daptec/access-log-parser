package com.stepup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите путь к файлу: ");
        String path = scanner.nextLine();

        int totalRequests = 0;
        int googleBotRequests = 0;
        int yandexBotRequests = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            Pattern userAgentPattern = Pattern.compile("\"(Mozilla/[^\\\"]+)\"");

            while ((line = reader.readLine()) != null) {
                totalRequests++;
                Matcher matcher = userAgentPattern.matcher(line);
                if (matcher.find()) {
                    String userAgent = matcher.group(1);
                    int openBracketIndex = userAgent.indexOf("(");
                    int closeBracketIndex = userAgent.indexOf(")");
                    if (openBracketIndex != -1 && closeBracketIndex != -1) {
                        String firstBrackets = userAgent.substring(openBracketIndex + 1, closeBracketIndex);
                        String[] parts = firstBrackets.split(";");
                        if (parts.length >= 2) {
                            String fragment = parts[1].trim();
                            String botName = fragment.split("/")[0];
                            if (botName.equalsIgnoreCase("Googlebot")) {
                                googleBotRequests++;
                            } else if (botName.equalsIgnoreCase("Yandexbot")) {
                                yandexBotRequests++;
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }

        double googleBotShare = (googleBotRequests / (double) totalRequests) * 100;
        double yandexBotShare = (yandexBotRequests / (double) totalRequests) * 100;
        System.out.println("Googlebot: " + googleBotShare + "%");
        System.out.println("Yandexbot: " + yandexBotShare + "%");

    }
}