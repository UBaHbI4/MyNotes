package softing.ubah4ukdev.mynotes.domain;

import java.io.Serializable;

import softing.ubah4ukdev.mynotes.ui.profile.AuthType;

/****
 Project MyNotes
 Package softing.ubah4ukdev.mynotes.domain

 Created by Ivan Sheynmaer

 2021.03.31
 v1.0
 */
public class AccountInfo implements Serializable {

    public static final AccountInfo INSTANCE = new AccountInfo();

    private String name;
    private String mail;
    private String imageURL;
    private AuthType authType;

    public AccountInfo() {
    }

    public void clear() {
        name = null;
        mail = null;
        imageURL = null;
    }

    public boolean isEmpty() {
        return name == null && mail == null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public AuthType getAuthType() {
        return authType;
    }

    public void setAuthType(AuthType authType) {
        this.authType = authType;
    }
}
