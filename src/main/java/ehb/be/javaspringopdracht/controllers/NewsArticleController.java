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
        List<NewsArticle> lastTen = newsArticleDAO.findAllCronologic();

        return lastTen;
    }

    @ModelAttribute("newArticle")
    public NewsArticle saveNew(){
        return new NewsArticle();
    }



    @GetMapping(value = {"/", "/index"})
    public String showIndex(ModelMap map){

        return "index";
    }


    @GetMapping("/details/{id}")
    public String getDetails(@PathVariable int id, ModelMap map){
        Optional<NewsArticle> article = newsArticleDAO.findById(id);
        if(article.isPresent()){
            map.addAttribute("article", article.get());
            return "details";
        }

        return "redirect:/index";
    }


    @GetMapping("/new")
    public String showNew(){
        return "new";
    }

    @PostMapping("/new")
    public String makeArticle(@ModelAttribute("newArticle")@Valid NewsArticle toSave, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "new";
        }
        newsArticleDAO.save(toSave);
        return "redirect:/index";
    }

    @GetMapping("/about")
    public String showAbout(){
        return "about";
    }

    @GetMapping("/all")
    public String showAll(ModelMap map){
        return "all";
    }


}
