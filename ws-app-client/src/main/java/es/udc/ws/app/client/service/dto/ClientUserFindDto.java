package es.udc.ws.app.client.service.dto;

import java.time.LocalDateTime;

public class ClientUserFindDto {
	
	private Long id_show;
	private String name;
	private String description;
	private LocalDateTime date_time_show;
	private LocalDateTime date_time_show_finish;
	private LocalDateTime date_time_lim;
	private int max_tickets;
	private float real_price;
	private float discounted_price;
	
	
	public ClientUserFindDto(Long id_show, String name, String description, LocalDateTime date_time_show,
			LocalDateTime date_time_show_finish, LocalDateTime date_time_lim, int max_tickets, float real_price,
			float discounted_price) {
		super();
		this.id_show = id_show;
		this.name = name;
		this.description = description;
		this.date_time_show = date_time_show;
		this.date_time_show_finish = date_time_show_finish;
		this.date_time_lim = date_time_lim;
		this.max_tickets = max_tickets;
		this.real_price = real_price;
		this.discounted_price = discounted_price;
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


	public LocalDateTime getDate_time_show_finish() {
		return date_time_show_finish;
	}


	public void setDate_time_show_finish(LocalDateTime date_time_show_finish) {
		this.date_time_show_finish = date_time_show_finish;
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
	
	
	
	
	
}