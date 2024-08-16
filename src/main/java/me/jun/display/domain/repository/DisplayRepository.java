package me.jun.display.domain.repository;

import org.springframework.data.domain.Page;

public interface DisplayRepository<T> {

    Page<T> retrieveDisplay(int page, int size);
}
