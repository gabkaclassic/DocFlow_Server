package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import server.entity.process.Participant;
import server.entity.team.Invite;
import server.entity.team.Team;

@Repository
public interface InviteRepository extends JpaRepository<Invite, Long>, CrudRepository<Invite, Long> {

    void deleteByTeam(Team team);
    
    void deleteByCandidate(Participant candidate);
}
