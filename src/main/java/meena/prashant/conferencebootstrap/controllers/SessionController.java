package meena.prashant.conferencebootstrap.controllers;

import static org.springframework.http.HttpStatus.CREATED;

import java.util.List;
import meena.prashant.conferencebootstrap.models.Session;
import meena.prashant.conferencebootstrap.repositories.SessionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionController {

  final SessionRepository sessionRepository;

  public SessionController(SessionRepository sessionRepository) {
    this.sessionRepository = sessionRepository;
  }

  @GetMapping
  public List<Session> getAllSessions() {
    //    return sessionRepository.findAll();
    List<Session> allSessions = sessionRepository.findAll();
    System.out.println(allSessions);
    return allSessions;
  }

  @GetMapping("/{id}")
  public Session getSessionById(@PathVariable Long id) {
    Session session = sessionRepository.getById(id);
    // Handle the case when the id is not present in the DB, right now app is crashing,
    // due to some serialization issue, as null can't be marshaled

    //    if (session == null) {
    //      throw new NoSessionFoundException("No such session with id=" + id);
    //    }
    return session;
  }

  @PostMapping
  @ResponseStatus(code = CREATED)
  public Session postSession(@RequestBody final Session session) {
    //    return sessionRepository.save(session);
    return sessionRepository.saveAndFlush(session);
  }

  @DeleteMapping("/{id}")
  public void deleteSessionById(@PathVariable Long id) {
    // Also need to check the children (dependent) records, before deleting the parent
    // Basically will call it cascade
    // Will get a foreign key constraint exception when trying to delete it directly
    // Homework: write logic for deleting the children records for this session
    sessionRepository.deleteById(id);
  }

  @PutMapping("/{id}")
  public Session updateSessionById(@PathVariable Long id, @RequestBody Session session) {
    //  Add validation to the incoming session data, before updating the existing data in DB, otherwise return a 400

    // As this is a PUT api, which expects all the data fields to be updated
    // (hence you need to pass all the data to the request, otherwise the updated objects will be assigned to null)

    // To update only a specific portion of the api, you need to implement the PATCH api
    Session existingSession = sessionRepository.getById(id);
    BeanUtils.copyProperties(session, existingSession, "id");
    Session savedSession = sessionRepository.saveAndFlush(existingSession);
    System.out.println("done");
    return savedSession;
  }

}
