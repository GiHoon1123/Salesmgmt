package io.dustin.finstage.user.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {
    private final String displayName;
    private final String email;
    private final String departmentName;

    public  static User of(String displayName, String email, String departmentName) {
        return new User(displayName,email,departmentName);
    }


}
