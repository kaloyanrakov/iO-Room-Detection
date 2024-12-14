package nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.repository;

import nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.repository.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    @Query("select r.maxOccupancy from ReservationEntity r where r.meetingRoom.id = :meetingRoomId")
    Integer getReservationMaxOccupancyByMeetingRoomId(long meetingRoomId);

    @Modifying
    @Query("update ReservationEntity r set r.maxOccupancy = :maxOccupancy where r.meetingRoom.id = :meetingRoomId")
    void updateReservationMaxOccupancy(@Param("meetingRoomId") long meetingRoomId, @Param("maxOccupancy") int maxOccupancy);
}
