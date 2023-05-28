package com.vsu.skibin.coursework.app.service;

import com.vsu.skibin.coursework.app.api.data.dto.ProfileDTO;
import com.vsu.skibin.coursework.app.api.data.request.profile.UpdateProfileRequest;
import com.vsu.skibin.coursework.app.entity.Profile;
import com.vsu.skibin.coursework.app.exception.profile.SignUpException;
import com.vsu.skibin.coursework.app.exception.profile.SubscribeOnNonExistentProfile;
import com.vsu.skibin.coursework.app.exception.profile.WrongOldPasswordException;
import com.vsu.skibin.coursework.app.exception.profile.WrongProfileId;
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

    @Autowired
    public ProfileService(ProfileDAO profileDAO) {
        this.profileDAO = profileDAO;
    }

    public ProfileDTO signIn(String login, Long password) {
        return new ProfileDTO(profileDAO.signIn(login, password));
    }

    public void signUp(String login, String email, String password, String passwordRepeat) {
        if (password.equals(passwordRepeat)) {
            profileDAO.signUp(login, email, PasswordUtil.getHash(password));
        } else {
            throw new SignUpException("Passwords don't match");
        }
    }

    public Collection<ProfileDTO> getSubscribes(Long id) {
        Collection<Profile> profiles = profileDAO.getSubscribes(id);
        Collection<ProfileDTO> profileDTOs = new ArrayList<>();
        for (Profile profile: profiles) {
            profileDTOs.add(new ProfileDTO(profile));
        }
        return profileDTOs;
    }

    public ProfileDTO getProfile(Long profileId) {
        return new ProfileDTO(profileDAO.getProfile(profileId));
    }

    @Transactional
    public void changePassword(Long profileId, String oldPassword, String newPassword) {
        if (profileDAO.checkPassword(profileId, PasswordUtil.getHash(oldPassword))) {
            profileDAO.changePassword(profileId, PasswordUtil.getHash(newPassword));
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
    public void subscribeToProfile(Long subscriberId, String authorLogin) {
        try {
            Long authorId = profileDAO.getProfileId(authorLogin);
            profileDAO.subscribe(subscriberId, authorId);
        } catch (EmptyResultDataAccessException e) {
            throw new SubscribeOnNonExistentProfile("Trying to following a nun-existing profile");
        } catch (DataIntegrityViolationException e) {
            throw new WrongProfileId("Wrong Subscriber id");
        }
    }

    @Transactional
    public void unsubscribeFromProfile(Long profileId, String authorLogin) {
        try {
            Long authorId = profileDAO.getProfileId(authorLogin);
            profileDAO.unsubscribe(profileId, authorId);
        } catch (EmptyResultDataAccessException e) {
            throw new SubscribeOnNonExistentProfile("Trying to unfollowing a nun-existing profile");
        } catch (DataIntegrityViolationException e) {
            throw new WrongProfileId("Wrong Unsubscriber id");
        }
    }

    public int updateProfile(Long profileId, UpdateProfileRequest request) {
        return profileDAO.updateProfile(profileId, request);
    }
}
