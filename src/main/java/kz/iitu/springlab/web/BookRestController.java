package kz.iitu.springlab.web;

import kz.iitu.springlab.catalog.Book;
import kz.iitu.springlab.catalog.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import kz.iitu.springlab.dto.BookRequest;
import kz.iitu.springlab.dto.BookMapper;
import kz.iitu.springlab.dto.ValidationGroups;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/api/books")
public class BookRestController {

    private final BookService service;

    public BookRestController(BookService service) {
        this.service = service;
    }

    @GetMapping
    public List<Book> list(@RequestParam(required = false) String author,
                           @RequestParam(defaultValue = "10") int limit) {
        return service.findAll(author).stream().limit(limit).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> find(@PathVariable long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Book> create(
            @RequestBody
            @Validated(ValidationGroups.Create.class)
            BookRequest request) {

        Book book = BookMapper.toEntity(request, null);

        Book saved = service.create(book);

        return ResponseEntity
                .created(URI.create("/api/books/" + saved.id()))
                .body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> replace(
            @PathVariable long id,
            @RequestBody
            @Validated(ValidationGroups.Update.class)
            BookRequest request) {

        if (!java.util.Objects.equals(request.id(), id)) {
            return ResponseEntity.badRequest().build();
        }
        Book book = BookMapper.toEntity(request, id);

        return service.replace(id, book)
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        return service.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}