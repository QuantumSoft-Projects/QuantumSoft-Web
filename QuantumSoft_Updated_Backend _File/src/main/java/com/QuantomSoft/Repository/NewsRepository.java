package com.QuantomSoft.Repository;

import com.QuantomSoft.Entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewsRepository extends JpaRepository<News,Long> {

    Optional<News> findByAdminId(Long adminId);
}