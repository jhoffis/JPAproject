package no.hvl.dat108.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(schema="spill", name="competitor")
public class Competitor{
	@Id
	private String username;
	private String email;
	private String password;
	
	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "competitor", orphanRemoval=true)
	private List<Alias> aliases;
	
	public Competitor() {
		aliases = new ArrayList<Alias>();
	}
	
	public Competitor(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
		aliases = new ArrayList<Alias>();
	}

	public List<Alias> getAliasList(){
		return aliases;
	}
	
	public void addAlias(Alias alias) {
		aliases.add(alias);
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "Competitor [username=" + username + ", email=" + email + ", password=" + password + "]";
	}
}
