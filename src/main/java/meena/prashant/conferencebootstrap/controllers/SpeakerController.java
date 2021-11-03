package meena.prashant.conferencebootstrap.controllers;

import static org.springframework.http.HttpStatus.CREATED;

import java.util.List;
import meena.prashant.conferencebootstrap.models.Speaker;
import meena.prashant.conferencebootstrap.repositories.SpeakerRepository;
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
@RequestMapping("/api/v1/speakers")
public class SpeakerController {
  final SpeakerRepository speakerRepository;

  public SpeakerController(SpeakerRepository speakerRepository) {
    this.speakerRepository = speakerRepository;
  }

  @GetMapping
  public List<Speaker> getAllSpeakers() {
    return speakerRepository.findAll();
  }

  @GetMapping("/{id}")
  public Speaker getSpeakerById(@PathVariable Long id) {
    return speakerRepository.getById(id);
  }

  @PostMapping
  @ResponseStatus(code = CREATED)
  public Speaker postSpeaker(@RequestBody Speaker speaker) {
    return speakerRepository.save(speaker);
  }

  @DeleteMapping("/{id}")
  public void deleteSpeakerById(@PathVariable Long id) {
    // Also need to check the children (dependent) records, before deleting the parent
    // Basically will call it cascade
    // Will get a foreign key constraint exception when trying to delete it directly
    // Homework: write logic for deleting the children records for this session
    speakerRepository.deleteById(id);
  }

  @PutMapping("/{id}")
  public Speaker updateSpeakerById(@PathVariable Long id, @RequestBody Speaker speaker) {
    //  Add validation to the incoming speaker data, before updating the existing data in DB, otherwise return a 400

    // As this is a PUT api, which expects all the data fields to be updated
    // (hence you need to pass all the data to the request, otherwise the updated objects will be assigned to null)

    // To update only a specific portion of the api, you need to implement the PATCH api
    Speaker existingSpeaker = speakerRepository.getById(id);
    BeanUtils.copyProperties(speaker, existingSpeaker, "id"); // not "speaker_id"
    return speakerRepository.saveAndFlush(existingSpeaker);
  }

}
