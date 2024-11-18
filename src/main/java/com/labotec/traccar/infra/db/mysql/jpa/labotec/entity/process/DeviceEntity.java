package com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.process;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "traccar_device_id")
    private Long traccarDeviceId;

    @Column(name = "vehicle_id")
    private Long vehicleId;

    public DeviceEntity(Long traccarDeviceId, Long vehicleId) {
        this.traccarDeviceId = traccarDeviceId;
        this.vehicleId = vehicleId;
    }

    @OneToMany(mappedBy = "device", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ParameterEntity> parameters = new ArrayList<>();


    public void addParameter(ParameterEntity parameter) {
        parameters.add(parameter);
        parameter.setDevice(this);
    }

    public ParameterEntity getParameter(String parameterName) {
        return parameters.stream()
                .filter(parameter -> parameter.getName().equals(parameterName))
                .findFirst()
                .orElse(null);
    }
}