package ncc.up.pt.updateBackend.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ncc.up.pt.updateBackend.Model.Attendees;
import ncc.up.pt.updateBackend.Model.AttendeesPoints;
import org.springframework.stereotype.Repository;


@Repository
public interface AttendeesPointsRepository extends JpaRepository<AttendeesPoints, Long> {
        Optional<AttendeesPoints> findByAttendee(Attendees attendee);

}
