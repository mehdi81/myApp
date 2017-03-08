package org.boconf.tool.repository;

import org.boconf.tool.domain.Wishlist;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Wishlist entity.
 */
@SuppressWarnings("unused")
public interface WishlistRepository extends JpaRepository<Wishlist,Long> {

    @Query("select wishlist from Wishlist wishlist where wishlist.user.login = ?#{principal.username}")
    List<Wishlist> findByUserIsCurrentUser();

}
