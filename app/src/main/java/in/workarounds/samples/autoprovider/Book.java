package in.workarounds.samples.autoprovider;

import in.workarounds.autoprovider.AndroidId;
import in.workarounds.autoprovider.AutoIncrement;
import in.workarounds.autoprovider.Column;
import in.workarounds.autoprovider.NotNull;
import in.workarounds.autoprovider.PrimaryKey;
import in.workarounds.autoprovider.Table;

/**
 * Created by madki on 08/10/15.
 */
@Table(name = "BookTable")
public class Book {
    @AndroidId @Column
    public Long id;
    @NotNull @Column
    public String name;
    @Column
    public String author;
    @Column
    public String genre;
    @Column
    public Float rating;

    public Book() {}

    public Book(String name, String author, String genre, Float rating) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return id + " " + name + " " + author + " " + genre + " " + rating;
    }
}
