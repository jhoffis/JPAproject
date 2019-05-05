package no.hvl.dat108.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(schema="spill", name="alias")
public class Alias {
	@Id 
	@SequenceGenerator(
		    name="aliasid_seq",
		    sequenceName="spill.aliasid_sequence",
		    allocationSize=1
		)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="aliasid_seq")
	@Column(name="aliasid", unique=true)
	private Integer aliasid;
	
	@ManyToOne(cascade = CascadeType.PERSIST, fetch=FetchType.LAZY)
	@JoinColumn(name="competitorusername")
	private Competitor competitor;
	
	private String name;
	private Integer levelsbeaten;
	
	@OneToOne(cascade = CascadeType.PERSIST, fetch=FetchType.LAZY) 
	@JoinColumn(name="carid")
	private Car car;
	
	public Alias() {
		car = new Car(this);
		levelsbeaten = 0;
	}
	
	public Alias(Competitor competitor, String name, int levelsbeaten) {
		this.name = name;
		this.competitor = competitor;
		this.levelsbeaten = levelsbeaten;
		car = new Car(this);
	}
	

	public Competitor getCompetitor() {
		return competitor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public Integer getAliasid() {
		return aliasid;
	}
	
	public void setLevelsBeaten(Integer i) {
		levelsbeaten = i;
	}
	
	public Integer getLevelsBeaten() {
		return levelsbeaten;
	}
	
}
