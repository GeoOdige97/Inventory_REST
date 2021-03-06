package entities.impl;
// Generated 16-Aug-2017 3:49:11 AM by Hibernate Tools 5.2.0.CR1

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import dao.interf.IEntity;

/**
 * Product generated by hbm2java
 */
@Entity
@Table(name = "product", schema = "public")
public class Product implements java.io.Serializable,IEntity  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private Type type;
	private String name;
	private String model;
	private String company;
	private Integer productionYear;
	private List<Device> devices = null;
	private List<Purchase> purchases = null;
	private List<Stock> stocks = null;

	public Product() {
	}

	public Product(int id) {
		this.id = id;
	}

	public Product(int id, Type type, String name, String model, String company, Integer productionYear,
			List<Device> devices, List<Purchase> purchases, List<Stock> stocks) {
		this.id = id;
		this.type = type;
		this.name = name;
		this.model = model;
		this.company = company;
		this.productionYear = productionYear;
		this.devices = devices;
		this.purchases = purchases;
		this.stocks = stocks;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type_id")
	public Type getType() {
		return this.type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	@Column(name = "name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "model")
	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Column(name = "company")
	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@Column(name = "production_year")
	public Integer getProductionYear() {
		return this.productionYear;
	}

	public void setProductionYear(Integer productionYear) {
		this.productionYear = productionYear;
	}

/*	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
	public List<Device> getDevices() {
		return this.devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
	public List<Purchase> getPurchases() {
		return this.purchases;
	}

	public void setPurchases(List<Purchase> purchases) {
		this.purchases = purchases;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
	public List<Stock> getStocks() {
		return this.stocks;
	}

	public void setStocks(List<Stock> stocks) {
		this.stocks = stocks;
	}*/

}
