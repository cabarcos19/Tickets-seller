package es.udc.ws.app.service.dto;

import java.time.LocalDateTime;

public class ServiceFindDto {
	private Long id_show;
	private String name;
	private String description;
	private LocalDateTime date_time_show;
	private int duration;
	private LocalDateTime date_time_lim;
	private int max_tickets;
	private float real_price;
	private float discounted_price;
	
	public ServiceFindDto(Long id_show, String name, String description, int duration,LocalDateTime date_time_lim, 
			int max_tickets, float real_price, float discounted_price,LocalDateTime date_time_show) {
		//this.id_show = id_show;
		this.name = name;
		this.description = description;
		this.duration = duration;
		this.date_time_lim = date_time_lim;
		this.max_tickets = max_tickets;
		this.real_price = real_price;
		this.discounted_price = discounted_price;
		this.date_time_show = date_time_show;
	}
	public Long getId_show() {
		return id_show;
	}
	public void setId_show(Long id_show) {
		this.id_show = id_show;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDateTime getDate_time_show() {
		return date_time_show;
	}
	public void setDate_time_show(LocalDateTime date_time_show) {
		this.date_time_show = date_time_show;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public LocalDateTime getDate_time_lim() {
		return date_time_lim;
	}
	public void setDate_time_lim(LocalDateTime date_time_lim) {
		this.date_time_lim = date_time_lim;
	}
	public int getMax_tickets() {
		return max_tickets;
	}
	public void setMax_tickets(int max_tickets) {
		this.max_tickets = max_tickets;
	}
	public float getReal_price() {
		return real_price;
	}
	public void setReal_price(float real_price) {
		this.real_price = real_price;
	}
	public float getDiscounted_price() {
		return discounted_price;
	}
	public void setDiscounted_price(float discounted_price) {
		this.discounted_price = discounted_price;
	}
	@Override
	public String toString() {
		return "ServiceFindDto [id_show=" + id_show + ", name=" + name + ", description=" + description
				+ ", date_time_show=" + date_time_show + ", duration=" + duration + ", date_time_lim=" + date_time_lim
				+ ", max_tickets=" + max_tickets + ", real_price=" + real_price + ", discounted_price="
				+ discounted_price + "]";
	}
	
	

}
