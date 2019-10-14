package com.service.client.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.service.client.model.AgentService;

import com.service.client.repository.AgentServiceRepository;

@Service("agentServiceService")
public class AgentServiceService {
	private AgentServiceRepository agentServiceRepository;
	
	@Autowired
	public AgentServiceService(AgentServiceRepository agentServiceRepository) {
		this.agentServiceRepository = agentServiceRepository;
	}

	public List<AgentService> findAll() {
		return agentServiceRepository.findAll();
	}
	
	public AgentService saveAgentService(AgentService agentService) {
		return agentServiceRepository.save(agentService);
	}
	
	public void delete(Long id) {
		agentServiceRepository.deleteById(id);
	}
}
