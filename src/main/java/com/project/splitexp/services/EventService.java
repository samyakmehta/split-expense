package com.project.splitexp.services;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.splitexp.repository.EventRepository;
import com.project.splitexp.repository.EventUserMappingRepository;
import com.project.splitexp.repository.models.Event;
import com.project.splitexp.repository.models.EventUserMapping;
import com.project.splitexp.repository.models.User;
import com.project.splitexp.request.models.EventCreationRequest;
import com.project.splitexp.response.models.EventInformation;

@Service
public class EventService {

  private final EventRepository eventRepository;
  private final EventUserMappingRepository eventUserMappingRepo;
  private final UserEntityService userEntityService;

  @Autowired
  public EventService(EventRepository eventRepository, EventUserMappingRepository eventUserMappingRepo,
      UserEntityService userEntityService) {
    this.eventRepository = eventRepository;
    this.eventUserMappingRepo = eventUserMappingRepo;
    this.userEntityService = userEntityService;
  }

  public Event getEvent(String eventId) {
    return eventRepository.findById(UUID.fromString(eventId)).orElse(null);
  }

  public EventInformation getEventInformation(String eventId) {

    Event event = getEvent(eventId);
    if (event == null) {
      // throw exception
    }

    Set<UUID> userIds = new HashSet<UUID>();
    List<EventUserMapping> eventUserMappings = eventUserMappingRepo.findByEventId(event.getId());

    for (EventUserMapping eventUserMapping : eventUserMappings) {
      userIds.add(eventUserMapping.getUserId());
    }

    return new EventInformation(event.getId(), event.getName(), userIds, event.getCreatedAt());
  }

  public EventInformation createEvent(EventCreationRequest eventCreationRequest) {

    validateEventCrationRequest(eventCreationRequest);
    OffsetDateTime createdAt = OffsetDateTime.now();
    Event event = Event.builder().name(eventCreationRequest.getName()).createdAt(createdAt).build();
    event = eventRepository.save(event);
    Set<UUID> userIds = new HashSet<UUID>();

    for (String id : eventCreationRequest.getUserIds()) {
      EventUserMapping eventUserMapping = eventUserMappingRepo
          .save(EventUserMapping.builder().eventId(event.getId()).userId(UUID.fromString(id)).build());
      userIds.add(eventUserMapping.getUserId());
    }

    return new EventInformation(event.getId(), event.getName(), userIds, createdAt);

  }

  private void validateEventCrationRequest(EventCreationRequest eventCreationRequest) {

    if (StringUtils.isEmpty(eventCreationRequest.getName())) {
      // throw exception
    }

    if (eventCreationRequest.getUserIds() == null || eventCreationRequest.getUserIds().isEmpty()) {
      // throw exception
    }

    for (String userId : eventCreationRequest.getUserIds()) {
      User user = userEntityService.getUser(userId);
      if (user == null) {
        // throw exception
      }
    }
  }
}
