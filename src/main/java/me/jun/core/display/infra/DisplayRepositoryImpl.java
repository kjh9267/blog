package me.jun.core.display.infra;

import lombok.RequiredArgsConstructor;
import me.jun.core.display.application.dto.CategoryArticleResponse;
import me.jun.core.display.domain.repository.DisplayRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DisplayRepositoryImpl implements DisplayRepository<CategoryArticleResponse> {

    @PersistenceContext
    private final EntityManager entityManager;

    public Page<CategoryArticleResponse> retrieveDisplay(int page, int size) {
        String query = "SELECT /*+ JOIN_FIXED_ORDER() */ " +
                "a.id, a.title, a.content, c.name, m.name member_name " +
                "FROM article a " +
                "JOIN member m " +
                "ON a.writer_id = m.id " +
                "JOIN category c " +
                "ON a.category_id = c.id " +
                "ORDER BY a.id ASC LIMIT ? OFFSET ?";

        List resultList = entityManager.createNativeQuery(query)
                .setParameter(1, size)
                .setParameter(2, page * size)
                .getResultList();

        return new PageImpl<>(resultList);
    }
}
