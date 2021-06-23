package com.project.splitexp.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.project.splitexp.repository.models.Group;

public interface GroupRepository extends CrudRepository<Group, UUID> {

}
