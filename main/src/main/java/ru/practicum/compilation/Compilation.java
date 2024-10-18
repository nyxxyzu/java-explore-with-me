package ru.practicum.compilation;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.event.model.Event;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "compilations")
public class Compilation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToMany
	@ToString.Exclude
	@JoinTable(name = "compilation_event",
	           joinColumns = { @JoinColumn(name = "compilation_id")},
	           inverseJoinColumns = { @JoinColumn(name = "event_id") })
	private List<Event> events;
	private Boolean pinned;
	private String title;
}
