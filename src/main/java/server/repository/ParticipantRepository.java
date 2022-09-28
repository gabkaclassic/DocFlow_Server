package server.repository;

import server.entity.process.Participant;
import server.entity.user.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Long>,
        CrudRepository<Participant, Long> {
    
    Participant findByOwner(Client owner);
}
