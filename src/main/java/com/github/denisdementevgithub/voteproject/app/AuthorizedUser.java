package com.github.denisdementevgithub.voteproject.app;

import com.github.denisdementevgithub.voteproject.user.model.User;
import com.github.denisdementevgithub.voteproject.user.to.UserTo;
import com.github.denisdementevgithub.voteproject.user.util.UsersUtil;
import lombok.Getter;

import java.io.Serial;

@Getter
public class AuthorizedUser extends org.springframework.security.core.userdetails.User {
    @Serial
    private static final long serialVersionUID = 1L;

    private UserTo userTo;

    public AuthorizedUser(User user) {
        super(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, user.getRoles());
        setTo(UsersUtil.asTo(user));
    }

    public void setTo(UserTo newTo) {
        newTo.setPassword(null);
        userTo = newTo;
    }

    @Override
    public String toString() {
        return userTo.toString();
    }
}