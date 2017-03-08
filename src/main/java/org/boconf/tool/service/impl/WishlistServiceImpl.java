package org.boconf.tool.service.impl;

import org.boconf.tool.service.WishlistService;
import org.boconf.tool.domain.Wishlist;
import org.boconf.tool.repository.WishlistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Wishlist.
 */
@Service
@Transactional
public class WishlistServiceImpl implements WishlistService{

    private final Logger log = LoggerFactory.getLogger(WishlistServiceImpl.class);
    
    @Inject
    private WishlistRepository wishlistRepository;

    /**
     * Save a wishlist.
     *
     * @param wishlist the entity to save
     * @return the persisted entity
     */
    public Wishlist save(Wishlist wishlist) {
        log.debug("Request to save Wishlist : {}", wishlist);
        Wishlist result = wishlistRepository.save(wishlist);
        return result;
    }

    /**
     *  Get all the wishlists.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Wishlist> findAll(Pageable pageable) {
        log.debug("Request to get all Wishlists");
        Page<Wishlist> result = wishlistRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one wishlist by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Wishlist findOne(Long id) {
        log.debug("Request to get Wishlist : {}", id);
        Wishlist wishlist = wishlistRepository.findOne(id);
        return wishlist;
    }

    /**
     *  Delete the  wishlist by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Wishlist : {}", id);
        wishlistRepository.delete(id);
    }
}
