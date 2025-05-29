package com.QuantomSoft.Controller;

import com.QuantomSoft.Entity.Admin;

import com.QuantomSoft.Entity.News;

import com.QuantomSoft.Exception.AdminNotFoundException;

import com.QuantomSoft.Exception.NewsNotFound;

import com.QuantomSoft.Repository.AdminRepository;

import com.QuantomSoft.Service.NewsService;

import com.QuantomSoft.ServiceImpl.NewsServiceImpl;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.nio.file.Files;

import java.nio.file.Path;

import java.nio.file.Paths;

import java.util.*;

@RestController

@RequestMapping("/api/News")

@CrossOrigin("*")

public class NewsController {

    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Value("${news.image.upload-dir}")

    private String uploadDir;


    @Autowired

    private NewsService newsService;

    @Autowired

    private NewsServiceImpl newsServiceImpl;

    @Autowired

    private AdminRepository adminRepository;

    // 🔵 Upload Image Separately and Return Filename

    @PostMapping("/uploadImage")

    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {

        try {

            String fileName = newsServiceImpl.uploadImage(file);

            return ResponseEntity.ok(fileName);

        } catch (IOException e) {

            logger.error("Image upload failed: {}", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image upload failed.");

        }

    }

    @PostMapping("/SaveNews")

    public ResponseEntity<Map<String, Object>> saveNews(

            @RequestParam String newsTitle,

            @RequestParam String newsContent,

            @RequestParam Long adminId,

            @RequestParam("image") MultipartFile image

    ) {

        try {

            Admin admin = adminRepository.findById(adminId)

                    .orElseThrow(() -> new AdminNotFoundException("Admin not found with ID: " + adminId));

            // Save Image to Folder

            String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();

            Path imagePath = Paths.get(uploadDir, fileName);

            Files.createDirectories(imagePath.getParent());

            Files.write(imagePath, image.getBytes());

            // Save News with imageUrl

            News news = new News();

            news.setNewsTitle(newsTitle);

            news.setNewsContent(newsContent);

            news.setAdmin(admin);

            news.setImageUrl(fileName);

            News savedNews = newsService.saveNews(news);

            Map<String, Object> response = new HashMap<>();

            response.put("message", "News created successfully");

            response.put("newsId", savedNews.getId());

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            logger.error("Error saving news: {}", e.getMessage(), e);

            Map<String, Object> errorResponse = new HashMap<>();

            errorResponse.put("message", "Failed to create news");

            errorResponse.put("error", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);

        }

    }


    @GetMapping("/getNewsById/{id}")

    public ResponseEntity<News> getNewsById(@PathVariable Long id) {

        try {

            logger.info("Request for getting News with id {}", id);

            News news = newsService.getNewsByNewsId(id);

            return ResponseEntity.ok(news);

        } catch (NewsNotFound e) {

            logger.error("News not found with id {}: {}", id, e.getMessage(), e);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }

    }

    @GetMapping("/allnews")

    public ResponseEntity<List<News>> getAllNews() {

        try {

            logger.info("Fetching all news");

            List<News> allNews = newsService.getAllNews();

            return new ResponseEntity<>(allNews, HttpStatus.OK);

        } catch (NewsNotFound e) {

            logger.info("Error while fetching all news", e.getMessage());

            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        }

    }

    @GetMapping("/getNewsImage/{id}")

    public ResponseEntity<byte[]> getNewsImage(@PathVariable Long id) {

        try {

            News news = newsService.getNewsByNewsId(id);

            if (news.getImageUrl() != null) {

                Path imagePath = Paths.get(uploadDir, news.getImageUrl());

                byte[] imageBytes = Files.readAllBytes(imagePath);

                return ResponseEntity.ok()

                        .contentType(MediaType.IMAGE_JPEG) // or MediaType.IMAGE_PNG

                        .body(imageBytes);

            } else {

                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

            }

        } catch (Exception e) {

            logger.error("Error fetching image for news with id {}: {}", id, e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }

    }


    @PutMapping("/update/{newsId}")

    public ResponseEntity<News> updateNews(@PathVariable Long newsId, @RequestBody News news) {

        try {

            News updatedNews = newsService.updateNewsByNewsId(newsId, news);

            return new ResponseEntity<>(updatedNews, HttpStatus.OK);

        } catch (NewsNotFound e) {

            logger.error("Error while updating news for adminId {}: {}", newsId, e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        } catch (Exception e) {

            logger.error("Error while updating news {}", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        }

    }

    @DeleteMapping("/delete/{newsId}")

    public ResponseEntity<Void> deleteNews(@PathVariable Long newsId) {

        try {

            newsService.deleteNewsByNewsId(newsId);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        } catch (NewsNotFound e) {

            logger.error("Error while deleting news for adminId {}: {}", newsId, e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        } catch (Exception e) {

            logger.error("Error while deleting news {}", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }

    }

}

