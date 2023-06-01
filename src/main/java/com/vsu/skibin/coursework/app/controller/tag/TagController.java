package com.vsu.skibin.coursework.app.controller.tag;

import com.vsu.skibin.coursework.app.api.data.dto.TagDTO;
import com.vsu.skibin.coursework.app.api.data.request.tag.AddTagRequest;
import com.vsu.skibin.coursework.app.api.data.request.tag.UpdateTagRequest;
import com.vsu.skibin.coursework.app.service.TagService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tag")
public class TagController {
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/{id}")
    public TagDTO getTagInfo(@PathVariable Long id) {
        return tagService.getTagInfo(id);
    }

    @PostMapping(value = {"", "/"})
    public int addTag(@RequestBody AddTagRequest request) {
        return tagService.addTag(request);
    }

    @PostMapping("/{id}")
    public int addTagToTheArticle(@PathVariable Long id, @RequestParam("articleId") Long articleId){
        return tagService.addTagToTheArticle(articleId, id);
    }
    @DeleteMapping("/{id}/article/{articleId}")
    public int removeTagFromTheArticle(@PathVariable("id") Long id, @PathVariable("articleId") Long articleId){
        return tagService.removeTagFromTheArticle(articleId, id);
    }

    @PutMapping("/{id}")
    public int updateTag(@PathVariable("id") Long id, @Valid @RequestBody UpdateTagRequest request){
        return tagService.updateTag(id, request);
    }

    @DeleteMapping("/{id}")
    public int deleteTag(@PathVariable("id") Long id){
        return tagService.deleteTag(id);
    }
}
