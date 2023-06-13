package com.vsu.skibin.coursework.app.api.data.request.tag;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateTagRequest {
    @Size(max = 10, message = "max title size 10")
    @NotBlank(message = "title can't be blank")
    private String title;
}
