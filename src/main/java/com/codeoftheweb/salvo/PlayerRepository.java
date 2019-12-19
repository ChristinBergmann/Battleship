package com.codeoftheweb.salvo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource
public interface PlayerRepository extends JpaRepository<Player, Long> {
    //Player findByUserEmail(@Param("userEmail") String userEmail);
   // Player findByUserName(@Param("user") String userName);

}