package com.vsu.skibin.coursework.app.service;

import com.vsu.skibin.coursework.app.api.data.dto.ArticleDTO;
import com.vsu.skibin.coursework.app.repository.dao.ArticleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

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
}
