package com.project.splitexp.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.project.splitexp.repository.models.EventUserMapping;

public interface EventUserMappingRepository extends CrudRepository<EventUserMapping, UUID> {

  public List<EventUserMapping> findByEventId(UUID eventId);
}
