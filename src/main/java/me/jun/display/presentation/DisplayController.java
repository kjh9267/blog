package me.jun.display.presentation;

import lombok.RequiredArgsConstructor;
import me.jun.display.application.DisplayService;
import me.jun.display.application.dto.CategoryArticlesResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("api/display")
@RequiredArgsConstructor
public class DisplayController {

    private final DisplayService displayService;

    @GetMapping(
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CategoryArticlesResponse> display(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {

        CategoryArticlesResponse response = displayService.retrieveDisplay(page, size);

        return ResponseEntity.ok(response);
    }
}
