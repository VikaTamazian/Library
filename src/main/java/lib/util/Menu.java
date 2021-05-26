package lib.util;

import lib.model.Library;

import java.util.Scanner;

public class Menu {
    Scanner scanner = new Scanner(System.in);
    Library library = new Library();

    public void enterMenu() throws InterruptedException {
        System.out.println("You are in Library. What you are going to do? Please, make your choice: "
                + "\n1 - Watch books' collection."
                + "\n2 - Add new book."
                + "\n3 - Remove definite book."
                + "\n4 - Edit book's information."
                + "\n5 - Save and quite.");

        int userChoice = scanner.nextInt();
        while (userChoice < 1 || userChoice > 5) {
            System.out.println("Please, enter correct number (1-5)");
            enterMenu();
        }
        switch (userChoice) {
            case 1: {
                System.out.println("Would you like to see all books or definite one?"
                        + "\n1 - load all books"
                        + "\n2 - load one"
                        + "\nPlease, make your choice: ");
                userChoice = scanner.nextInt();
                while (userChoice < 1 || userChoice > 2) {
                    System.out.println("Please, enter correct number (1-2)");
                    enterMenu();
                }

                switch (userChoice) {
                    case 1:
                        System.out.println("All books in library:");
                        library.loadAll();
                        enterMenu();

                    case 2:
                        System.out.println("Please, enter ID of the book");
                        userChoice = scanner.nextInt();
                        library.loadBook(userChoice);
                        enterMenu();
                }
            }
            case 2: {
                System.out.println("Would you like to add book manually or load it from file?"
                        + "\n1 - load from file"
                        + "\n2 - add manually"
                        + "\nPlease, make your choice: ");
                userChoice = scanner.nextInt();
                while (userChoice < 1 || userChoice > 2) {
                    System.out.println("Please, enter correct number (1-2)");
                    enterMenu();
                }

                switch (userChoice) {
                    case 1:
                        library.addFromFile();
                        System.out.println("File has been loaded successfully.");
                        enterMenu();

                    case 2:
                        library.addBookManually();
                        enterMenu();
                }
            }
            case 3: {
                System.out.println("Please, enter ID of the book");
                userChoice = scanner.nextInt();
                library.remove(userChoice);
                enterMenu();
            }


            case 4: {
                System.out.println("Please, enter ID of the book");
                userChoice = scanner.nextInt();
                library.edit(userChoice);
                System.out.println("Book has been edited successfully.");
                enterMenu();
            }

            case 5: {
                System.out.println("Data saving...");
                Thread.sleep(1000);
                System.out.println("Bye-bye.");
                break;
            }

        }
    }

}
