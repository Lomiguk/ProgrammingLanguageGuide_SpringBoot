package com.vsu.skibin.coursework.app.service;

import com.vsu.skibin.coursework.app.api.data.dto.ArticleDTO;
import com.vsu.skibin.coursework.app.api.data.request.article.GetSubscribedArticleRequest;
import com.vsu.skibin.coursework.app.entity.Article;
import com.vsu.skibin.coursework.app.repository.dao.ArticleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class ArticleService {
    private final ArticleDAO articleDAO;

    @Autowired
    public ArticleService(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    public ArticleDTO getArticle(Long id) {
        return new ArticleDTO(articleDAO.getArticle(id));
    }

    public int addArticle(Long authorId, String title, Timestamp date, String content) {
        return articleDAO.addArticle(authorId, title, date, content);
    }

    public int updateArticle(Long id, String tittle, String content) {
        return articleDAO.updateArticle(id, tittle, content);
    }

    public int deleteArticle(Long id) {
        return articleDAO.deleteArticle(id);
    }

    public int incReadCount(Long id) {
        return articleDAO.incReadCount(id);
    }

    public Collection<ArticleDTO> getAllArticlesWithPagination(Integer limit, Integer offset) {
        return transformCollectionEntityToDTO(articleDAO.getAllArticlesWithPagination(limit, offset));
    }

    public Collection<ArticleDTO> getSubscribedArticlesWithPagination(GetSubscribedArticleRequest request,
                                                                      Integer limit,
                                                                      Integer offset) {
        return transformCollectionEntityToDTO(articleDAO.getSubscribedArticlesWithPagination(request.getSubscriberId(),
                limit,
                offset));
    }

    private Collection<ArticleDTO> transformCollectionEntityToDTO(Collection<Article> articles) {
        Collection<ArticleDTO> resultDTO = new ArrayList<>();
        for (Article article : articles) {
            resultDTO.add(new ArticleDTO(article));
        }
        return resultDTO;
    }
}
