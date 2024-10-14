package ru.practicum.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "events")
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String annotation;
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	@Column(name = "confirmed_requests")
	private Long confirmedRequests;
	@Column(name = "created_on")
	private LocalDateTime createdOn;
	private String description;
	@Column(name = "event_date")
	private LocalDateTime eventDate;
	@ManyToOne
	@JoinColumn(name = "initiator_id")
	private User initiator;
	@OneToOne
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
	private Long views;
	@Enumerated(value = EnumType.STRING)
	private State state;

}
