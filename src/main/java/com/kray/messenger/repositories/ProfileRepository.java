package com.kray.messenger.repositories;

import com.kray.messenger.model.Profile;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public abstract class ProfileRepository {

    private static Map<Long, Profile> profiles = new HashMap<>();

    public static Map<Long, Profile> getProfiles() {
        return profiles;
    }

}
