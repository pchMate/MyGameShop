package mygameshop.Models;

import mygameshop.interfaces.RegisteredUser;

public final class RegisteredUserUserModel implements RegisteredUser {
    private int Id;
    private boolean Banned;
    private boolean IsAdmin;
    private String loginname;
    private String passhash;

    @Override
    public String loginname() {
        return loginname;
    }

    @Override
    public String passhash() {
        return passhash;
    }

    public int getId() {
        return Id;
    }

    public void setId(final int id) {
        Id = id;
    }

    public boolean isBanned() {
        return Banned;
    }

    public void setBanned(final boolean banned) {
        Banned = banned;
    }

    public boolean isAdmin() {
        return IsAdmin;
    }

    public void setAdmin(final boolean admin) {
        IsAdmin = admin;
    }

    public void setLoginname(final String loginname) {
        this.loginname = loginname;
    }

    public void setPasshash(final String passhash) {
        this.passhash = passhash;
    }
}
