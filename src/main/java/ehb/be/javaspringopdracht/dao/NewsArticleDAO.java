package ehb.be.javaspringopdracht.dao;

import ehb.be.javaspringopdracht.model.NewsArticle;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NewsArticleDAO extends CrudRepository<NewsArticle, Integer> {

}
