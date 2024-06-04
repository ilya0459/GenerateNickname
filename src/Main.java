import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Main {
    private static final AtomicInteger ATOMIC_3 = new AtomicInteger(0);
    private static final AtomicInteger ATOMIC_4 = new AtomicInteger(0);
    private static final AtomicInteger ATOMIC_5 = new AtomicInteger(0);
    private static final int NAMBER_World = 100_000;

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[NAMBER_World];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread thread1 = new Thread(() -> {
            for (String text : texts) {
                if (isPalindrome(text)) {
                    incrementCounter(text.length());
                }
            }
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            for (String text : texts) {
                if (isSameLetter(text)) {
                    incrementCounter(text.length());
                }
            }
        });
        thread2.start();

        Thread thread3 = new Thread(() -> {
            for (String text : texts) {
                if (isAlphabeticOrder(text)) {
                    incrementCounter(text.length());
                }
            }
        });
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();

        System.out.printf("Красивых слов с длиной 3: " + ATOMIC_3.get() + " штук" +
                "\nКрасивых слов с длиной 4: " + ATOMIC_4.get() + " штук" +
                "\nКрасивых слов с длиной 5: " + ATOMIC_5.get() + " штук");

    }


    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isPalindrome(String text) {
        if (IntStream.range(0, text.length() / 2)
                .noneMatch(i -> text.charAt(i) != text.charAt(text.length() - i - 1))) {
            return true;
        }
        return false;
    }

    public static boolean isSameLetter(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) != text.charAt(i - 1))
                return false;
        }
        return true;
    }

    public static boolean isAlphabeticOrder(String s) {
        int n = s.length();
        char c[] = new char[n];
        for (int i = 0; i < n; i++) {
            c[i] = s.charAt(i);
        }
        Arrays.sort(c);
        for (int i = 0; i < n; i++)
            if (c[i] != s.charAt(i))
                return false;
        return true;
    }

    public static void incrementCounter(int text) {
        switch (text) {
            case 3 -> ATOMIC_3.incrementAndGet();
            case 4 -> ATOMIC_4.incrementAndGet();
            case 5 -> ATOMIC_5.incrementAndGet();
        }
    }
}