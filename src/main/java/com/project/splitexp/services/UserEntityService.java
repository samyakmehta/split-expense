package com.project.splitexp.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.splitexp.exceptions.GroupNotFoundException;
import com.project.splitexp.repository.GroupRepository;
import com.project.splitexp.repository.UserGroupMappingRepository;
import com.project.splitexp.repository.UserRepository;
import com.project.splitexp.repository.models.Group;
import com.project.splitexp.repository.models.User;
import com.project.splitexp.repository.models.UserGroupMapping;
import com.project.splitexp.response.models.GroupInformation;

@Service
public class UserEntityService {

  private final UserRepository userRepository;
  private final GroupRepository groupRepository;
  private final UserGroupMappingRepository userGroupMappingRepository;

  public UserEntityService(UserRepository userRepository, GroupRepository groupRepository,
      UserGroupMappingRepository userGroupMappingRepository) {

    this.userRepository = userRepository;
    this.groupRepository = groupRepository;
    this.userGroupMappingRepository = userGroupMappingRepository;
  }

  public User getUser(String id) {

    return userRepository.findById(UUID.fromString(id)).orElse(null);
  }

  public Set<User> getAllUsers() {

    Set<User> users = new HashSet<User>();
    userRepository.findAll().forEach(users::add);
    return users;
  }

  public Set<Group> getAllGroups() {

    Set<Group> groups = new HashSet<Group>();
    groupRepository.findAll().forEach(groups::add);
    return groups;
  }

  public GroupInformation getGroupInformation(String groupId) throws GroupNotFoundException {

    Group group = groupRepository.findById(UUID.fromString(groupId)).orElse(null);
    if (group == null) {
      throw new GroupNotFoundException("Group not found with id " + groupId);
    }

    GroupInformation groupInformation = new GroupInformation();
    groupInformation.setGroupId(group.getId());
    groupInformation.setName(group.getName());

    List<UserGroupMapping> userGroupMappings = userGroupMappingRepository.findByGroupId(UUID.fromString(groupId));
    Set<UUID> userIds = userGroupMappings.stream().map(UserGroupMapping::getUserId).collect(Collectors.toSet());
    groupInformation.setUserIds(userIds);

    return groupInformation;
  }
}
