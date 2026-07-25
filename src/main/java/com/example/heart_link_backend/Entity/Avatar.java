package com.example.heart_link_backend.Entity;

import jakarta.persistence.*;

@Entity
@Table(name="avatars")
public class Avatar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(name="image_url")
    private String imageUrl;

    @Column(name="tags")
    private String tags;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
