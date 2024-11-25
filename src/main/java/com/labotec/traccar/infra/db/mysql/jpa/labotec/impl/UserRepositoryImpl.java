package com.labotec.traccar.infra.db.mysql.jpa.labotec.impl;

import com.labotec.traccar.app.ports.input.repository.UserRepository;
import com.labotec.traccar.domain.database.models.User;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.UserEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.UserMapper;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.UserRepositoryJpa;
import com.labotec.traccar.infra.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserRepositoryJpa userRepositoryJpa;
    private final UserMapper userMapper;

    @Override
    @Cacheable(value = "userCache", key = "#userId")
    public User findByUserId(Long userId) {
        System.out.println("Fetching user from database for userId: " + userId);
        UserEntity userEntity = userRepositoryJpa.findById(userId).orElseThrow(
                ()-> new EntityNotFoundException("Usuario no encontrado: " + userId)
        );
        return userMapper.toDomain(userEntity);
    }

    @Override
    public void deleById(Long userId) {
      userRepositoryJpa.deleteById(userId);
    }

    @Override
    public User create(User user) {
        UserEntity userEntity = userMapper.toEntity(user);
        return userMapper.toDomain(userRepositoryJpa.save(userEntity));
    }

}
