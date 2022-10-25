import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListOfBooks {
    @SerializedName("books")
    public List<Book> bookList;

    public ListOfBooks(List<Book> books) {
        this.bookList = books;
    }

}
