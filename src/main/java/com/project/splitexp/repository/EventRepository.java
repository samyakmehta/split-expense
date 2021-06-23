package com.project.splitexp.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.project.splitexp.repository.models.Event;

public interface EventRepository extends CrudRepository<Event, UUID> {

}
