package ehb.be.javaspringopdracht.dao;

import ehb.be.javaspringopdracht.model.NewsArticle;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface NewsArticleDAO extends CrudRepository<NewsArticle, Integer> {

    @Query("SELECT n FROM NewsArticle n ORDER BY n.dateCreated DESC LIMIT 10 ")
    List<NewsArticle> findAllCronologic();
}
