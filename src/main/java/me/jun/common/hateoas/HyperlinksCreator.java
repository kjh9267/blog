package me.jun.common.hateoas;

import org.springframework.hateoas.RepresentationModel;

public interface HyperlinksCreator {

    RepresentationModel createHyperlinks();
}
