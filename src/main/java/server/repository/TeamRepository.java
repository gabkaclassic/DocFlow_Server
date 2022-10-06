package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import server.entity.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long>, CrudRepository<Team, Long> {

}
