package no.hvl.dat108.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(schema="spill", name="car")
public class Car {
	@Id 
	@SequenceGenerator(
		    name="carid_seq",
		    sequenceName="spill.carid_sequence",
		    allocationSize=1
		)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="carid_seq")
	@Column(name="carid", unique=true)
	private Integer carid;
	@OneToOne(cascade = CascadeType.PERSIST, fetch=FetchType.LAZY, mappedBy="car", orphanRemoval=true)
	private Alias owner;
	
	public Car() {
		// TODO Add some values that can be used to make this table useful later
	}
	
	public Car(Alias owner) {
		this.owner = owner;
	}

	public Integer getCarid() {
		return carid;
	}
	
	public Alias getOwner() {
		return owner;
	}
	
}
