package com.aja.store.repository;

import com.aja.store.domain.ItemInstanceTransact;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the ItemInstanceTransact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemInstanceTransactRepository extends JpaRepository<ItemInstanceTransact, Long>, JpaSpecificationExecutor<ItemInstanceTransact> {

    @Query("select itemInstanceTransact from ItemInstanceTransact itemInstanceTransact where itemInstanceTransact.user.login = ?#{principal.username}")
    List<ItemInstanceTransact> findByUserIsCurrentUser();
}
