package com.vsu.skibin.coursework.app.tool;

import com.google.common.hash.Hashing;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class PasswordUtil {
    public Long getHash(String origin) {
        return Hashing.sha256()
                .hashString(origin, StandardCharsets.UTF_8)
                .padToLong();
    }
}
