package me.jun.guestbook.hateoas;

import org.springframework.hateoas.RepresentationModel;

public interface HyperlinksCreator {

    RepresentationModel createHyperlinks();
}
