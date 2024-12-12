package com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.process;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Entity
@Table(name = "tbl_door_status")
public class DoorStatusEntity {

    @EmbeddedId
    private DoorStatusId id;

    @Setter
    @Column(name = "state", nullable = false)
    private int state;

    @Setter
    @Column(name = "last_change_time", nullable = false)
    private Instant lastChangeTime;

    @Column(name = "alert_sent", nullable = false)
    private boolean alertSent = false;

    public DoorStatusEntity() {}

    public DoorStatusEntity(long deviceId, String doorId, int state, Instant lastChangeTime) {
        this.id = new DoorStatusId(deviceId, doorId);
        this.state = state;
        this.lastChangeTime = lastChangeTime;
    }

    public DoorStatusEntity setAlertSent(boolean alertSent) {
        this.alertSent = alertSent;
        return this;
    }
}

