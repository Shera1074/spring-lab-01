package kz.iitu.lab8;

import org.springframework.data.domain.Pageable;

import org.springframework.data.web.PagedModel;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    // paged and sorted: /api/books?page=1&size=3&sort=year,desc
    @GetMapping
    public PagedModel<BookResponse> list(@RequestParam(required = false) Genre genre,
                                         @PageableDefault(size = 5, sort = "title") Pageable pageable) {
        return new PagedModel<>(service.find(genre, pageable));
    }

    @GetMapping("/search")
    public List<BookResponse> search(@RequestParam Genre genre, @RequestParam int year) {
        return service.search(genre, year);
    }

    @GetMapping("/count")
    public long count(@RequestParam String author) {
        return service.countByAuthor(author);
    }

    @PostMapping("/archive")
    public int archive(@RequestParam int year) {
        return service.archiveOlderThan(year);
    }
}
