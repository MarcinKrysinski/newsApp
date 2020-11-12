package pl.krysinski.newsapp.service;

import pl.krysinski.newsapp.model.NewsModel;

import java.util.List;

public interface NewsService {

    List<NewsModel> getAllNews();

    NewsModel getNewsById(String id);

    boolean editNews(NewsModel news);
}
