package es.udc.ws.app.service.dto;

import java.time.LocalDateTime;
import es.udc.ws.app.service.soapservice.LocalDateTimeAdapter;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceShowDto {
	private Long id_show;
	private String name;
	private String description; 
	@XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
	private LocalDateTime date_time_show;	
	private int duration; 
	@XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
	private LocalDateTime date_time_lim;
	private int max_tickets;
	private float real_price;
	private float discounted_price;
	private double commission_sale;
	private int remaining_tickets;
	
	public ServiceShowDto() {

	}
	
	public ServiceShowDto(Long id_show, String name, String description, LocalDateTime date_time_show, int duration,
			LocalDateTime date_time_lim, int max_tickets, float real_price, float discounted_price,
			double commission_sale, int remaining_tickets) {
		this.id_show = id_show;
		this.name = name;
		this.description = description;
		this.date_time_show = date_time_show;
		this.duration = duration;
		this.date_time_lim = date_time_lim;
		this.max_tickets = max_tickets;
		this.real_price = real_price;
		this.discounted_price = discounted_price;
		this.commission_sale = commission_sale;
		this.remaining_tickets = remaining_tickets;
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

	public double getCommission_sale() {
		return commission_sale;
	}

	public void setCommission_sale(double commission_sale) {
		this.commission_sale = commission_sale;
	}

	public int getRemaining_tickets() {
		return remaining_tickets;
	}

	public void setRemaining_tickets(int remaining_tickets) {
		this.remaining_tickets = remaining_tickets;
	}

	@Override
	public String toString() {
		return "ServiceShowDto [id_show=" + id_show + ", name=" + name + ", description=" + description
				+ ", date_time_show=" + date_time_show + ", duration=" + duration + ", date_time_lim=" + date_time_lim
				+ ", max_tickets=" + max_tickets + ", real_price=" + real_price + ", discounted_price="
				+ discounted_price + ", commission_sale=" + commission_sale + ", remaining_tickets=" + remaining_tickets
				+ "]";
	}




	
}
