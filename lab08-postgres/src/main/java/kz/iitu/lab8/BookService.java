package kz.iitu.lab8;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public Page<BookResponse> find(Genre genre, Pageable pageable) {
        Page<Book> page = (genre == null)
                ? repository.findAll(pageable)
                : repository.findPageByGenre(genre, pageable);
        return page.map(this::toResponse);
    }

    public List<BookResponse> search(Genre genre, int year) {
        return repository.search(genre, year).stream().map(this::toResponse).toList();
    }

    public long countByAuthor(String name) {
        return repository.countByAuthorName(name);
    }

    @Transactional
    public int archiveOlderThan(int year) {
        return repository.archiveOlderThan(year);
    }

    // prints how many SQL statements each approach needs (watch the show-sql log)
    public void nPlusOneDemo() {
        List<Book> books = repository.findAll();
        for (Book b : books) {
            System.out.println("N+1 author: " + b.getAuthor().getName());
        }
        List<Book> joined = repository.findAllWithAuthor();
        for (Book b : joined) {
            System.out.println("JOIN FETCH author: " + b.getAuthor().getName());
        }
    }

    // calls the three derived queries that have no HTTP endpoint, so their SQL shows in the log
    public void derivedDemo() {
        System.out.println("findByGenre: " + repository.findByGenre(Genre.TECH).size());
        System.out.println("findByTitleContainingIgnoreCase: " + repository.findByTitleContainingIgnoreCase("spring").size());
        System.out.println("findTop3ByGenreOrderByYearDesc: " + repository.findTop3ByGenreOrderByYearDesc(Genre.TECH).size());
    }

    private BookResponse toResponse(Book b) {
        return new BookResponse(
                b.getId(), b.getTitle(), b.getGenre(), b.getYear(), b.isArchived(),
                b.getAuthor() == null ? null : b.getAuthor().getName(),
                b.getTags().stream().map(Tag::getName).sorted().toList());
    }
}
