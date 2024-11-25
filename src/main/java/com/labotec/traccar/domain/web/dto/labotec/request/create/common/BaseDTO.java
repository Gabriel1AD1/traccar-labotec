package com.labotec.traccar.domain.web.dto.labotec.request.create.common;
import com.labotec.traccar.domain.database.models.Company;
import com.labotec.traccar.domain.database.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BaseDTO {
    protected User user;

    protected Company company;
}

