package me.jun.guestbook.home.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/home")
@RequiredArgsConstructor
public class HomeController {

    private final HomeRepresentationModelCreator homeRepresentationModelCreator;

    @GetMapping
    public ResponseEntity<RepresentationModel> home() {
        return ResponseEntity.ok(
                homeRepresentationModelCreator.createRepresentationModel()
        );
    }
}
