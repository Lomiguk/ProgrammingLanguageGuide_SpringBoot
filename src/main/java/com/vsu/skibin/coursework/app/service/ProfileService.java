package com.vsu.skibin.coursework.app.service;

import com.vsu.skibin.coursework.app.api.data.dto.ProfileDTO;
import com.vsu.skibin.coursework.app.api.data.request.profile.UpdateProfileRequest;
import com.vsu.skibin.coursework.app.entity.Profile;
import com.vsu.skibin.coursework.app.exception.exception.profile.*;
import com.vsu.skibin.coursework.app.repository.dao.ProfileDAO;
import com.vsu.skibin.coursework.app.tool.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class ProfileService {
    private final ProfileDAO profileDAO;
    private final PasswordUtil passwordUtil;

    @Autowired
    public ProfileService(ProfileDAO profileDAO, PasswordUtil passwordUtil) {
        this.profileDAO = profileDAO;
        this.passwordUtil = passwordUtil;
    }

    public ProfileDTO signIn(String login, Long password) {
        return new ProfileDTO(profileDAO.signIn(login, password));
    }

    @Transactional
    public ProfileDTO signUp(String login, String email, String password, String passwordRepeat) throws SignUpException, CouldNotFoundCreatedProfileException {
        if (password.equals(passwordRepeat)) {
            profileDAO.signUp(login, email, passwordUtil.getHash(password));
        } else {
            throw new SignUpException("Passwords don't match");
        }
        // --
        Profile createdProfile = profileDAO.getProfile(login);
        if (createdProfile == null) {
            throw new CouldNotFoundCreatedProfileException("Couldn't found created profile!");
        }
        return new ProfileDTO(createdProfile);
    }

    public Collection<ProfileDTO> getSubscribes(Long id) {
        Collection<Profile> profiles = profileDAO.getSubscribes(id);
        Collection<ProfileDTO> profileDTOs = new ArrayList<>();
        for (Profile profile : profiles) {
            profileDTOs.add(new ProfileDTO(profile));
        }
        return profileDTOs;
    }

    public ProfileDTO getProfile(Long profileId) throws ReturnUnknownProfileException {
        try {
            return new ProfileDTO(profileDAO.getProfile(profileId));
        } catch (EmptyResultDataAccessException e) {
            throw new ReturnUnknownProfileException("Unknown profile");
        }
    }

    @Transactional
    public void changePassword(Long profileId, String oldPassword, String newPassword) throws WrongOldPasswordException {
        if (profileDAO.checkPassword(profileId, passwordUtil.getHash(oldPassword))) {
            profileDAO.changePassword(profileId, passwordUtil.getHash(newPassword));
        } else {
            throw new WrongOldPasswordException("The \"old password\" is not up-to-date");
        }
    }

    @Transactional
    public boolean becomeAnAuthor(Long profileId) {
        profileDAO.becomeAnAuthor(profileId);
        return profileDAO.isAuthor(profileId);
    }

    @Transactional
    public Boolean subscribeToProfile(Long subscriberId, String authorLogin) throws SubscribeOnNonExistentProfile, WrongProfileIdException {
        try {
            Long authorId = profileDAO.getProfileId(authorLogin);
            profileDAO.subscribe(subscriberId, authorId);
            return profileDAO.checkSubscribe(subscriberId, authorId);
        } catch (EmptyResultDataAccessException e) {
            throw new SubscribeOnNonExistentProfile("Trying to following a nun-existing profile");
        } catch (DataIntegrityViolationException e) {
            throw new WrongProfileIdException("Wrong Subscriber id");
        }
    }

    @Transactional
    public void unsubscribeFromProfile(Long profileId, String authorLogin) throws SubscribeOnNonExistentProfile, WrongProfileIdException {
        try {
            Long authorId = profileDAO.getProfileId(authorLogin);
            profileDAO.unsubscribe(profileId, authorId);
        } catch (EmptyResultDataAccessException e) {
            throw new SubscribeOnNonExistentProfile("Trying to unfollowing a nun-existing profile");
        } catch (DataIntegrityViolationException e) {
            throw new WrongProfileIdException("Wrong Unsubscriber id");
        }
    }

    @Transactional
    public ProfileDTO updateProfile(Long profileId, UpdateProfileRequest request) {
        profileDAO.updateProfile(profileId, request);
        return new ProfileDTO(profileDAO.getProfile(profileId));
    }
}
