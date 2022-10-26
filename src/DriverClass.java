import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class DriverClass {
    private static final PrintStream syso = System.out;
    private static final Scanner scan = new Scanner(System.in);
    private static final Gson gson = new Gson();
    private static List<Book> filteredList = List.of();
    private static Book scratchBook = new Book("test", "1234", 0.99, 1, 0, "Teat Author", 1, 2012, 100, 0);
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
        syso.println("\n1. Show all records\n2. Search for a book.\n3. Add a book.\n4. Delete a book.\n5. Modify book records.\n6. Apply discount to inventory\n7. View individual records\n8. Lend out a book\n9. Return a book\n10. Reset all records\n11. Exit program");
        syso.println("Please select the index number of the operation that you want to perform: ");
        int selOption = scan.nextInt();
        switch (selOption) {
            case 1: {
                showAllRecords();
                generateMainMenu();
                break;
            }

            case 2: {
                searchRecords();
                break;
            }

            case 3: {
                addBook();
                break;
            }

            case 4: {
                deleteBook();
                break;
            }

            case 5:
                modifyBook(true);

            case 6: {
                discountInventory();
                break;
            }

            case 7: {
                viewIndividualRecords(true, false, false);
                break;
            }

            case 8: {
                lendBook();
                break;
            }

            case 9: {
                returnBook();
                break;
            }

            case 10: {
                purgeAndResetRecords();
                break;
            }

            case 11: {
                System.exit(0);
                break;
            }

            default: {
                syso.println("Invalid choice, please select again.\n");
                generateMainMenu();
            }
        }
    }

    private static void searchRecords() {
        syso.println("Search by\n1. Title\n2. Author\n3. ISBN\n4. Price\n5. Quantity\n6. Number of books available\n7. Edition\n8. Release year\n9. Number of pages\n10. Discount percentage");
        syso.println("Please enter index number of your search parameter: ");
        var selChoice = scan.nextInt();
        while (true) {
            if (selChoice > 0 && selChoice < 10) break;
            else {
                syso.println("Invalid choice, please try again.");
                syso.println("Please enter index number of your search parameter: ");
                selChoice = scan.nextInt();
            }
        }
        searchBy(selChoice);
    }

    private static void searchBy(int selChoice) {
        switch (selChoice) {
            case 1: {
                syso.println("Please enter the title: ");
                scan.nextLine();
                var searchPhrase = scan.nextLine();
                syso.println(searchPhrase);
                filteredList = books.stream().filter(it -> it.title.contains(searchPhrase)).collect(Collectors.toList());
                break;
            }
            case 2: {
                syso.println("Please enter the author name: ");
                scan.nextLine();
                var searchPhrase = scan.nextLine();
                filteredList = books.stream().filter(it -> it.authors.contains(searchPhrase)).collect(Collectors.toList());
                break;
            }
            case 3: {
                syso.println("Please enter the ISBN: ");
                scan.nextLine();
                var searchPhrase = scan.nextLine();
                filteredList = books.stream().filter(it -> it.isbn.contains(searchPhrase)).collect(Collectors.toList());
                break;
            }
            case 4: {
                syso.println("Please enter the price range.\nMinimum price: ");
                var min = scan.nextInt();
                syso.println("Maximum price");
                var max = scan.nextInt();
                if (max > min)
                    filteredList = books.stream().filter(it -> it.discountedPrice <= max && it.price >= min).collect(Collectors.toList());
                else if (max == min)
                    filteredList = books.stream().filter(it -> it.discountedPrice == min).collect(Collectors.toList());
                else
                    filteredList = books.stream().filter(it -> it.discountedPrice <= min && it.price >= max).collect(Collectors.toList());
                break;
            }
            case 5: {
                syso.println("Please enter quantity: ");
                var quantity = scan.nextInt();
                syso.println("1. See books with quantity greater than and equal to " + quantity);
                syso.println("2. See books with quantity less than and equal to " + quantity);
                syso.println("3. See books with quantity greater than " + quantity);
                syso.println("4. See books with quantity less than " + quantity);
                syso.println("Please enter the index number of the type of filter: ");
                var choice = scan.nextInt();
                while (true) {
                    if (choice > 0 && choice < 5) break;
                    else {
                        syso.println("Invalid choice, please enter your choice again: ");
                        choice = scan.nextInt();
                    }
                }

                switch (choice) {
                    case 1: {
                        filteredList = books.stream().filter(it -> it.quantity >= quantity).collect(Collectors.toList());
                        break;
                    }
                    case 2: {
                        filteredList = books.stream().filter(it -> it.quantity <= quantity).collect(Collectors.toList());
                        break;
                    }
                    case 3: {
                        filteredList = books.stream().filter(it -> it.quantity > quantity).collect(Collectors.toList());
                        break;
                    }
                    case 4: {
                        filteredList = books.stream().filter(it -> it.quantity < quantity).collect(Collectors.toList());
                        break;
                    }
                }
                break;
            }
            case 6: {
                syso.println("Please enter number of available books: ");
                var available = scan.nextInt();
                syso.println("1. See which books have available greater than and equal to " + available);
                syso.println("2. See which books have available less than and equal to " + available);
                syso.println("3. See which books have available greater than " + available);
                syso.println("4. See which books have available less than " + available);
                syso.println("Please enter the index number of the type of filter: ");
                var choice = scan.nextInt();
                while (true) {
                    if (choice > 0 && choice < 5) break;
                    else {
                        syso.println("Invalid choice, please enter your choice again: ");
                        choice = scan.nextInt();
                    }
                }

                switch (choice) {
                    case 1: {
                        filteredList = books.stream().filter(it -> it.numAvailable >= available).collect(Collectors.toList());
                        break;
                    }
                    case 2: {
                        filteredList = books.stream().filter(it -> it.numAvailable <= available).collect(Collectors.toList());
                        break;
                    }
                    case 3: {
                        filteredList = books.stream().filter(it -> it.numAvailable > available).collect(Collectors.toList());
                        break;
                    }
                    case 4: {
                        filteredList = books.stream().filter(it -> it.numAvailable < available).collect(Collectors.toList());
                        break;
                    }
                }
            }
            case 7: {
                syso.println("Please enter edition number: ");
                var edition = scan.nextInt();
                filteredList = books.stream().filter(it -> it.edition == edition).collect(Collectors.toList());
            }
            case 8: {
            }
            case 9: {
                syso.println("Please enter the page range.\nMinimum pages: ");
                var min = scan.nextInt();
                syso.println("Maximum pages");
                var max = scan.nextInt();
                if (max > min)
                    filteredList = books.stream().filter(it -> it.numPages <= max && it.numPages >= min).collect(Collectors.toList());
                else if (max == min)
                    filteredList = books.stream().filter(it -> it.numPages == min).collect(Collectors.toList());
                else
                    filteredList = books.stream().filter(it -> it.numPages <= min && it.numPages >= max).collect(Collectors.toList());
                break;
            }
            case 10: {
                syso.println("Please enter quantity: ");
                var discount = scan.nextInt();
                syso.println("1. See books with quantity greater than and equal to " + discount);
                syso.println("2. See books with quantity less than and equal to " + discount);
                syso.println("3. See books with quantity greater than " + discount);
                syso.println("4. See books with quantity less than " + discount);
                syso.println("Please enter the index number of the type of filter: ");
                var choice = scan.nextInt();
                while (true) {
                    if (choice > 0 && choice < 5) break;
                    else {
                        syso.println("Invalid choice, please enter your choice again: ");
                        choice = scan.nextInt();
                    }
                }

                switch (choice) {
                    case 1: {
                        filteredList = books.stream().filter(it -> it.discount >= discount).collect(Collectors.toList());
                        break;
                    }
                    case 2: {
                        filteredList = books.stream().filter(it -> it.discount <= discount).collect(Collectors.toList());
                        break;
                    }
                    case 3: {
                        filteredList = books.stream().filter(it -> it.discount > discount).collect(Collectors.toList());
                        break;
                    }
                    case 4: {
                        filteredList = books.stream().filter(it -> it.discount < discount).collect(Collectors.toList());
                        break;
                    }
                }


                break;
            }
        }
        if (filteredList.isEmpty()) {
            syso.println("No items returned for this search parameter.\nEnter 1 to try search again or 2 to return to the main menu");
            int choice = scan.nextInt();
            while (true) {
                if (choice == 1 || choice == 2) break;
                else {
                    syso.println("Invalid choice, please try again.\nEnter 1 to try search again or 2 to return to the main menu");
                    choice = scan.nextInt();
                }
            }
            if (choice == 1) searchRecords();
            else generateMainMenu();
        } else workOnFilteredList();
    }

    private static void workOnFilteredList() {
        showFilteredRecords();
        syso.println("Do you want to modify items in the filtered list? Enter 1 for yes, 2 for no: ");
        int choice = scan.nextInt();
        while (true) {
            if (choice == 1 || choice == 2) break;
            else {
                syso.println("Invalid choice, please try again.\nDo you want to modify items in the filtered list? Enter 1 for yes, 2 for no: ");
                choice = scan.nextInt();
            }
        }
        if (choice == 1) {
            scratchBook = filteredList.get(chooseFilteredBook());
            modifyBook(false);
        } else generateMainMenu();
    }

    private static void showFilteredRecords() {
        Book book;
        for (int index = 0; index < filteredList.size(); index++) {
            book = filteredList.get(index);
            syso.println(index + 1 + ". " + book.title + " (vol" + book.edition + ")");
        }
        syso.println("\n--------------------------------------------------------------------------");
    }

    private static void showAllRecords() {
        Book book;
        for (int index = 0; index < books.size(); index++) {
            book = books.get(index);
            syso.println(index + 1 + ". " + book.title + " (vol" + book.edition + ")");
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
        books.add(new Book("SAS data analytic development : dimensions of software quality", "1-119-25591-0", 49.99, 3, 0, "Hughes, Troy Martin", 1, 2016, 624, 0));
        books.add(new Book("Project Management Techniques and Innovations in Information Technology", "9781466609303", 149.99, 5, 2, "Wang, John", 1, 2012, 342, 5));
        books.add(new Book("The Definitive Guide to Terracotta Cluster the JVM for Spring, Hibernate and POJO Scalability", "1-281-75710-1", 249.99, 7, 6, "Terracotta Inc.", 1, 2008, 364, 0));
        books.add(new Book("Hands-On Software Engineering with Python: Move Beyond Basic Programming and Construct Reliable and Efficient Software with Complex Code", "1788622014", 349.99, 4, 0, "Allbee, Brian", 1, 2018, 364, 10));
        books.add(new Book("Guide to Software Development: Designing and Managing the Life Cycle", "9781447167976", 99.99, 3, 1, "Langer, Arthur M", 1, 2016, 162, 50));
        books.add(new Book("Making sense of agile project management balancing control and agility", "1-118-01570-3", 89.99, 3, 1, "Cobb, Charles G.", 1, 2011, 245, 15));
        books.add(new Book("Ethical IT Innovation: A Value-Based System Design Approach", "1482226359", 29.99, 3, 1, "Spiekermann, Sarah", 1, 2016, 283, 30));
        books.add(new Book("REPEATABILITY RELIABILITY SCALABILITY THROUGH GITOPS", "9781801077798", 64.99, 3, 1, "Bryan Feuling", 1, 2021, 350, 0));
        books.add(new Book("Industrial Cybersecurity", "9781788395151", 399.99, 3, 1, "Ackerman, Pascal", 1, 2017, 350, 60));

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

        syso.println("\nTitle: " + scratchBook.title + "\nAuthor: " + scratchBook.authors + "\nISBN: " + scratchBook.isbn + "\nRRP: $" + scratchBook.price + "\nSale price: $" + scratchBook.discountedPrice + "\nQuantity on hand: " + scratchBook.quantity + "\nNumber of books available: " + scratchBook.numAvailable + "\nEdition: " + scratchBook.edition + "\nRelease year: " + scratchBook.releaseYear + "\nNumber of pages: " + scratchBook.numPages + "\nDiscount applied: " + scratchBook.discount + ("%"));
        syso.println("\n--------------------------------------------------------------------------");

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

    private static void lendBook() {
        scratchBook = books.get(chooseBook());
        if (scratchBook.numAvailable > 0) {
            --scratchBook.numAvailable;
            syso.println("Book lent out, stock updated.");
        } else {
            syso.println("Not enough books available in stock, unable to lend out.");
        }
        updateRecords();
        generateMainMenu();
    }

    private static void returnBook() {
        scratchBook = books.get(chooseBook());
        if (scratchBook.numAvailable < scratchBook.quantity) {
            ++scratchBook.numAvailable;
            syso.println("Book added to the stock.");
        } else {
            syso.println("Unable to return book to record as all available books are already in stock.");
        }
        updateRecords();
        generateMainMenu();
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


        syso.println("\nTitle: " + scratchBook.title + "\nAuthor: " + scratchBook.authors + "\nISBN: " + scratchBook.isbn + "\nRRP: $" + scratchBook.price + "\nSale price: $" + scratchBook.discountedPrice + "\nQuantity on hand: " + scratchBook.quantity + "\nNumber of books available: " + scratchBook.numAvailable + "\nEdition: " + scratchBook.edition + "\nRelease year: " + scratchBook.releaseYear + "\nNumber of pages: " + scratchBook.numPages + "\nDiscount applied: " + scratchBook.discount + ("%"));
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
            case 1: {
                viewIndividualRecords(false, true, false);
                break;
            }
            case 2: {
                viewIndividualRecords(false, false, true);
                break;
            }
            case 3: {
                modifyBook(false);
                break;
            }
            case 4: {
                generateMainMenu();
                break;
            }
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

    private static int chooseFilteredBook() {
        syso.println("Please enter the index number from the filtered list of the books, that you want to modify: ");
        int index = scan.nextInt();
        while (true) {
            if (index <= filteredList.size() && index > 0) break;
            else {
                syso.println("Index number cannot be less than 1 or greater than " + filteredList.size() + ", please re-enter index:");
                index = scan.nextInt();
            }
        }
        return index - 1;
    }

    private static void discountInventory() {
        syso.println("To discount all inventory enter 0, to discount individual books, enter 1: ");
        int selChoice = scan.nextInt();
        while (true) {
            if (selChoice == 0 || selChoice == 1) break;
            else {
                syso.println("Invalid entry, please try again.");
                syso.println("To discount all inventory enter 0, to discount individual books, enter 1: ");
                selChoice = scan.nextInt();
            }
        }
        if (selChoice == 0) applyBulkDiscount();
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
