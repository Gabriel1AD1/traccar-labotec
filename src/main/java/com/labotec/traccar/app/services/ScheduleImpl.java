package com.labotec.traccar.app.services;

import com.labotec.traccar.app.usecase.ports.input.ScheduleRepository;
import com.labotec.traccar.app.usecase.ports.out.GenericCrudService;
import com.labotec.traccar.domain.database.models.Schedule;
import com.labotec.traccar.domain.web.dto.ScheduleDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor

public class ScheduleImpl implements GenericCrudService<Schedule, ScheduleDTO , Integer> {
    private final ScheduleRepository scheduleRepository;
    @Override
    public Schedule create(ScheduleDTO entityDto) {
        return scheduleRepository.create(entityDto);
    }

    @Override
    public Schedule findById(Integer integer) {
        return scheduleRepository.findById(integer);
    }

    @Override
    public Iterable<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    @Override
    public Schedule update(ScheduleDTO entityDto, Integer integer) {
        return scheduleRepository.update(entityDto,integer);
    }

    @Override
    public void deleteById(Integer integer) {
        scheduleRepository.deleteById(integer);
    }
}
