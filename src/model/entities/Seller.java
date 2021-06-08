package model.entities;

import java.io.Serializable;
import java.util.Date;

public class Seller implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private String nome;
	private String email;
	private Date aniversario;
	private double salario;
	
	private Departament departament;
	
	public Seller() {
		
	}
	
	public Seller(int id, String nome, String email, Date aniversario, double salario, Departament departament) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.aniversario = aniversario;
		this.salario = salario;
		this.departament = departament;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getAniversario() {
		return aniversario;
	}

	public void setAniversario(Date aniversario) {
		this.aniversario = aniversario;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}

	public Departament getDepartament() {
		return departament;
	}

	public void setDepartament(Departament departament) {
		this.departament = departament;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Seller other = (Seller) obj;
		if (id != other.id)
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Seller [id=" + id + ", nome=" + nome + ", email=" + email + ", aniversario=" + aniversario
				+ ", salario=" + salario + ", departament=" + departament + "]";
	}
	
}
