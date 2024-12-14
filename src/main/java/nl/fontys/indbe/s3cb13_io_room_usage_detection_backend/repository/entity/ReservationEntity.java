package nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.repository.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Reservation")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "room_id")
    private MeetingRoomEntity meetingRoom;
    @NotNull
    @Min(0)
    @Column(name = "max_occupancy")
    private Integer maxOccupancy;
}
