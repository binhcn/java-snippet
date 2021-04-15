package dev.binhcn.service;

import dev.binhcn.entity.Actress;
import dev.binhcn.entity.Author;
import dev.binhcn.repository.ActressRepository;
import dev.binhcn.repository.AuthorRepository;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookstoreService {

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;

    private final AuthorRepository authorRepository;
    private final ActressRepository actressRepository;

    private final EntityManager entityManager;

    @Transactional
    public void batchAuthors() {

//        List<Author> authors = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Author author = new Author();
            author.setId((long) i + 1);
            author.setName("Name_" + i);
            author.setGenre("Genre_" + i);
            author.setAge(18 + i);

            long id = (long) i + 1;
//            if (i == 2) {
//                id = 2;
//            }
            Actress actress = new Actress();
            actress.setId(id);
            actress.setName("Name_" + i);
            actress.setGenre("Genre_" + i);
            actress.setAge(18 + i);

            authorRepository.save(author);
            actressRepository.save(actress);
//            entityManager.persist(author);
//            entityManager.persist(actress);

            entityManager.flush();
//            authors.add(author);
//            if (i % batchSize == 0 && i > 0) {
//                authorRepository.saveAll(authors);
//                authors.clear();
//            }
        }

//        if (authors.size() > 0) {
//            authorRepository.saveAll(authors);
//            authors.clear();
//        }
    }
}