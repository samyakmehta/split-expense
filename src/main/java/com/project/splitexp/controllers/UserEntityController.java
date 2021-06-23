package com.project.splitexp.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.project.splitexp.repository.models.Group;
import com.project.splitexp.repository.models.User;
import com.project.splitexp.response.models.GroupInformation;
import com.project.splitexp.response.models.GroupsResponse;
import com.project.splitexp.response.models.UsersResponse;
import com.project.splitexp.services.UserEntityService;

@RestController
public class UserEntityController {

  private final UserEntityService userEntityService;

  @Autowired
  public UserEntityController(UserEntityService userEntityService) {
    this.userEntityService = userEntityService;
  }

  @GetMapping("/v1/users")
  public UsersResponse getAllUsers() {
    Set<User> users = userEntityService.getAllUsers();
    return new UsersResponse(users);
  }

  @GetMapping("/v1/user/{userId}")
  public User getUser(@PathVariable String userId) {
    return userEntityService.getUser(userId);
  }

  @GetMapping("/v1/groups")
  public GroupsResponse getAllGroups() {
    Set<Group> groups = userEntityService.getAllGroups();
    return new GroupsResponse(groups);
  }

  @GetMapping("/v1/group/{groupId}")
  public GroupInformation getGroupInformation(@PathVariable String groupId) {
    return userEntityService.getGroupInformation(groupId);
  }
}
