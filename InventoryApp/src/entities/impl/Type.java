package entities.impl;
// Generated 16-Aug-2017 3:49:11 AM by Hibernate Tools 5.2.0.CR1

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import dao.interf.IEntity;

/**
 * Type generated by hbm2java
 */
@Entity
@Table(name = "type", schema = "public")
public class Type implements java.io.Serializable,IEntity  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private List<Product> products = null;
	public Type() {
	}

	public Type(int id) {
		this.id = id;
	}

	public Type(int id, String name, List<Product> products) {
		this.id = id;
		this.name = name;
		this.products = products;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Override
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

/*	@OneToMany(fetch = FetchType.LAZY, mappedBy = "type")
	public List<Product> getProducts() {
		return this.products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}*/

}
