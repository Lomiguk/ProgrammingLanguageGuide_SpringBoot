package com.vsu.skibin.coursework.app.service;

import com.vsu.skibin.coursework.app.api.data.dto.ArticleDTO;
import com.vsu.skibin.coursework.app.api.data.request.article.GetSubscribedArticleRequest;
import com.vsu.skibin.coursework.app.entity.Article;
import com.vsu.skibin.coursework.app.exception.article.NotAuthorException;
import com.vsu.skibin.coursework.app.repository.dao.ArticleDAO;
import com.vsu.skibin.coursework.app.repository.dao.ProfileDAO;
import com.vsu.skibin.coursework.app.repository.dao.TagDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class ArticleService {
    private final ArticleDAO articleDAO;
    private final TagDAO tagDAO;
    private final ProfileDAO profileDAO;

    @Autowired
    public ArticleService(ArticleDAO articleDAO, TagDAO tagDAO, ProfileDAO profileDAO) {
        this.articleDAO = articleDAO;
        this.tagDAO = tagDAO;
        this.profileDAO = profileDAO;
    }

    public ArticleDTO getArticle(Long id) {
        return new ArticleDTO(articleDAO.getArticle(id));
    }

    @Transactional
    public int addArticle(Long authorId, String title, Timestamp date, String content) throws NotAuthorException {
        if (profileDAO.isAuthor(authorId)){
            return articleDAO.addArticle(authorId, title, date, content);
        }
        throw new NotAuthorException("Пользователь не является автором");
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
        return transformCollectionEntityToDTO(articleDAO
                .getAllArticlesWithPagination(limit, offset));
    }

    public Collection<ArticleDTO> getSubscriptionsArticlesWithPagination(GetSubscribedArticleRequest request,
                                                                         Integer limit,
                                                                         Integer offset) {
        return transformCollectionEntityToDTO(articleDAO
                .getSubscriptionsArticlesWithPagination(request.getSubscriberId(), limit, offset));
    }

    private Collection<ArticleDTO> transformCollectionEntityToDTO(Collection<Article> articles) {
        Collection<ArticleDTO> resultDTO = new ArrayList<>();
        for (Article article : articles) {
            resultDTO.add(new ArticleDTO(article));
        }
        return resultDTO;
    }

    public Collection<ArticleDTO> getArticlesByTagId(Long id) {
        return transformCollectionEntityToDTO(articleDAO.getArticlesByTagId(id));
    }

    @Transactional
    public Collection<ArticleDTO> getArticlesByTagTitle(String title) {
        Long id = tagDAO.getIdByTitle(title);
        return transformCollectionEntityToDTO(articleDAO.getArticlesByTagId(id));
    }
}
