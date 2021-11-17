package com.company;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) {
        Calculator calculator=new Calculator();
        BufferedReader d = new BufferedReader(new InputStreamReader(System.in));
        Scanner scanner = new Scanner(System.in);
        int number=1;
        String expression;
        while (number!=0)
        {
            try {
                System.out.println("Введите выражение для расчета. Поддерживаются цифры, операции +,-,*,/,^,% и приоритеты в виде скобок ( и ):");
                expression = d.readLine();
                expression = calculator.reverse_polish_notation(expression);
                System.out.println("Результат вычисления\n" +calculator.calculate(expression));

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}




