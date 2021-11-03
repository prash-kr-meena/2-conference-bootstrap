package meena.prashant.conferencebootstrap.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//@ToString // @ToString causes problems with lazy loading data, we can @Exclude them  e.g. speakers
@Table(name = "sessions")
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Session {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "session_id", nullable = false)
  private Long id;

  @Column(name = "session_name", nullable = false, length = 80)
  private String sessionName;

  @Column(name = "session_description", nullable = false, length = 1024)
  private String sessionDescription;

  @Column(name = "session_length", nullable = false)
  private Integer sessionLength;

  @ManyToMany
  @JoinTable(
      name = "session_speakers",
      joinColumns = @JoinColumn(name = "session_id"),
      inverseJoinColumns = {@JoinColumn(name = "speaker_id")})
  //  @Exclude
  private List<Speaker> speakers;


  public Integer getSessionLength() {
    log.info("Inside {}", Thread.currentThread().getStackTrace()[1].getMethodName());
    return sessionLength;
  }

  public void setSessionLength(Integer sessionLength) {
    log.info("Inside {}", Thread.currentThread().getStackTrace()[1].getMethodName());
    this.sessionLength = sessionLength;
  }

  public String getSessionDescription() {
    log.info("Inside {}", Thread.currentThread().getStackTrace()[1].getMethodName());
    return sessionDescription;
  }

  public void setSessionDescription(String sessionDescription) {
    log.info("Inside {}", Thread.currentThread().getStackTrace()[1].getMethodName());
    this.sessionDescription = sessionDescription;
  }

  public String getSessionName() {
    log.info("Inside {}", Thread.currentThread().getStackTrace()[1].getMethodName());
    return sessionName;
  }

  public void setSessionName(String sessionName) {
    log.info("Inside {}", Thread.currentThread().getStackTrace()[1].getMethodName());
    this.sessionName = sessionName;
  }

  public Long getId() {
    log.info("Inside {}", Thread.currentThread().getStackTrace()[1].getMethodName());
    return id;
  }

  public void setId(Long id) {
    log.info("Inside {}", Thread.currentThread().getStackTrace()[1].getMethodName());
    this.id = id;
  }

  public List<Speaker> getSpeakers() {
    log.info("Inside {}", Thread.currentThread().getStackTrace()[1].getMethodName());
    return speakers;
  }

  public void setSpeakers(List<Speaker> speakers) {
    log.info("Inside {}", Thread.currentThread().getStackTrace()[1].getMethodName());
    this.speakers = speakers;
  }
}