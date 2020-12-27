package com.aja.store.repository;

import com.aja.store.domain.Store;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Store entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StoreRepository extends JpaRepository<Store, Long>, JpaSpecificationExecutor<Store> {

    @Query("select store from Store store where store.user.login = ?#{principal.username}")
    List<Store> findByUserIsCurrentUser();
}
