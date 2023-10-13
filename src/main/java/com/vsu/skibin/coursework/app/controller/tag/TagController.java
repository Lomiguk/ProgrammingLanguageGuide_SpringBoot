package com.vsu.skibin.coursework.app.controller.tag;

import com.vsu.skibin.coursework.app.api.data.dto.TagDTO;
import com.vsu.skibin.coursework.app.api.data.request.tag.AddTagRequest;
import com.vsu.skibin.coursework.app.api.data.request.tag.UpdateTagRequest;
import com.vsu.skibin.coursework.app.exception.exception.global.WrongIdValueException;
import com.vsu.skibin.coursework.app.exception.exception.tag.CouldNotFoundTagException;
import com.vsu.skibin.coursework.app.exception.exception.tag.FindTheTagException;
import com.vsu.skibin.coursework.app.service.TagService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/tag")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping(value = {"", "/"})
    public ResponseEntity<Collection<TagDTO>> getAllTagsWithPagination(@RequestParam("limit") Integer limit,
                                                                       @RequestParam("offset") Integer offset) {
        return new ResponseEntity<>(tagService.getAllTagsWithPagination(limit, offset), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDTO> getTagInfo(@PathVariable Long id) throws FindTheTagException, WrongIdValueException {
        return new ResponseEntity<>(tagService.getTagInfo(id), HttpStatus.OK);
    }

    @PostMapping(value = {"", "/"})
    public ResponseEntity<TagDTO> addTag(@Valid @RequestBody AddTagRequest request) throws CouldNotFoundTagException {
        return new ResponseEntity<>(tagService.addTag(request), HttpStatus.CREATED);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Boolean> addTagToTheArticle(@PathVariable Long id,
                                                      @RequestParam("articleId") Long articleId) {
        return new ResponseEntity<>(tagService.addTagToTheArticle(articleId, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/article/{articleId}")
    public ResponseEntity<Boolean> removeTagFromTheArticle(@PathVariable("id") Long id,
                                                           @PathVariable("articleId") Long articleId) {
        return new ResponseEntity<>(tagService.removeTagFromTheArticle(articleId, id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagDTO> updateTag(@PathVariable("id") Long id,
                                            @Valid @RequestBody UpdateTagRequest request) throws CouldNotFoundTagException {
        return new ResponseEntity<>(tagService.updateTag(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTag(@PathVariable("id") Long id) {
        return new ResponseEntity<>(tagService.deleteTag(id), HttpStatus.OK);
    }
}
