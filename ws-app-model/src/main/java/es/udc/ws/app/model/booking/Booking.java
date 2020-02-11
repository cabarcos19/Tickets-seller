package es.udc.ws.app.model.booking;

import java.time.LocalDateTime;

public class Booking {
	private long id_booking;
	private String email;
	private String credit_card;
	private LocalDateTime date_time_book; 
	private long id_show;
	private float discounted_price;
	private boolean used;
	private int num_tickets;
	
	public Booking(String email, String credit_card, LocalDateTime date_time_book, long id_show, float discounted_price,
			boolean used,int num_tickets) {
		this.email = email;
		this.credit_card = credit_card;
		this.date_time_book = date_time_book;
		this.id_show = id_show;
		this.discounted_price = discounted_price;
		this.used = used;
		this.num_tickets = num_tickets;
	}
	
	public Booking(long id_booking,String email, String credit_card, LocalDateTime date_time_book, long id_show, float discounted_price,
			boolean used,int num_tickets) {
		this(email,credit_card,date_time_book,id_show,discounted_price,used,num_tickets);
		this.id_booking=id_booking;
	}

	public long getId_booking() {
		return id_booking;
	}

	public void setId_booking(long id_booking) {
		this.id_booking = id_booking;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCredit_card() {
		return credit_card;
	}

	public void setCredit_card(String credit_card) {
		this.credit_card = credit_card;
	}

	public LocalDateTime getDate_time_book() {
		return date_time_book;
	}

	public void setDate_time_book(LocalDateTime date_time_book) {
		this.date_time_book = date_time_book;
	}

	public long getId_show() {
		return id_show;
	}

	public void setId_show(long id_show) {
		this.id_show = id_show;
	}

	public float getDiscounted_price() {
		return discounted_price;
	}

	public void setDiscounted_price(float discounted_price) {
		this.discounted_price = discounted_price;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public int getNum_tickets() {
		return num_tickets;
	}

	public void setNum_tickets(int num_tickets) {
		this.num_tickets = num_tickets;
	}	
	
	
	
	
}
