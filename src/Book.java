import java.io.PrintStream;
import java.util.Scanner;

public class Book {
    private static final PrintStream syso = System.out;
    private static final Scanner scan = new Scanner(System.in);
    String title;
    String isbn;
    double price;
    double discountedPrice;
    int quantity;
    int numAvailable;
    String authors;
    int edition;
    int releaseYear;
    int numPages;
    double discount;

    public Book(String title, String isbn, Double price, int quantity, int numAvailable, String authors, int edition, int releaseYear, int numPages, double discount) {
        this.title = title;
        this.isbn = isbn;
        this.price = price;
        this.quantity = quantity;
        this.numAvailable = numAvailable;
        this.authors = authors;
        this.edition = edition;
        this.releaseYear = releaseYear;
        this.numPages = numPages;
        this.discount = discount;
        discountedPrice = price * (100 - discount) / 100;
    }

    public static boolean isbnValidation(String isbn) {
        boolean isValid = false;
        for (int i = 0; i < isbn.length(); i++) {
            if (Character.isDigit(isbn.charAt(i)) || Character.toString(isbn.charAt(i)).equals("-")) {
                isValid = true;
            } else {
                isValid = false;
                break;
            }
        }
        return isValid;
    }

    public void setQuantity(boolean setAvailable) {
        if (setAvailable) {
            numAvailable = 0;
        }
        syso.println("Please enter quantity being added to the inventory ");
        int quantity = scan.nextInt();
        while (true) {
            if (quantity < 0 || quantity < numAvailable) {
                syso.println("Quantity cannot be less than the available books(" + numAvailable + "), please re-enter the quantity: ");
                quantity = scan.nextInt();
            } else break;
        }
        this.quantity = quantity;
    }


    public void createNewBook() {
        syso.println("Note: none of the fields can be empty or 0 with the exception of number of books available\n");
        setTitle();
        setIsbn();
        setAuthors();
        setReleaseYear();
        setPrice();
        setQuantity(true);
        numAvailable = quantity;
        setEdition();
        setNumPages();
    }

    public void setTitle() {
        syso.println("Please enter book title: ");
        String title = scan.nextLine();

        while (true) {
            if (title.length() < 2) {
                syso.println("Title cannot be empty or contain just 1 character, please re-enter the title:");
                title = scan.nextLine();
            } else break;
        }
        this.title = title;
    }

    public void setIsbn() {
        syso.println("Please enter book ISBN: ");
        String isbn = scan.next();
        while (true) {
            if (!isbnValidation(isbn)) {
                syso.println("ISBN can only contain digits or '-', please re-enter ISBN: ");
                isbn = scan.next();
                isbnValidation(isbn);
            } else break;
        }
        this.isbn = isbn;
    }

    public void setPrice() {
        syso.println("Please enter the price of the book: ");
        double price = scan.nextDouble();
        while (true) {
            if (price < 0.00) {
                syso.println("Price cannot be less than 0, please re-enter the price: ");
                price = scan.nextDouble();
            } else break;
        }
        this.price = price;
    }

    public void updateDiscountedPrice() {
        discountedPrice = price * (100 - discount) / 100;
    }

    public void setNumAvailable() {
        syso.println("Please enter the number of books available: ");
        int numAvailable = scan.nextInt();
        while (true) {
            if (numAvailable < 0) {
                syso.println("Number of available books cannot be less than 0, please re-enter number of books available: ");
                numAvailable = scan.nextInt();
            } else if (numAvailable > quantity) {
                syso.println("Number of available books cannot be greater than the quantity (" + quantity + ") of books, please re-enter available books: ");
                numAvailable = scan.nextInt();
            } else break;
        }
        this.numAvailable = numAvailable;
    }

    public void setAuthors() {
        syso.println("Please enter book author(s)");
        syso.println("Note: for multiple authors, separate by commas: ");
        String authors = scan.next();
        while (true) {
            if (authors.length() < 2) {
                syso.println("Authors field cannot be empty or have only one character, please re-enter authors: ");
                authors = scan.next();
            } else break;
        }
        this.authors = authors;
    }

    public void setEdition() {
        syso.println("Please enter the book's edition number ");
        int edition = scan.nextInt();
        while (true) {
            if (edition < 1) {
                syso.println("Book edition cannot be 0 or negative, please re-enter book edition: ");
                edition = scan.nextInt();
            } else break;
        }
        this.edition = edition;
    }

    public void setReleaseYear() {
        syso.println("Please enter the release year of the book in 'yyyy' format (minimum 1500, maximum 2022): ");
        int releaseYear = scan.nextInt();
        while (true) {
            if (releaseYear > 1499 && releaseYear < 2023) break;
            else {
                syso.println("Invalid year, please try again: ");
                releaseYear = scan.nextInt();
            }
        }
        this.releaseYear = releaseYear;
    }

    public void setNumPages() {
        syso.println("Please enter the number of pages in the book ");
        int numPages = scan.nextInt();
        while (true) {
            if (numPages < 1) {
                syso.println("Number of pages cannot be less than 0, please re-enter number of pages: ");
                numPages = scan.nextInt();
            } else break;
        }
        this.numPages = numPages;
    }

    public void setDiscount() {
        syso.println("Please enter discount percentage between 0-100: ");
        double discount = scan.nextDouble();
        while (true) {
            if (discount < 0.00 || discount > 100.00) {
                syso.println("Discount can only be a number between 0-100, please re-enter discount: ");
                discount = scan.nextDouble();
            } else break;
        }
        this.discount = discount;
        updateDiscountedPrice();
    }

}
