package server.repository;

import server.entity.user.Client;
import server.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long>,
        JpaRepository<Client, Long> {
    
    Client findByAccount(User account);
}
