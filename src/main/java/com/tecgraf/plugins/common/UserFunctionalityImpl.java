package com.tecgraf.plugins.common;

public class UserFunctionalityImpl implements UserFunctionality{
    private User user;
    private Functionality functionality;

    public UserFunctionalityImpl(User user, Functionality functionality) {
        this.user = user;
        this.functionality = functionality;
    }

    @Override
    public User getUser() {
        return user;
    }
    @Override
    public void setUser(User user) {
        this.user = user;
    }
    @Override
    public Functionality getFunctionality() {
        return functionality;
    }
    @Override
    public void setFunctionality(Functionality functionality) {
        this.functionality = functionality;
    }
}
