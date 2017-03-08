package org.boconf.tool.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.boconf.tool.domain.Wishlist;
import org.boconf.tool.service.WishlistService;
import org.boconf.tool.web.rest.util.HeaderUtil;
import org.boconf.tool.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Wishlist.
 */
@RestController
@RequestMapping("/api")
public class WishlistResource {

    private final Logger log = LoggerFactory.getLogger(WishlistResource.class);
        
    @Inject
    private WishlistService wishlistService;

    /**
     * POST  /wishlists : Create a new wishlist.
     *
     * @param wishlist the wishlist to create
     * @return the ResponseEntity with status 201 (Created) and with body the new wishlist, or with status 400 (Bad Request) if the wishlist has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/wishlists")
    @Timed
    public ResponseEntity<Wishlist> createWishlist(@Valid @RequestBody Wishlist wishlist) throws URISyntaxException {
        log.debug("REST request to save Wishlist : {}", wishlist);
        if (wishlist.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("wishlist", "idexists", "A new wishlist cannot already have an ID")).body(null);
        }
        Wishlist result = wishlistService.save(wishlist);
        return ResponseEntity.created(new URI("/api/wishlists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("wishlist", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /wishlists : Updates an existing wishlist.
     *
     * @param wishlist the wishlist to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated wishlist,
     * or with status 400 (Bad Request) if the wishlist is not valid,
     * or with status 500 (Internal Server Error) if the wishlist couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/wishlists")
    @Timed
    public ResponseEntity<Wishlist> updateWishlist(@Valid @RequestBody Wishlist wishlist) throws URISyntaxException {
        log.debug("REST request to update Wishlist : {}", wishlist);
        if (wishlist.getId() == null) {
            return createWishlist(wishlist);
        }
        Wishlist result = wishlistService.save(wishlist);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("wishlist", wishlist.getId().toString()))
            .body(result);
    }

    /**
     * GET  /wishlists : get all the wishlists.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of wishlists in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/wishlists")
    @Timed
    public ResponseEntity<List<Wishlist>> getAllWishlists(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Wishlists");
        Page<Wishlist> page = wishlistService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/wishlists");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /wishlists/:id : get the "id" wishlist.
     *
     * @param id the id of the wishlist to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the wishlist, or with status 404 (Not Found)
     */
    @GetMapping("/wishlists/{id}")
    @Timed
    public ResponseEntity<Wishlist> getWishlist(@PathVariable Long id) {
        log.debug("REST request to get Wishlist : {}", id);
        Wishlist wishlist = wishlistService.findOne(id);
        return Optional.ofNullable(wishlist)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /wishlists/:id : delete the "id" wishlist.
     *
     * @param id the id of the wishlist to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/wishlists/{id}")
    @Timed
    public ResponseEntity<Void> deleteWishlist(@PathVariable Long id) {
        log.debug("REST request to delete Wishlist : {}", id);
        wishlistService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("wishlist", id.toString())).build();
    }

}
