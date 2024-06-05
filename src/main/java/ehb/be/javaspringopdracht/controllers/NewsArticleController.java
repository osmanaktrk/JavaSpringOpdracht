package ehb.be.javaspringopdracht.controllers;

import ehb.be.javaspringopdracht.dao.NewsArticleDAO;
import ehb.be.javaspringopdracht.model.NewsArticle;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class NewsArticleController {

    private final NewsArticleDAO newsArticleDAO;

    @Autowired
    public NewsArticleController(NewsArticleDAO newsArticleDAO) {
        this.newsArticleDAO = newsArticleDAO;
    }

    @ModelAttribute("all")
    public Iterable<NewsArticle> findAll(){
        return newsArticleDAO.findAll();
    }

    @ModelAttribute("lastTen")
    public Iterable<NewsArticle> findLastTen(){
        List<NewsArticle> last = newsArticleDAO.findAllCronologic();
        List<NewsArticle> lastTen = new ArrayList<>();

        if(!last.isEmpty()){
            if (last.size()>9){
                for (int i = 0; i < 10; i++) {
                    lastTen.add(last.get(i));

                }
            }else{
                lastTen.addAll(last);
            }
        }

        return lastTen;
    }

    @ModelAttribute("newArticle")
    public NewsArticle toSave(){
        return new NewsArticle();
    }



    @GetMapping(value = {"/", "/index"})
    public String showIndex(ModelMap map){

        return "index";
    }

    @PostMapping(value = {"/", "/index"})
    public String save(@ModelAttribute("newArticle") @Valid NewsArticle newArticle, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "index";
        }
        newsArticleDAO.save(newArticle);
        return "redirect:/index";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable int id, ModelMap map){
        Optional<NewsArticle> article = newsArticleDAO.findById(id);
        if(article.isPresent()){
            map.addAttribute("article", article.get());
            return "details";
        }

        return "redirect:/index";
    }



}
