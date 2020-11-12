package pl.krysinski.newsapp.dao;

import pl.krysinski.newsapp.model.NewsModel;

import java.util.List;

public interface NewsDao {

    void addNewsFromApiToDb();

    List<NewsModel> getAllNews();

    boolean editNews(NewsModel news);

    NewsModel getNewsById(String id);

}
