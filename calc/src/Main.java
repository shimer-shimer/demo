import java.util.Scanner;

class Main {
    public static int toArabic(String roman) {
        int result = 0;
        String romanNumeral = roman.toUpperCase();
        for(int i = 0; i < romanNumeral.length() - 1; i++) {
            int current = romanCharToInt(romanNumeral.charAt(i));
            int next = romanCharToInt(romanNumeral.charAt(i + 1));
            if(current < next) {
                result -= current;
            } else {
                result += current;
            }
        }
        result += romanCharToInt(romanNumeral.charAt(romanNumeral.length() - 1));
        return result;
    }

    public static int romanCharToInt(char romanChar) {
        switch (romanChar) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                return 0;
        }
    }

    public static String toRoman(int number) {
        if (number < 1 || number > 3999) {
            throw new IllegalArgumentException("в римской системе нет отрицательных чисел");
        }
        String[] thousands = {"", "M", "MM", "MMM"};
        String[] hundreds = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] tens = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] ones = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        return thousands[number/1000] + hundreds[(number%1000)/100] + tens[(number%100)/10] + ones[number%10];
    }

    public static boolean isArabic(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isRoman(String input) {
        String[] validRomanNumerals = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
        for (String roman : validRomanNumerals) {
            if (input.equals(roman)) {
                return true;
            }
        }
        return false;
    }

    public static int calculate(int a, int b, char operator) {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                return a / b;
            default:
                throw new IllegalArgumentException("1");
        }
    }

    public static String calc(String input) {
        String[] parts = input.split(" ");
        if (parts.length != 3) {
            throw new IllegalArgumentException("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }
        int a, b;
        char operator;
        if (isArabic(parts[0])) {
            a = Integer.parseInt(parts[0]);
            if (!isArabic(parts[2])) {
                throw new IllegalArgumentException("недопустимый оператор");
            }
            b = Integer.parseInt(parts[2]);
        } else if (isRoman(parts[0])) {
            a = toArabic(parts[0]);
            if (!isRoman(parts[2])) {
                throw new IllegalArgumentException("используются одновременно разные системы счисления");
            }
            b = toArabic(parts[2]);
        } else {
            throw new IllegalArgumentException("недопустимый оператор");
        }
        operator = parts[1].charAt(0);
        int result = calculate(a, b, operator);
        if (isArabic(parts[0]) && isArabic(parts[2])) {
            return Integer.toString(result);
        } else {
            return toRoman(result);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        scanner.close();
        try {
            String result = calc(input);
            System.out.println(result);
        } catch (IllegalArgumentException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}