package com.vsu.skibin.coursework.app.service;

import com.vsu.skibin.coursework.app.api.data.dto.TagDTO;
import com.vsu.skibin.coursework.app.api.data.request.tag.AddTagRequest;
import com.vsu.skibin.coursework.app.api.data.request.tag.UpdateTagRequest;
import com.vsu.skibin.coursework.app.entity.Tag;
import com.vsu.skibin.coursework.app.repository.dao.TagDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class TagService {
    private final TagDAO tagDAO;
    @Autowired
    public TagService(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }
    public TagDTO getTagInfo(Long id) {
        return new TagDTO(tagDAO.getTagInfo(id));
    }

    public int addTag(AddTagRequest request) {
        return tagDAO.addTag(request);
    }

    public int updateTag(Long id, UpdateTagRequest request) {
        return tagDAO.updateTag(id, request);
    }

    public int deleteTag(Long id) {
        return tagDAO.deleteTag(id);
    }

    public int addTagToTheArticle(Long articleId, Long id) {
        return tagDAO.addTagToTheArticle(articleId, id);
    }

    public int removeTagFromTheArticle(Long articleId, Long id) {
        return tagDAO.removeTagFromTheArticle(articleId, id);
    }

    public Collection<TagDTO> getTagFromArticle(Long articleId) {
        Collection<Tag> tags = tagDAO.getTagFromArticle(articleId);
        Collection<TagDTO> tagDTOs = new ArrayList<>();
        for (Tag tag: tags) {
            tagDTOs.add(new TagDTO(tag));
        }
        return tagDTOs;
    }
}
