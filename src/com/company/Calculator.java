package com.company;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * @author Вяткины Виктория и Ксения 7 группа
 */

public class Calculator {
    /**
     *  Преобразовать строку в обратную польскую нотацию
     * @param expression строка вводимая пользователем
     * @return Выходная строка в обратной польской нотации
     * @throws Exception обработка ошибки разбора скобок и пробелов
     */
    public String reverse_polish_notation(String expression) throws Exception {
        StringBuilder StringBuilderStack = new StringBuilder(""), StringBuilderOut = new StringBuilder("");
        char new_expression, new_substring; //в эту переменную вернется извлеченные символы из строки между двумя индексами
        for (int i = 0; i < expression.length(); i++) {
            new_expression = expression.charAt(i);
            if (isOperator(new_expression)) {
                while (StringBuilderStack.length() > 0) {
                    new_substring =StringBuilderStack.substring(StringBuilderStack.length()-1).charAt(0);
                    if (isOperator(new_substring) && (operatorPrior(new_expression) <= operatorPrior(new_substring))) {
                        StringBuilderOut.append(" ").append(new_substring).append(" ");
                        StringBuilderStack.setLength(StringBuilderStack.length()-1);
                    } else {
                        StringBuilderOut.append(" ");
                        break;
                    }
                }
                StringBuilderOut.append(" ");
                StringBuilderStack.append(new_expression);
            } else if ('(' == new_expression) {
               StringBuilderStack.append(new_expression);
            }
            else if (')' == new_expression) {
                new_substring = StringBuilderStack.substring(StringBuilderStack.length()-1).charAt(0);
                while ('('  != new_substring ) {
                    if (StringBuilderStack.length() < 1) {
                        throw new Exception("Ошибка разбора скобок.");
                    }
                    StringBuilderOut.append(" ").append(new_substring);
                    StringBuilderStack.setLength(StringBuilderStack.length()-1);
                    new_substring = StringBuilderStack.substring(StringBuilderStack.length()-1).charAt(0);
                }

                StringBuilderStack.setLength(StringBuilderStack.length()-1);

            } else {
                StringBuilderOut.append(new_expression);
            }
        }

        // Если в стеке остались операторы, добавляем их в входную строку
        while (StringBuilderStack.length() > 0) {
            StringBuilderOut.append(" ").append(StringBuilderStack.substring(StringBuilderStack.length()-1));
            StringBuilderStack.setLength(StringBuilderStack.length()-1);
        }

        return  StringBuilderOut.toString();

    }

    /**
     *Функция проверяет, является ли текущий символ оператором
     * @param c переменная символа
     * @return true|false если нашли эти символы
     */
    private static boolean isOperator(char c) {
        switch (c) {
            case '-':
            case '+':
            case '*':
            case '/':
            case '^':
                return true;
        }
        return false;
    }

    /**
     *Возвращает приоритет операции
     * @param op приоритет операции
     * @return возвращает порядок приоритета
     */
    private static byte operatorPrior(char op) {
        switch (op) {
            case '^':
                return 3;
            case '*':
            case '/':
            case '%':
                return 2;
        }
        return 1;
    }

    /**
     *
     * @param expression вводимая строка
     * @return очищение стека
     * @throws Exception ошибки: неверное количество данных в стеке для операций,деление на ноль
     */
    public static double calculate(String expression) throws Exception {
        double dA = 0, dB = 0;
        String new_expression;
        Deque<Double> stack = new ArrayDeque<>();
        StringTokenizer stringTokenizer = new StringTokenizer(expression);
        while(stringTokenizer.hasMoreTokens()) {
            try {
                new_expression = stringTokenizer.nextToken().trim();
                if (1 == new_expression.length() && isOperator(new_expression.charAt(0))) {
                    if (stack.size() < 2) {
                        throw new Exception("Неверное количество данных в стеке для операции " + new_expression);
                    }
                    dB = stack.pop();
                    dA = stack.pop();
                    switch (new_expression.charAt(0)) {
                        case '+':
                            dA += dB;
                            break;
                        case '-':
                            dA -= dB;
                            break;
                        case '/':
                            if (dB==0)
                                System.out.println("Деление на ноль");
                            else
                            {
                                dA /= dB;
                                break;
                            }
                        case '*':
                            dA *= dB;
                            break;
                        case '%':
                            dA %= dB;
                            break;
                        case '^':
                            dA = Math.pow(dA, dB);
                            break;
                        default:
                            throw new Exception("Недопустимая операция " + new_expression);
                    }
                    stack.push(dA);
                }
                else {
                    dA = Double.parseDouble(new_expression);
                    stack.push(dA);
                }
            } catch (Exception e) {
                throw new Exception("Недопустимый символ в выражении");
            }
        }
        if (stack.size() > 1) {
            throw new Exception("Количество операторов не соответствует количеству операндов");
        }
        return stack.pop();
    }

}

