package com.vsu.skibin.coursework.app.service;

import com.vsu.skibin.coursework.app.api.data.dto.TagDTO;
import com.vsu.skibin.coursework.app.api.data.request.tag.AddTagRequest;
import com.vsu.skibin.coursework.app.api.data.request.tag.UpdateTagRequest;
import com.vsu.skibin.coursework.app.entity.Tag;
import com.vsu.skibin.coursework.app.exception.exception.global.WrongIdValueException;
import com.vsu.skibin.coursework.app.exception.exception.tag.CouldNotFoundTagException;
import com.vsu.skibin.coursework.app.exception.exception.tag.FindTheTagException;
import com.vsu.skibin.coursework.app.repository.dao.TagDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class TagService {
    private final TagDAO tagDAO;

    @Autowired
    public TagService(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }

    public TagDTO getTagInfo(Long id) throws FindTheTagException, WrongIdValueException {
        if (id < 0) {
            throw new WrongIdValueException("Wrong tag id!");
        }
        Tag tag = tagDAO.getTagInfo(id);
        if (tag == null) {
            throw new FindTheTagException("Couldn't found a tag with this id");
        }
        return new TagDTO(tag);
    }

    @Transactional
    public TagDTO addTag(AddTagRequest request) throws CouldNotFoundTagException {
        tagDAO.addTag(request);
        Tag tag = tagDAO.getTagInfo(request.getTitle());
        if (tag == null) {
            throw new CouldNotFoundTagException("Could not found created tag");
        }
        return new TagDTO(tag);
    }

    @Transactional
    public TagDTO updateTag(Long id, UpdateTagRequest request) throws CouldNotFoundTagException {
        tagDAO.updateTag(id, request);
        Tag tag = tagDAO.getTagInfo(id);
        if (tag == null) {
            throw new CouldNotFoundTagException("Couldn't found tag after update");
        }
        return new TagDTO(tag);
    }

    public int deleteTag(Long id) {
        return tagDAO.deleteTag(id);
    }

    @Transactional
    public Boolean addTagToTheArticle(Long articleId, Long id) {
        tagDAO.addTagToTheArticle(articleId, id);
        return tagDAO.checkTagOnArticle(articleId, id);
    }

    @Transactional
    public Boolean removeTagFromTheArticle(Long articleId, Long id) {
        tagDAO.removeTagFromTheArticle(articleId, id);
        return !tagDAO.checkTagOnArticle(articleId, id);
    }

    public Collection<TagDTO> getTagFromArticle(Long articleId) {
        return transformCollectionTagEntityToDTO(tagDAO.getTagFromArticle(articleId));
    }

    public Collection<TagDTO> getAllTagsWithPagination(Integer limit, Integer offset) {
        return transformCollectionTagEntityToDTO(tagDAO.getAllTagsWithPagination(limit, offset));
    }

    private Collection<TagDTO> transformCollectionTagEntityToDTO(Collection<Tag> tags) {
        Collection<TagDTO> tagDTOs = new ArrayList<>();
        for (Tag tag : tags) {
            tagDTOs.add(new TagDTO(tag));
        }
        return tagDTOs;
    }
}
