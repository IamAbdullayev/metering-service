package com.ramazan.metering.repository;

import com.ramazan.metering.model.entity.Meter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MeterRepository extends JpaRepository<Meter, UUID> {
}
