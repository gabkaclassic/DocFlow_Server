package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import server.entity.process.Participant;
import server.entity.process.document.Document;
import server.entity.user.User;

/**
 * Репозиторий для сущности "Участник"
 * @see Participant
 * */
@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long>, CrudRepository<Participant, Long> {
    
    Participant findByOwner(User user);
}
