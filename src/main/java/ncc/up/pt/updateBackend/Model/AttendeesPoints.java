package ncc.up.pt.updateBackend.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;

@Entity
public class AttendeesPoints {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    private int points;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "attendee_id", referencedColumnName = "id")
    private Attendees attendee;

    // Default constructor
    public AttendeesPoints() {
    }

    // Parameterized constructor
    public AttendeesPoints(Long id, Attendees attendee, int points) {
        this.id = id;
        this.attendee = attendee;
        this.points = points;
    }

    // Method to add points
    public void addPoints(int pointsToAdd) {
        this.points += pointsToAdd;
    }

    // Standard getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Attendees getAttendee() {
        return attendee;
    }

    public void setAttendee(Attendees attendee) {
        this.attendee = attendee;
    }
}
