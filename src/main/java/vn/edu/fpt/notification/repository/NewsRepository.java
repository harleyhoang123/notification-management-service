package vn.edu.fpt.notification.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.notification.entity.News;

import java.util.Optional;

@Repository
public interface NewsRepository extends MongoRepository<News, String> {

    Optional<News> findByTitle(String s);
}
