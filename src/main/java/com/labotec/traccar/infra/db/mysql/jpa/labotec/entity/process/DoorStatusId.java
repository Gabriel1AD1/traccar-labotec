package com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.process;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class DoorStatusId implements Serializable {

    private Long deviceId;
    private String doorId;

    public DoorStatusId() {}

    public DoorStatusId(Long deviceId, String doorId) {
        this.deviceId = deviceId;
        this.doorId = doorId;
    }

    // Getters, setters, equals y hashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoorStatusId that = (DoorStatusId) o;
        return Objects.equals(deviceId, that.deviceId) && Objects.equals(doorId, that.doorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deviceId, doorId);
    }
}
