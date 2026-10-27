package kz.iitu.lab8;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    // five derived queries: no SQL written, the name is the query
    List<Book> findByGenre(Genre genre);

    List<Book> findByTitleContainingIgnoreCase(String fragment);

    List<Book> findByYearBetweenOrderByYearDesc(int from, int to);

    List<Book> findTop3ByGenreOrderByYearDesc(Genre genre);

    long countByAuthorName(String name); // nested property: author.name

    // the same derived query, but paged and sorted
    Page<Book> findPageByGenre(Genre genre, Pageable pageable);

    // two written queries: JPQL with named parameters, and a bulk update
    @Query("select b from Book b where b.genre = :genre and b.year >= :year order by b.title")
    List<Book> search(@Param("genre") Genre genre, @Param("year") int year);

    @Modifying(clearAutomatically = true)
    @Query("update Book b set b.archived = true where b.year < :year")
    int archiveOlderThan(@Param("year") int year);

    // N+1 cure: one query with the author joined in
    @Query("select b from Book b join fetch b.author")
    List<Book> findAllWithAuthor();
}
