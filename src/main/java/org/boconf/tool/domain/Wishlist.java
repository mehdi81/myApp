package org.boconf.tool.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Wishlist.
 */
@Entity
@Table(name = "wishlist")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Wishlist implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "hidden")
    private Boolean hidden;

    @ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Wishlist name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Wishlist creationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Boolean isHidden() {
        return hidden;
    }

    public Wishlist hidden(Boolean hidden) {
        this.hidden = hidden;
        return this;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public User getUser() {
        return user;
    }

    public Wishlist user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Wishlist wishlist = (Wishlist) o;
        if(wishlist.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, wishlist.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Wishlist{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", creationDate='" + creationDate + "'" +
            ", hidden='" + hidden + "'" +
            '}';
    }
}
