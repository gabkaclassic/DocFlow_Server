package server.entity.team;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import server.entity.deserializer.InviteDeserializer;
import server.entity.process.Participant;

import javax.persistence.*;

@Entity
@Table(name = "invites")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize(using = InviteDeserializer.class)
public class Invite {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    
    @ManyToOne
    private Team team;
    
    @ManyToOne
    private Participant candidate;
    
    public Invite(Team team, Participant participant) {
        
        this.team = team;
        this.candidate = participant;
    }
}
