package com.testv2;

/**
 * Created by steve on 7/20/17.
 */

public class RepoModel {
    String name, fullName, description, updatedAt, language, stargazersCount, avatarUrl;

    public void setName(String name) {
        this.name = name;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setStargazersCount(String stargazersCount) {
        this.stargazersCount = stargazersCount;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getStargazersCount() {
        return stargazersCount;
    }

    public String getDescription() {
        return description;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getLanguage() {
        return language;
    }
}
