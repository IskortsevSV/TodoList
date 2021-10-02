package org.example.services;

import org.example.domain.Tag;
import org.example.repositories.TagRepository;
import org.example.services.inrerfaces.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * by Iskortsev S.V.
 */

@Service
public class TagService implements ITagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    @Transactional
    public Tag findOrCreate(Tag tag) {

        List<Tag> foundTags = tagRepository.findByName(tag.getName());

        if(foundTags.isEmpty()) {
            tagRepository.save(tag);
            return tag;
        } else {
            return foundTags.get(0);
        }
    }
}