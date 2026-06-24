package com.bomberos.monitoreo.repository;

import com.bomberos.monitoreo.model.ZonaMonitoreo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitoreoRepository extends JpaRepository<ZonaMonitoreo, Long> {
}