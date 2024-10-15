package ru.practicum.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.category.Category;
import ru.practicum.enums.State;
import ru.practicum.user.User;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(
		name = "event-entity-graph",
		attributeNodes = {
				@NamedAttributeNode("category"),
				@NamedAttributeNode("initiator"),
				@NamedAttributeNode("location"),
		}
)
@Entity
@Table(name = "events")
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String annotation;
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	private Category category;
	@Column(name = "confirmed_requests")
	private Long confirmedRequests;
	@Column(name = "created_on")
	private LocalDateTime createdOn;
	private String description;
	@Column(name = "event_date")
	private LocalDateTime eventDate;
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "initiator_id")
	private User initiator;
	@ToString.Exclude
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "location_id")
	private Location location;
	private Boolean paid;
	@Column(name = "participant_limit")
	private Long participantLimit;
	@Column(name = "published_on")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime publishedOn;
	@Column(name = "request_moderation")
	private Boolean requestModeration;
	private String title;
	@Transient
	@JsonInclude
	private Long views;
	@Enumerated(value = EnumType.STRING)
	private State state;

}
