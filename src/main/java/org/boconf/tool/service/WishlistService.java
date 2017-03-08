package org.boconf.tool.service;

import org.boconf.tool.domain.Wishlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Wishlist.
 */
public interface WishlistService {

    /**
     * Save a wishlist.
     *
     * @param wishlist the entity to save
     * @return the persisted entity
     */
    Wishlist save(Wishlist wishlist);

    /**
     *  Get all the wishlists.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Wishlist> findAll(Pageable pageable);

    /**
     *  Get the "id" wishlist.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Wishlist findOne(Long id);

    /**
     *  Delete the "id" wishlist.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
