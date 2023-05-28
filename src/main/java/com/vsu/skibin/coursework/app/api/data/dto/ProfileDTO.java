package com.vsu.skibin.coursework.app.api.data.dto;

import com.vsu.skibin.coursework.app.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProfileDTO {
    private String email;
    private String login;
    private Boolean isAuthor;

    public ProfileDTO(Profile profile) {
        this.email = profile.getEmail();
        this.login = profile.getLogin();
        this.isAuthor = profile.getIsAuthor();
    }
}
