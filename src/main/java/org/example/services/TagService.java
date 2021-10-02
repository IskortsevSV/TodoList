package org.example.services;

import org.example.domain.Tag;
import org.example.services.inrerfaces.ITagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * by Iskortsev S.V.
 */

@Service
public class TagService implements ITagService {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public Tag findOrCreate(Tag tag) {

        List<Tag> foundTags = this.entityManager
                .createQuery("SELECT tag FROM Tag tag WHERE tag.name = :name", Tag.class)
                .setParameter("name", tag.getName())
                .getResultList();
        if(foundTags.isEmpty()) {
            this.entityManager.persist(tag);
            return tag;
        } else {
            return foundTags.get(0);
        }
    }
}