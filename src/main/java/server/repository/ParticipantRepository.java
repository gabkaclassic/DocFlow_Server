package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import server.entity.process.Participant;
import server.entity.user.User;

public interface ParticipantRepository extends JpaRepository<Participant, Long>,
        CrudRepository<Participant, Long> {
    
    Participant findByOwner(User user);
}
