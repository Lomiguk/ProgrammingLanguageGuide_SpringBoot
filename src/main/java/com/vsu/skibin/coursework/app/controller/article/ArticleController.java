package com.vsu.skibin.coursework.app.controller.article;

import com.vsu.skibin.coursework.app.api.data.dto.ArticleDTO;
import com.vsu.skibin.coursework.app.api.data.dto.TagDTO;
import com.vsu.skibin.coursework.app.api.data.request.article.AddArticleRequest;
import com.vsu.skibin.coursework.app.api.data.request.article.GetSubscribedArticleRequest;
import com.vsu.skibin.coursework.app.api.data.request.article.PatchArticleRequest;
import com.vsu.skibin.coursework.app.exception.exception.article.CouldNotFoundArticleException;
import com.vsu.skibin.coursework.app.exception.exception.article.NotAuthorException;
import com.vsu.skibin.coursework.app.exception.exception.article.UnknownArticleException;
import com.vsu.skibin.coursework.app.exception.exception.global.WrongIdValueException;
import com.vsu.skibin.coursework.app.service.ArticleService;
import com.vsu.skibin.coursework.app.service.TagService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Collection<ArticleDTO>> getAllArticlesWithPagination(@RequestParam Integer limit, @RequestParam Integer offset) {
        return new ResponseEntity<>(articleService.getAllArticlesWithPagination(limit, offset), HttpStatus.FOUND);
    }

    @GetMapping()
    public ResponseEntity<Collection<ArticleDTO>> getSubscriptionsArticlesWithPagination(@Valid @RequestBody GetSubscribedArticleRequest request,
                                                                                         @RequestParam Integer limit,
                                                                                         @RequestParam Integer offset) {
        return new ResponseEntity<>(articleService.getSubscriptionsArticlesWithPagination(request, limit, offset), HttpStatus.FOUND);
    }

    @GetMapping("/tag/{tagId}")
    public ResponseEntity<Collection<ArticleDTO>> getArticlesByTagId(@PathVariable("tagId") Long id) {
        return new ResponseEntity<>(articleService.getArticlesByTagId(id), HttpStatus.FOUND);
    }

    @GetMapping("/tag/{tagName}")
    public ResponseEntity<Collection<ArticleDTO>> getArticlesByTagName(@PathVariable("tagName") String name) {
        return new ResponseEntity<>(articleService.getArticlesByTagTitle(name), HttpStatus.FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO> getArticle(@PathVariable Long id) throws WrongIdValueException {
        ArticleDTO articleDTO = articleService.getArticle(id);
        if (articleDTO == null) {
            throw new UnknownArticleException("Unknown article");
        }
        return new ResponseEntity<>(articleDTO, HttpStatus.FOUND);
    }

    @GetMapping("/{id}/tag")
    public ResponseEntity<Collection<TagDTO>> getTagsFromArticle(@PathVariable("id") Long articleId) {
        return new ResponseEntity<>(tagService.getTagFromArticle(articleId), HttpStatus.FOUND);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Integer> incReadCount(@PathVariable Long id) {
        return new ResponseEntity<>(articleService.incReadCount(id), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<ArticleDTO> addArticle(@RequestParam Long authorId, @Valid @RequestBody AddArticleRequest articleRequest) throws NotAuthorException, CouldNotFoundArticleException {
        return new ResponseEntity<>(articleService.addArticle(authorId,
                articleRequest.getTitle(),
                articleRequest.getDate(),
                articleRequest.getContent()), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ArticleDTO> editArticle(@PathVariable Long id, @Valid @RequestBody PatchArticleRequest patchArticle) throws CouldNotFoundArticleException {
        return new ResponseEntity<>(articleService.updateArticle(id, patchArticle.getTittle(), patchArticle.getContent()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteArticle(@PathVariable Long id) {
        return new ResponseEntity<>(articleService.deleteArticle(id), HttpStatus.OK);
    }
}
