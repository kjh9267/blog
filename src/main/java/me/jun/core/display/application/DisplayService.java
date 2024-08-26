package me.jun.core.display.application;

import lombok.RequiredArgsConstructor;
import me.jun.core.display.application.dto.CategoryArticleResponse;
import me.jun.core.display.application.dto.CategoryArticlesResponse;
import me.jun.core.display.domain.repository.DisplayRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DisplayService {

    private final DisplayRepository<CategoryArticleResponse> displayRepositoryImpl;

    public CategoryArticlesResponse retrieveDisplay(int page, int size) {
        Page result = displayRepositoryImpl.retrieveDisplay(page, size);

        return CategoryArticlesResponse.of(result);
    }
}
