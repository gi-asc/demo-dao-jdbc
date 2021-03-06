package model.entities;

import java.io.Serializable;

public class Departament implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	
	public Departament() {
		
	}

	public Departament(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String string) {
		this.name = string;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Departament other = (Departament) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Departament [id=" + id + ", name=" + name + "]";
	}
	
}
