package com.vsu.skibin.coursework.app.api.data.request.tag;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddTagRequest {
    @Size(max = 10)
    @NotBlank
    private String title;
}
