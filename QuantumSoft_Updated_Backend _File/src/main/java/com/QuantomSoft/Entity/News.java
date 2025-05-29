package com.QuantomSoft.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class News {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NotNull(message = "Title is required")
    @Size(min = 5, message = "Title should be between 5 and 100 characters")
    private String newsTitle;

    private String newsContent;

    private LocalDate publishedDate = LocalDate.now();

    @ManyToOne
    @JoinColumn(name = "adminId", nullable = true)
    @JsonIgnoreProperties
    @JsonBackReference
    private Admin admin;

    private String imageUrl;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull(message = "Title is required") @Size(min = 5, message = "Title should be between 5 and 100 characters") String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(@NotNull(message = "Title is required") @Size(min = 5, message = "Title should be between 5 and 100 characters") String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
