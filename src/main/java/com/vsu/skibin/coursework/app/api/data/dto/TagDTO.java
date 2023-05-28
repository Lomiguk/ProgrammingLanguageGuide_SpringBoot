package com.vsu.skibin.coursework.app.api.data.dto;

import com.vsu.skibin.coursework.app.entity.Tag;
import lombok.Data;

@Data
public class TagDTO {
    private String title;

    public TagDTO(Tag tag) {
        this.title = tag.getTitle();
    }
}
