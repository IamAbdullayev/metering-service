package com.ramazan.metering.repository;

import com.ramazan.metering.model.entity.Meter;
import com.ramazan.metering.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MeterRepository extends JpaRepository<Meter, UUID> {
    Optional<Meter> findMeterById(UUID id);

    List<Meter> findAllByUser(User user);
}
