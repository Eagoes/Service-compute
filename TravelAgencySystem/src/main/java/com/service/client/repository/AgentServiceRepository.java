package com.service.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.service.client.model.AgentService;

@Repository("serviceRepository")
public interface AgentServiceRepository extends JpaRepository<AgentService, Long> {
	void deleteById(long id);
}
