package ua.com.javarush.popelo.cryptanalysistask;

import ua.com.javarush.popelo.cryptanalysistask.cryptor.Cryptor;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    /**
     *
     */
    private static final String[] MENU = {
        "1 - Encrypt",
        "2 - Decrypt",
        "3 - Brute force",
        "4 - Exit",
    };

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            makeChoice();

        } catch (Exception ex) {
            processException(ex);
        }
    }

    /**
     * @param ex
     */
    private static void processException(Exception ex) {
        System.out.println("Exception: " + ex.toString());
    }

    /**
     *
     */
    private static void makeChoice() {
        System.out.println("Select menu item: ");

        Scanner scanner = new Scanner(System.in);

        printMenu();

        try {
            int option = scanner.nextInt();

            if (option < 1 || option > MENU.length) {
                throw new InputMismatchException();
            }

            Cryptor cryptor = new Cryptor();
            int maxShift = cryptor.getMaxShift();

            switch (option) {
                case 1:
                    System.out.println("Inter the shift value. From 1 to " + maxShift);
                    cryptor.setShift(scanner.nextInt());
                    cryptor.encryptData();
                    System.out.println("Encrypted.");
                    break;
                case 2:
                    System.out.println("Inter the shift value. From 1 to " + maxShift);
                    cryptor.setShift(scanner.nextInt());
                    cryptor.decryptData();
                    System.out.println("Decrypted.");
                    break;
                case 3:
                    cryptor.bruteForceData();
                    System.out.println("Decrypted.");
                    break;
                case 4:
                    System.out.println("Bye.");
                    System.exit(0);
                    break;
            }

        } catch (Exception ex) {
            System.out.println("Wrong value. Exit.");
            System.exit(0);
        }
    }

    /**
     *
     */
    private static void printMenu() {
        for (String item : MENU) {
            System.out.println(item);
        }
    }

}
