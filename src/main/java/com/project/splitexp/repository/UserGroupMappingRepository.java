package com.project.splitexp.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.project.splitexp.repository.models.UserGroupMapping;

public interface UserGroupMappingRepository extends CrudRepository<UserGroupMapping, UUID> {

  public List<UserGroupMapping> findByGroupId(UUID groupId);
}
