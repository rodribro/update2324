package ncc.up.pt.updateBackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ncc.up.pt.updateBackend.Model.Attendees;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendeesRepository extends JpaRepository<Attendees, Long> {
}
