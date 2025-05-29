package com.QuantomSoft.ServiceImpl;

import com.QuantomSoft.Entity.Admin;
import com.QuantomSoft.Entity.News;
import com.QuantomSoft.Exception.AdminNotFoundException;
import com.QuantomSoft.Exception.NewsNotFound;
import com.QuantomSoft.Repository.AdminRepository;
import com.QuantomSoft.Repository.NewsRepository;
import com.QuantomSoft.Service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    private static final Logger logger = LoggerFactory.getLogger(NewsServiceImpl.class);

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Value("${news.image.upload-dir}")
    private String uploadDir;

    @Override
    public News saveNews(News news) {
        logger.info("Creating News with title: {}", news.getNewsTitle());

        Admin admin = adminRepository.findById(news.getAdmin().getId())
                .orElseThrow(() -> new AdminNotFoundException("Admin with Id " + news.getAdmin().getId() + " is not available"));

        return newsRepository.save(news);
    }

    public News saveNewsWithImage(News news, MultipartFile imageFile) {
        logger.info("Creating News with image for title: {}", news.getNewsTitle());

        Admin admin = adminRepository.findById(news.getAdmin().getId())
                .orElseThrow(() -> new AdminNotFoundException("Admin with Id " + news.getAdmin().getId() + " is not available"));

        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                byte[] imageBytes = imageFile.getBytes();
                news.setImageUrl(Arrays.toString(imageBytes));
            }
            return newsRepository.save(news);
        } catch (IOException e) {
            logger.error("Image upload failed", e);
            throw new RuntimeException("Failed to upload image", e);
        }
    }

    @Override
    public News getNewsByNewsId(Long id) {
        logger.info("Fetching News by news id {}", id);
        return newsRepository.findById(id)
                .orElseThrow(() -> new NewsNotFound("News not found with id " + id));
    }

    @Override
    public News getNewsByAdminId(Long adminId) {
        logger.info("Fetching news by adminId {}", adminId);
        return newsRepository.findByAdminId(adminId)
                .orElseThrow(() -> new NewsNotFound("News not found for adminId: " + adminId));
    }

    @Override
    public List<News> getAllNews() {
        logger.info("Getting all news");
        return newsRepository.findAll();
    }

    @Override
    public News updateNewsByNewsId(Long newsId, News updatedNews) {
        logger.info("Updating news by newsId {}", newsId);
        News existingNews = newsRepository.findById(newsId)
                .orElseThrow(() -> new NewsNotFound("News not found with id: " + newsId));

        existingNews.setNewsTitle(updatedNews.getNewsTitle());
        existingNews.setNewsContent(updatedNews.getNewsContent());
        existingNews.setImageUrl(updatedNews.getImageUrl());

        return newsRepository.save(existingNews);
    }

    public News updateNewsWithImage(Long newsId, News updatedNews, MultipartFile imageFile) {
        logger.info("Updating news with image for newsId {}", newsId);
        News existingNews = newsRepository.findById(newsId)
                .orElseThrow(() -> new NewsNotFound("News not found with id: " + newsId));

        try {
            existingNews.setNewsTitle(updatedNews.getNewsTitle());
            existingNews.setNewsContent(updatedNews.getNewsContent());

            if (imageFile != null && !imageFile.isEmpty()) {
                String imagePath = uploadImage(imageFile);
                existingNews.setImageUrl(Arrays.toString(imagePath.getBytes()));
            }

            return newsRepository.save(existingNews);
        } catch (IOException e) {
            logger.error("Failed to upload image during update", e);
            throw new RuntimeException("Image upload failed", e);
        }
    }

    @Override
    public void deleteNewsByNewsId(Long newsId) {
        logger.info("Deleting news by newsId {}", newsId);
        News existingNews = newsRepository.findById(newsId)
                .orElseThrow(() -> new NewsNotFound("News not found with id: " + newsId));

        newsRepository.delete(existingNews);
    }

    public String uploadImage(MultipartFile file) throws IOException {
        Path path = Paths.get(uploadDir);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = path.resolve(filename);
        file.transferTo(filePath.toFile());

        return filename;
    }
}
