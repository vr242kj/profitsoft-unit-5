package com.example.profitsoftunit5.repository;

import com.example.profitsoftunit5.model.entity.Email;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailRepository extends ElasticsearchRepository<Email, String> {
    List<Email> findByStatus(String status);

}
