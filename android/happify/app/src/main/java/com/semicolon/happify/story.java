package com.semicolon.happify;

public class story {
    private String authorFirstName;
    private String authorLastName;
    private String content;
    private String title;
    private String createdAt;


    public story(String authorFirstName, String authorLastName, String content, String title) {
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
        this.content = content;
        this.title = title;

    }



    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }



    public void setTitle(String title) {

        this.title = title;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
