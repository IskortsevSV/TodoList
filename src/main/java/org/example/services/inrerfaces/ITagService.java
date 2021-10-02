package org.example.services.inrerfaces;

import org.example.domain.Tag;

/**
 * by Iskortsev S.V.
 */

public interface ITagService {
    Tag findOrCreate(Tag tag);
}
