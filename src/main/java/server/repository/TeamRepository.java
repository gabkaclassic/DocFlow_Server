package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import server.entity.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, String>, CrudRepository<Team, String> {
    
    @Transactional
    <S extends Team> S save(S entity);
}
