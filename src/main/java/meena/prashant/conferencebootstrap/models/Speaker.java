package meena.prashant.conferencebootstrap.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

@Table(name = "speakers")
@Entity
@Setter
@Getter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Speaker {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "speaker_id", nullable = false)
  private Long id;

  @Column(name = "first_name", nullable = false, length = 30)
  private String firstName;

  @Column(name = "last_name", nullable = false, length = 30)
  private String lastName;

  @Column(name = "title", nullable = false, length = 40)
  private String title;

  @Column(name = "company", nullable = false, length = 50)
  private String company;

  @Column(name = "speaker_bio", nullable = false, length = 2000)
  private String speakerBio;

  @Lob          // Large Object
  @Type(type = "org.hibernate.type.BinaryType")
  @Column(name = "speaker_photo")
  private byte[] speakerPhoto;
  // Note : Need some more annotation, on this to get the JPA to stream the binary data to this field
  // In our case, Hibernate is the underlying JPA implementation that we are using under the cover
  // Hence we are using the above type

  @ManyToMany(mappedBy = "speakers")
  @JsonIgnore
  private List<Session> sessions;

}