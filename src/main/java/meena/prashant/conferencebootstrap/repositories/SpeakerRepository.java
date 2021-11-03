package meena.prashant.conferencebootstrap.repositories;

import meena.prashant.conferencebootstrap.models.Speaker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpeakerRepository extends JpaRepository<Speaker, Long> {
}
