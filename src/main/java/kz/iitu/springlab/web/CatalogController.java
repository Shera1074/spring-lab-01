package kz.iitu.springlab.web;

import kz.iitu.springlab.service.CatalogService;
import org.springframework.aop.support.AopUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CatalogController {

    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/api/lab4/item/{id}")
    public String item(@PathVariable long id) {
        return catalogService.findById(id);
    }

    @GetMapping("/api/lab4/items")
    public List<String> items(@RequestParam(defaultValue = "5") int limit) {
        return catalogService.findAll(limit);
    }

    @DeleteMapping("/api/lab4/item/{id}")
    public String remove(@PathVariable long id) {
        return catalogService.remove(id);
    }

    // Для проверки CGLIB Proxy (Task 4.1)
    @GetMapping("/api/lab4/proxy")
    public Map<String, Object> getProxyInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("serviceClass", catalogService.getClass().getName());
        info.put("isAopProxy", AopUtils.isAopProxy(catalogService));
        info.put("isCglibProxy", AopUtils.isCglibProxy(catalogService));
        return info;
    }

    // Для проверки self-invocation (Task 4.4)
    @GetMapping("/api/lab4/remove-twice/{id}")
    public String removeTwice(@PathVariable long id) {
        return catalogService.removeTwice(id);
    }
}