import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DriverClass {
    private static final PrintStream syso = System.out;
    private static final Scanner scan = new Scanner(System.in);
    private static final Gson gson = new Gson();
    private static Book scratchBook = new Book("test", "1234", 0.99, 1, 0, "Teat Author", 1, "2012", 100, 0);
    private static List<Book> books = new ArrayList<>();

    public static void main(String[] args) {
        initList();
        generateMainMenu();
    }


    private static void initList() {
        try {
            books = gson.fromJson(new FileReader("Book Records.txt"), ListOfBooks.class).bookList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void generateMainMenu() {
        syso.println("\n1. Show all records\n2. Search for a book.\n3. Add a book.\n4. Delete a book.\n5. Modify book records.\n6. Apply discount to inventory\n7. View individual records\n8. Reset all records\n9. Exit program.");
        syso.println("Please select the index number of the operation that you want to perform: ");
        int selOption = scan.nextInt();
        switch (selOption) {
            case 1 -> {
                showAllRecords();
                generateMainMenu();
            }

            case 2 -> {
            }

            case 3 -> addBook();

            case 4 -> deleteBook();

            case 5 -> modifyBook(true);

            case 6 -> discountInventory();

            case 7 -> viewIndividualRecords(true, false, false);

            case 8 -> purgeAndResetRecords();

            case 9 -> System.exit(0);

            default -> {
                syso.println("Invalid choice, please select again.\n");
                generateMainMenu();
            }
        }
    }

    private static void showAllRecords() {
        Book book;
        for (int index = 0; index < books.size(); index++) {
            book = books.get(index);
            syso.println(index + 1 + ". " + book.getTitle() + " (vol" + book.getEdition() + ")");
        }
        syso.println("\n--------------------------------------------------------------------------");
    }

    private static void purgeAndResetRecords() {
        try {
            FileWriter writer = new FileWriter("Book Records.txt");
            PrintWriter printWriter = new PrintWriter(writer, false);
            printWriter.flush();
            printWriter.close();
            writer.close();
            books.clear();
            syso.println("\nPurge complete");
        } catch (IOException e) {
            syso.println("\nPurge failed");
            throw new RuntimeException(e);
        }

        initDefaultBookList();
        updateRecords();
        generateMainMenu();
    }

    private static void initDefaultBookList() {
        books.add(new Book("SAS data analytic development : dimensions of software quality", "1-119-25591-0", 89.99, 3, 0, "Hughes, Troy Martin", 1, "2016", 624, 0));
        books.add(new Book("Project Management Techniques and Innovations in Information Technology", "9781466609303", 149.99, 5, 2, "Wang, John", 1, "2012", 342, 5));
        books.add(new Book("The Definitive Guide to Terracotta Cluster the JVM for Spring, Hibernate and POJO Scalability", "1-281-75710-1", 89.99, 7, 6, "Terracotta Inc.", 1, "2008", 364, 0));
        books.add(new Book("Hands-On Software Engineering with Python: Move Beyond Basic Programming and Construct Reliable and Efficient Software with Complex Code", "1788622014", 89.99, 4, 0, "Allbee, Brian", 1, "01-01-2018", 364, 10));
        books.add(new Book("Guide to Software Development: Designing and Managing the Life Cycle", "9781447167976", 89.99, 3, 1, "Langer, Arthur M", 1, "2016", 162, 50));
        books.add(new Book("Making sense of agile project management balancing control and agility", "1-118-01570-3", 89.99, 3, 1, "Cobb, Charles G.", 1, "2011", 245, 15));
        books.add(new Book("Ethical IT Innovation: A Value-Based System Design Approach", "1482226359", 89.99, 3, 1, "Spiekermann, Sarah", 1, "2016", 283, 30));
        books.add(new Book("REPEATABILITY RELIABILITY SCALABILITY THROUGH GITOPS", "9781801077798", 89.99, 3, 1, "Bryan Feuling", 1, "2021", 350, 0));
        books.add(new Book("Industrial Cybersecurity", "9781788395151", 89.99, 3, 1, "Ackerman, Pascal", 1, "2017", 350, 60));

        try {
            FileWriter writer = new FileWriter("Book Records.txt");
            writer.write(gson.toJson(new ListOfBooks(books)));
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        syso.println("\nDefault list initialized.");
    }

    private static void updateRecords() {
        try {
            FileWriter writer = new FileWriter("Book Records.txt");
            PrintWriter printWriter = new PrintWriter(writer, false);
            printWriter.flush();
            writer.write(gson.toJson(new ListOfBooks(books)));
            printWriter.close();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void deleteBook() {
        int index = chooseBook();
        Book delBook = books.get(index);
        books.remove(index);
        updateRecords();
        syso.println("Book: " + delBook.title + "\nHas been deleted from the records.\n");
        generateMainMenu();
    }

    private static void modifyBook(boolean chooseBook) {
        if (chooseBook) {
            scratchBook = books.get(chooseBook());
        }

        syso.println("1. Title\n2. Author\n3. ISBN\n4. Price\n5. Quantity\n6. Number of books available\n7. Edition\n8. Release year\n9. Number of pages\n10. Apply discount\n11. Go back to main menu");
        syso.println("Please enter the index(serial number) of the field that you would like to change:");
        int selIndex = scan.nextInt();
        switch (selIndex) {
            case 1: {
                scratchBook.setTitle();
                break;
            }

            case 2: {
                scratchBook.setAuthors();
                break;
            }

            case 3: {
                scratchBook.setIsbn();
                break;
            }

            case 4: {
                scratchBook.setPrice();
                break;
            }

            case 5: {
                scratchBook.setQuantity(false);
                break;
            }

            case 6: {
                scratchBook.setNumAvailable();
                break;
            }

            case 7: {
                scratchBook.setEdition();
                break;
            }

            case 8: {
                scratchBook.setReleaseYear();
                break;
            }

            case 9: {
                scratchBook.setNumPages();
            }

            case 10: {
                scratchBook.setDiscount();
                break;
            }

            case 11: {
                generateMainMenu();
                break;
            }
            default: {
                syso.println("Invalid entry, please try again from the main menu");
                generateMainMenu();
                break;
            }
        }
        updateRecords();
        bookUpdatePrompt();
    }

    private static void bookUpdatePrompt() {
        while (true) {
            syso.println("Would you like to update any other fields of this book? (Y/N)");
            String answer = scan.next();
            if (answer.startsWith("Y") || answer.startsWith("y")) {
                modifyBook(false);
            } else {
                generateMainMenu();
                break;
            }
        }
    }

    private static void viewIndividualRecords(boolean chooseBook, boolean nextBook, boolean previousBook) {
        int index;
        if (chooseBook) {
            index = chooseBook();
            scratchBook = books.get(index);
        } else if (nextBook) {
            index = books.indexOf(scratchBook);
            scratchBook = books.get(++index);
        } else if (previousBook) {
            index = books.indexOf(scratchBook);
            scratchBook = books.get(--index);
        } else index = books.indexOf(scratchBook);


        syso.println("\nTitle: " + scratchBook.title + "\nAuthor: " + scratchBook.authors + "\nISBN: " + scratchBook.isbn + "RRP: $" + scratchBook.price + "\nSale price: " + scratchBook.discountedPrice + "\nQuantity on hand: " + scratchBook.quantity + "\nNumber of books available: " + scratchBook.numAvailable + "\nEdition: " + scratchBook.edition + "\nRelease year: " + scratchBook.releaseYear + "\nNumber of pages: " + scratchBook.numPages + "\nDiscount applied: " + scratchBook.discount);
        syso.println("\n--------------------------------------------------------------------------");
        int selChoice;
        if (index == books.size() - 1) {
            syso.println("\nEnter 2 for previous record, 3 to modify current record or 4 to go back to main menu: ");
            selChoice = scan.nextInt();
            while (true) {
                if (selChoice == 2 || selChoice == 3 || selChoice == 4) break;
                else {
                    syso.println("Invalid entry, please try again.");
                    syso.println("Enter 2 for previous record, 3 to modify current record or 4 to go back to main menu: ");
                    selChoice = scan.nextInt();
                }
            }
        } else if (index == 0) {
            syso.println("\nEnter 1 for next record, 3 to modify current record and 4 to go back to main menu: ");
            selChoice = scan.nextInt();
            while (true) {
                if (selChoice == 1 || selChoice == 3 || selChoice == 4) break;
                else {
                    syso.println("Invalid entry, please try again.");
                    syso.println("Enter 1 for next record, 3 to modify current record and 4 to go back to main menu: ");
                    selChoice = scan.nextInt();
                }
            }
        } else {
            syso.println("\nEnter 1 for next record, 2 for previous record, 3 to modify current record or 4 to go back to main menu: ");
            selChoice = scan.nextInt();
            while (true) {
                if (selChoice == 1 || selChoice == 2 || selChoice == 3 || selChoice == 4) break;
                else {
                    syso.println("Invalid entry, please try again.");
                    syso.println("Enter 1 for next record, 2 for previous record, 3 to modify current record or 4 to go back to main menu: ");
                    selChoice = scan.nextInt();
                }
            }
        }

        switch (selChoice) {
            case 1 -> viewIndividualRecords(false, true, false);
            case 2 -> viewIndividualRecords(false, false, true);
            case 3 -> modifyBook(false);
            case 4 -> generateMainMenu();
        }
    }

    private static int chooseBook() {
        showAllRecords();
        syso.println("Please enter the index of the book that you want to choose for the action: ");
        int index = scan.nextInt();
        while (true) {
            if (index <= books.size() && index > 0) break;
            else {
                syso.println("Index number cannot be less than 1 or greater than " + books.size() + ", please re-enter index:");
                index = scan.nextInt();
            }
        }
        return index - 1;
    }

    private static void discountInventory() {
        syso.println("To discount all inventory enter 0, to discount individual books, enter 0: ");
        int selChoice = scan.nextInt();
        while (true) {
            if (selChoice == 0 || selChoice == 1) break;
            else {
                syso.println("Invalid entry, please try again.");
                syso.println("To discount all inventory enter 0, to discount individual books, enter 0: ");
                selChoice = scan.nextInt();
            }
        }
        if (selChoice == 1) applyBulkDiscount();
        else applyIndividualDiscount();
    }

    private static void applyBulkDiscount() {
        syso.println("Please enter discount percentage between 0-100: ");
        double discount = scan.nextInt();
        while (true) {
            if (discount < 0.00 || discount > 100.00) {
                syso.println("Discount can only be a number between 0-100, please re-enter discount: ");
                discount = scan.nextInt();
            } else break;
        }
        scratchBook.discount = discount;
        books.forEach(it -> {
            it.discount = scratchBook.discount;
            it.updateDiscountedPrice();
        });
        syso.println("Prices for all books have been updated.\n");
        generateMainMenu();
    }

    private static void applyIndividualDiscount() {
        books.get(chooseBook()).setDiscount();
    }

    private static void addBook() {
        scratchBook.createNewBook();
        books.add(scratchBook);
        updateRecords();
        syso.println("\nBook: " + books.get(books.size() - 1).title + " added to the records");
        generateMainMenu();
    }

}
