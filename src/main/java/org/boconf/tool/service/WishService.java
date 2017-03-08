package org.boconf.tool.service;

import org.boconf.tool.domain.Wish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Wish.
 */
public interface WishService {

    /**
     * Save a wish.
     *
     * @param wish the entity to save
     * @return the persisted entity
     */
    Wish save(Wish wish);

    /**
     *  Get all the wishes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Wish> findAll(Pageable pageable);

    /**
     *  Get the "id" wish.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Wish findOne(Long id);

    /**
     *  Delete the "id" wish.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
