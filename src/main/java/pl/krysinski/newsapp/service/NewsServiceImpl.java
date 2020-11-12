package pl.krysinski.newsapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.krysinski.newsapp.dao.NewsDao;
import pl.krysinski.newsapp.model.NewsModel;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    private NewsDao newsDao;

    @Autowired
    public NewsServiceImpl(NewsDao newsDao) {
        this.newsDao = newsDao;
    }

    @Override
    public List<NewsModel> getAllNews() {
        return newsDao.getAllNews();
    }

    @Override
    public NewsModel getNewsById(String id) {
        return newsDao.getNewsById(id);
    }

    @Override
    public boolean editNews(NewsModel news) {
       return newsDao.editNews(news);
    }
}
