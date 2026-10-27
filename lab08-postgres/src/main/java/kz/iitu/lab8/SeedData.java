package kz.iitu.lab8;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Order(1)
public class SeedData implements CommandLineRunner {

    private final AuthorRepository authors;
    private final TagRepository tags;
    private final BookRepository books;

    public SeedData(AuthorRepository authors, TagRepository tags,
                    BookRepository books) {
        this.authors = authors;
        this.tags = tags;
        this.books = books;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (books.count() > 0) {
            return;
        }
        Tag java = tags.save(new Tag("java"));
        Tag spring = tags.save(new Tag("spring"));
        Tag history = tags.save(new Tag("history"));
        Tag classic = tags.save(new Tag("classic"));

        // author with three books
        Author bloch = new Author("Joshua Bloch", "USA");
        Book effective = new Book("Effective Java", Genre.TECH, 2018);
        effective.addTag(java);
        effective.addTag(classic);           // a book with two tags
        bloch.addBook(effective);
        bloch.addBook(new Book("Java Puzzlers", Genre.TECH, 2005));
        bloch.addBook(new Book("Java Generics", Genre.TECH, 2010));
        bloch.getBooks().get(1).addTag(java);
        bloch.getBooks().get(2).addTag(java);
        authors.save(bloch);                 // cascade saves the books

        Author martin = new Author("Robert Martin", "USA");
        Book clean = new Book("Clean Code", Genre.TECH, 2008);
        clean.addTag(java);
        martin.addBook(clean);
        Book architecture = new Book("Clean Architecture", Genre.TECH, 2017);
        architecture.addTag(classic);
        martin.addBook(architecture);
        authors.save(martin);

        Author walls = new Author("Craig Walls", "USA");
        Book inAction = new Book("Spring in Action", Genre.TECH, 2022);
        inAction.addTag(spring);
        inAction.addTag(java);               // second tag on one book
        walls.addBook(inAction);
        Book startHere = new Book("Spring Start Here", Genre.TECH, 2021);
        startHere.addTag(spring);
        walls.addBook(startHere);
        Book rome = new Book("Rome: A History", Genre.HISTORY, 1995);
        rome.addTag(history);
        walls.addBook(rome);
        authors.save(walls);

        System.out.println("Seeded: " + books.count() + " books");
    }
}
