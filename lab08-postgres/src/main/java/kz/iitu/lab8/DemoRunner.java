package kz.iitu.lab8;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class DemoRunner implements CommandLineRunner {

    private final BookService service;

    public DemoRunner(BookService service) {
        this.service = service;
    }

    @Override
    public void run(String... args) {
        service.nPlusOneDemo();
        service.derivedDemo();
        System.out.println("archived by year<2000: " + service.archiveOlderThan(2000));
    }
}
