package mygameshop.Models;

import mygameshop.interfaces.registereduser;

public class RegisteredUserModel implements registereduser {
    public int Id;
    public boolean Banned;
    public boolean IsAdmin;
    public String loginname;
    public String passhash;

    @Override
    public String loginname() {
        return loginname;
    }

    @Override
    public String passhash() {
        return passhash;
    }
}
