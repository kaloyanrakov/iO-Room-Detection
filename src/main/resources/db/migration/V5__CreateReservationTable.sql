CREATE TABLE `Reservation` (
   `id` BIGINT NOT NULL AUTO_INCREMENT,
   `room_id` BIGINT NOT NULL,
   `max_occupancy` INT NOT NULL DEFAULT 0,
   PRIMARY KEY (`id`),
   FOREIGN KEY (`room_id`) REFERENCES `Meeting_Room` (`id`)
);