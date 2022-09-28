package server.repository;

import server.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, CrudRepository<User, Long> {
    
    User findByUsername(String username);
    
    @Modifying
    @Query("update User u set u.online = 'f' where u.id = :id")
    void logout(Long id);
    
    @Modifying
    @Query("update User u set u.online = 't' where u.id = :id")
    void login(Long id);
}
