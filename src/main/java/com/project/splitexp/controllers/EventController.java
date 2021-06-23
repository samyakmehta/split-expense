package com.project.splitexp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.splitexp.exceptions.EventNotFoundException;
import com.project.splitexp.exceptions.InvalidEventCreationRequest;
import com.project.splitexp.request.models.EventCreationRequest;
import com.project.splitexp.response.models.EventInformation;
import com.project.splitexp.services.EventService;

@RestController
public class EventController {

  private final EventService eventService;

  @Autowired
  public EventController(EventService eventService) {
    this.eventService = eventService;
  }

  @PostMapping("/v1/event")
  public EventInformation createEvent(@RequestBody EventCreationRequest eventCreationRequest)
      throws InvalidEventCreationRequest {

    return eventService.createEvent(eventCreationRequest);
  }

  @GetMapping("/v1/event/{eventId}")
  public EventInformation createEvent(@PathVariable String eventId) throws EventNotFoundException {

    return eventService.getEventInformation(eventId);
  }
}
