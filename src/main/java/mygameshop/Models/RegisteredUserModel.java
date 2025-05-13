package mygameshop.Models;

import mygameshop.interfaces.RegisteredUser;
import org.springframework.web.bind.annotation.ModelAttribute;

public final class RegisteredUserModel implements RegisteredUser {
    private int id;
    private boolean banned;
    private boolean isAdmin;
    private String loginname;
    private String passhash;

    @ModelAttribute("registeredUser")
    public RegisteredUserModel initRegisteredUser() {
        return new RegisteredUserModel();
    }

    @Override
    public String loginname() {
        return loginname;
    }

    @Override
    public String passhash() {
        return passhash;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(final boolean banned) {
        this.banned = banned;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(final boolean admin) {
        isAdmin = admin;
    }

    public void setLoginname(final String loginname) {
        this.loginname = loginname;
    }

    public void setPasshash(final String passhash) {
        this.passhash = passhash;
    }
}
