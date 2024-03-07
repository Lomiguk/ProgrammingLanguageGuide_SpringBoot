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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    private final ProfileService profileService;
    private final PasswordUtil passwordUtil;

    @Autowired
    public ProfileController(ProfileService profileService, PasswordUtil passwordUtil) {
        this.profileService = profileService;
        this.passwordUtil = passwordUtil;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDTO> profiling(@PathVariable("id") Long profileId) throws ReturnUnknownProfileException {
        return new ResponseEntity<>(profileService.getProfile(profileId), HttpStatus.OK);
    }

    @GetMapping("/{id}/subscription")
    public ResponseEntity<Collection<ProfileDTO>> getSubscription(@PathVariable("id") Long profileId) {
        return new ResponseEntity<>(profileService.getSubscribes(profileId), HttpStatus.OK);
    }

    @PostMapping("/sign_in")
    public ResponseEntity<ProfileDTO> signIn(@Valid @RequestBody SignInRequest request) {
        return new ResponseEntity<>(
                profileService.signIn(
                        request.getLogin(),
                        passwordUtil.getHash(request.getPassword())
                ),
                HttpStatus.ACCEPTED
        );
    }

    @PostMapping("/sign_up")
    public ResponseEntity<ProfileDTO> signUp(@Valid @RequestBody SignUpRequest request) throws SignUpException, CouldNotFoundCreatedProfileException {
        return new ResponseEntity<>(
                profileService.signUp(
                        request.getLogin(),
                        request.getEmail(),
                        request.getPassword(),
                        request.getPasswordRepeat()
                ),
                HttpStatus.CREATED
        );
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<Object> changePassword(@PathVariable("id") Long profileId,
                                                 @Valid @RequestBody DoublePasswordRequest passwordRequest) throws WrongOldPasswordException {
        profileService.changePassword(profileId, passwordRequest.getOldPassword(), passwordRequest.getNewPassword());
        return ResponseEntity.ok().body(Boolean.TRUE);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ProfileDTO> updateProfile(@PathVariable("id") Long profileId,
                                                    @Valid @RequestBody UpdateProfileRequest request) {
        return new ResponseEntity<>(profileService.updateProfile(profileId, request), HttpStatus.OK);
    }

    @PatchMapping("/{id}/author")
    public ResponseEntity<Boolean> becomeAnAuthor(@PathVariable("id") Long profileId) {
        return new ResponseEntity<>(profileService.becomeAnAuthor(profileId), HttpStatus.OK);
    }

    @PostMapping("/{id}/subscription")
    public ResponseEntity<Boolean> subscribe(@PathVariable("id") Long profileId,
                                             @RequestParam("author") String authorLogin) throws WrongProfileIdException, SubscribeOnNonExistentProfile {
        return new ResponseEntity<>(profileService.subscribeToProfile(profileId, authorLogin), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/subscription")
    public ResponseEntity<Object> unsubscribe(@PathVariable("id") Long profileId,
                                              @RequestParam("author") String authorLogin) throws WrongProfileIdException, SubscribeOnNonExistentProfile {
        profileService.unsubscribeFromProfile(profileId, authorLogin);
        return ResponseEntity.ok().body(Boolean.TRUE);
    }
}
