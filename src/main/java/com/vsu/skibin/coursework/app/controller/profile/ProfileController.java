package com.vsu.skibin.coursework.app.controller.profile;

import com.vsu.skibin.coursework.app.api.data.dto.ProfileDTO;
import com.vsu.skibin.coursework.app.api.data.request.profile.DoublePasswordRequest;
import com.vsu.skibin.coursework.app.api.data.request.profile.SignInRequest;
import com.vsu.skibin.coursework.app.api.data.request.profile.SignUpRequest;
import com.vsu.skibin.coursework.app.api.data.request.profile.UpdateProfileRequest;
import com.vsu.skibin.coursework.app.exception.exception.profile.*;
import com.vsu.skibin.coursework.app.service.ProfileService;
import com.vsu.skibin.coursework.app.tool.PasswordUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{id}")
    public ProfileDTO profiling(@PathVariable("id") Long profileId) throws ReturnUnknownProfileException {
        return profileService.getProfile(profileId);
    }

    @GetMapping("/{id}/subscription")
    public Collection<ProfileDTO> getSubscription(@PathVariable("id") Long profileId) {
        return profileService.getSubscribes(profileId);
    }

    @PostMapping("/sign_in")
    public ProfileDTO signIn(@Valid @RequestBody SignInRequest request) {
        return profileService.signIn(request.getLogin(), PasswordUtil.getHash(request.getPassword()));
    }

    @PostMapping("/sign_up")
    public void signUp(@Valid @RequestBody SignUpRequest request) throws SignUpException {
        profileService.signUp(request.getLogin(),
                request.getEmail(),
                request.getPassword(),
                request.getPasswordRepeat());
    }

    @PatchMapping("/{id}/password")
    public void changePassword(@PathVariable("id") Long profileId,
                               @Valid @RequestBody DoublePasswordRequest passwordRequest) throws WrongOldPasswordException {
        profileService.changePassword(profileId, passwordRequest.getOldPassword(), passwordRequest.getNewPassword());
    }

    @PutMapping("/{id}/update")
    public int updateProfile(@PathVariable("id") Long profileId,
                             @Valid @RequestBody UpdateProfileRequest request) {
        return profileService.updateProfile(profileId, request);
    }

    @PatchMapping("/{id}/author")
    public boolean becomeAnAuthor(@PathVariable("id") Long profileId) {
        return profileService.becomeAnAuthor(profileId);
    }

    @PostMapping("/{id}/subscription")
    public void subscribe(@PathVariable("id") Long profileId, @RequestParam("author") String authorLogin) throws WrongProfileIdException, SubscribeOnNonExistentProfile {
        profileService.subscribeToProfile(profileId, authorLogin);
    }

    @DeleteMapping("/{id}/subscription")
    public void unsubscribe(@PathVariable("id") Long profileId, @RequestParam("author") String authorLogin) throws WrongProfileIdException, SubscribeOnNonExistentProfile {
        profileService.unsubscribeFromProfile(profileId, authorLogin);
    }
}
