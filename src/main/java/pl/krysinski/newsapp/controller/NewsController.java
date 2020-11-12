package pl.krysinski.newsapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.krysinski.newsapp.model.NewsModel;
import pl.krysinski.newsapp.service.NewsService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/news", produces = {MediaType.APPLICATION_JSON_VALUE})
public class NewsController {

    private NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping
    public ResponseEntity<List<NewsModel>> getAllNews() {
        return new ResponseEntity<>(newsService.getAllNews(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsModel> getNewsById(@PathVariable String id) {
        Optional<NewsModel> newsById = Optional.ofNullable(newsService.getNewsById(id));
        if (newsById.isPresent()) {
            return new ResponseEntity<>(newsById.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping
    public ResponseEntity<?> editNews(@RequestBody NewsModel newNews) {
        boolean editNews = newsService.editNews(newNews);
        if (editNews) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
