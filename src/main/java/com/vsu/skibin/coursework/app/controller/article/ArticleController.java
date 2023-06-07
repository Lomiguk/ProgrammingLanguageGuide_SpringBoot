package com.vsu.skibin.coursework.app.controller.article;

import com.vsu.skibin.coursework.app.api.data.dto.ArticleDTO;
import com.vsu.skibin.coursework.app.api.data.dto.TagDTO;
import com.vsu.skibin.coursework.app.api.data.request.article.AddArticleRequest;
import com.vsu.skibin.coursework.app.api.data.request.article.GetSubscribedArticleRequest;
import com.vsu.skibin.coursework.app.api.data.request.article.PatchArticleRequest;
import com.vsu.skibin.coursework.app.exception.exception.article.NotAuthorException;
import com.vsu.skibin.coursework.app.exception.exception.article.UnknownArticleException;
import com.vsu.skibin.coursework.app.service.ArticleService;
import com.vsu.skibin.coursework.app.service.TagService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/article")
public class ArticleController {
    private final ArticleService articleService;
    private final TagService tagService;

    @Autowired
    public ArticleController(ArticleService articleService, TagService tagService) {
        this.articleService = articleService;
        this.tagService = tagService;
    }

    @GetMapping("/")
    public Collection<ArticleDTO> getAllArticlesWithPagination(@RequestParam Integer limit, @RequestParam Integer offset){
        return articleService.getAllArticlesWithPagination(limit, offset);
    }

    @GetMapping()
    public Collection<ArticleDTO> getSubscriptionsArticlesWithPagination(@RequestBody GetSubscribedArticleRequest request,
                                                                         @RequestParam Integer limit,
                                                                         @RequestParam Integer offset){
        return articleService.getSubscriptionsArticlesWithPagination(request, limit, offset);
    }

    @GetMapping("/tag/{tagId}")
    public Collection<ArticleDTO> getArticlesByTagId(@PathVariable("tagId") Long id){
        return articleService.getArticlesByTagId(id);
    }

    @GetMapping("/tag/{tagName}")
    public Collection<ArticleDTO> getArticlesByTagName(@PathVariable("tagName") String name){
        return articleService.getArticlesByTagTitle(name);
    }

    @GetMapping("/{id}")
    public ArticleDTO getArticle(@PathVariable Long id) {
        ArticleDTO articleDTO = articleService.getArticle(id);
        if (articleDTO == null){
            throw new UnknownArticleException("Unknown article");
        }
        return articleDTO;
    }

    @GetMapping("/{id}/tag")
    public Collection<TagDTO> getTagsFromArticle(@PathVariable("id") Long articleId){
        return tagService.getTagFromArticle(articleId);
    }

    @PostMapping("/{id}")
    public int incReadCount(@PathVariable Long id) {
        return articleService.incReadCount(id);
    }

    @PostMapping("/")
    public int addArticle(@RequestParam Long authorId, @Valid @RequestBody AddArticleRequest articleRequest) throws NotAuthorException {
        return articleService.addArticle(authorId,
                articleRequest.getTitle(),
                articleRequest.getDate(),
                articleRequest.getContent());
    }

    @PatchMapping("/{id}")
    public int editArticle(@PathVariable Long id, @Valid @RequestBody PatchArticleRequest patchArticle) {
        return articleService.updateArticle(id, patchArticle.getTittle(), patchArticle.getContent());
    }

    @DeleteMapping("/{id}")
    public int deleteArticle(@PathVariable Long id) {
        return articleService.deleteArticle(id);
    }
}
