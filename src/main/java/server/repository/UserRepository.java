package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import server.entity.process.document.Document;
import server.entity.user.User;

/**
 * Репозиторий для сущности "Пользователь"
 * @see User
 * */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, CrudRepository<User, Long> {
    
    User findByUsername(String username);
    
    @Modifying
    @Transactional
    @Query("update User u set u.online = 'f' where u.username = :username")
    void logout(String username);
    
    @Modifying
    @Transactional
    @Query("update User u set u.online = 't' where u.username = :username")
    void login(String username);
    
    boolean existsByUsername(String login);
}
