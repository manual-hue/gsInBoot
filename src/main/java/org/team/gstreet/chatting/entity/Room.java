package org.team.gstreet.chatting.entity;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Room {

    int roomNumber;
    String roomName;

    @Override
    public String toString() {
        return "Room [roomNumber=" + roomNumber + ", roomName=" + roomName + "]";
    }



}
