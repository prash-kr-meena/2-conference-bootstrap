package meena.prashant.conferencebootstrap.repositories;

import meena.prashant.conferencebootstrap.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository <Session, Long> {

}
