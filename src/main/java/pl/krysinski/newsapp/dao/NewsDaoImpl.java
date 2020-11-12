package pl.krysinski.newsapp.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import pl.krysinski.newsapp.model.NewsModel;
import pl.krysinski.newsapp.model.api.News;
import pl.krysinski.newsapp.model.api.NewsApi;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class NewsDaoImpl implements NewsDao {

    private JdbcTemplate jdbcTemplate;
    private List<NewsApi> newsList;

    @Autowired
    public NewsDaoImpl(JdbcTemplate jdbcTemplate) {
        RestTemplate restTemplate = new RestTemplate();
        News news = restTemplate.getForObject("https://api.currentsapi.services/v1/latest-news?language=pl&apiKey=Cd5LG_nUWxY-ZhijK8w1vCi994tT6w-MJrFkwx1V0K3BCPB9", News.class);
        newsList = news.getNews();
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addNewsFromApiToDb() {
        String truncateSql = "TRUNCATE TABLE news";
        jdbcTemplate.execute(truncateSql);

        String sql = "INSERT INTO news VALUES (?,?,?,?,?,?,?)";
        for (NewsApi newsApi : newsList) {
            jdbcTemplate.update(
                    sql,
                    newsApi.getId(),
                    newsApi.getTitle(),
                    newsApi.getDescription(),
                    newsApi.getUrl(),
                    newsApi.getAuthor(),
                    newsApi.getImage(),
                    newsApi.getPublished()
            );
        }
    }

    @Override
    public List<NewsModel> getAllNews() {
        List<NewsModel> newsList = new ArrayList<>();
        String sql = "SELECT * FROM news";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        maps.forEach(element ->
                newsList.add(new NewsModel(
                        String.valueOf(element.get("id")),
                        String.valueOf(element.get("title")),
                        String.valueOf(element.get("description")),
                        String.valueOf(element.get("url")),
                        String.valueOf(element.get("author")),
                        String.valueOf(element.get("image")),
                        String.valueOf(element.get("published"))
                )));
        return newsList;
    }

    @Override
    public boolean editNews(NewsModel news) {
        String sql = "UPDATE news SET title=?, description=?, url=?, author=?, image=?, published=? WHERE id=?";
        jdbcTemplate.update(sql, news.getTitle(), news.getDescription(),
                news.getUrl(), news.getAuthor(), news.getImage(), news.getPublished(), news.getId());
        return true;
    }

    @Override
    public NewsModel getNewsById(String id) {
        String sql = "SELECT * FROM news WHERE id=?";
        return jdbcTemplate.queryForObject(sql, new RowMapper<NewsModel>() {
            @Override
            public NewsModel mapRow(ResultSet resultSet, int i) throws SQLException {
                return new NewsModel(resultSet.getString("id"), resultSet.getString("title"),
                        resultSet.getString("description"), resultSet.getString("url"),
                        resultSet.getString("author"), resultSet.getString("image"),
                        resultSet.getString("published"));
            }
        }, id);
    }


}
