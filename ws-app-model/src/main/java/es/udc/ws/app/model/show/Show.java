package es.udc.ws.app.model.show;

import java.time.LocalDateTime;

public class Show {
    
    private Long id_show;
    private String name;
    private String description;
    private LocalDateTime date_time_show;
    private int duration;
    private LocalDateTime date_time_lim;
    private int max_tickets;
    private float real_price;
    private float discounted_price;
    private double commission_sale;
    private int remaining_tickets;
    
    
    public Show(String name, String description, int duration,
            LocalDateTime date_time_lim, int max_tickets, float real_price, float discounted_price, double commission_sale,
            int remaining_tickets) {
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.date_time_lim= date_time_lim;
        this.max_tickets = max_tickets;
        this.real_price = real_price;
        this.discounted_price = discounted_price;
        this.commission_sale = commission_sale;
        this.remaining_tickets = remaining_tickets;
    }



    public Show(Long id_show, String name, String description, int duration, LocalDateTime date_time_lim,
            int max_tickets, float real_price, float discounted_price, double commission_sale, int remaining_tickets) {
        this(name,description,duration,date_time_lim,max_tickets,real_price,discounted_price,commission_sale,remaining_tickets);
        this.id_show = id_show;
    } 
    
    public Show(Long id_show, String name, String description,int duration, LocalDateTime date_time_lim,
            int max_tickets, float real_price, float discounted_price, double commission_sale, int remaining_tickets,LocalDateTime date_time_show) {
        this(id_show,name,description,duration,date_time_lim,max_tickets,real_price,discounted_price,commission_sale,remaining_tickets);
        this.date_time_show = date_time_show;
        if (date_time_show != null) {
            this.date_time_show.withNano(0);
        }
        
    }

	public Show(Long id_show, String name, String description, int duration, LocalDateTime date_time_lim,
			int max_tickets,float real_price, float discounted_price, LocalDateTime date_time_show) {
		this.id_show = id_show;
		this.name = name;
		this.description = description;
		this.duration = duration;
		this.date_time_lim = date_time_lim;
		this.max_tickets = max_tickets;
		this.real_price = real_price;
		this.discounted_price = discounted_price;
		this.date_time_show = date_time_show;
	}

	public Show(Long id_show, String name, String description, int duration, LocalDateTime date_time_lim,
			float real_price, float discounted_price, int remaining_tickets, LocalDateTime date_time_show) {
		this.id_show = id_show;
		this.name = name;
		this.description = description;
		this.duration = duration;
		this.date_time_lim= date_time_lim;
		this.real_price = real_price;
		this.discounted_price = discounted_price;
		this.remaining_tickets = remaining_tickets;
		this.date_time_show = date_time_show;
	}
	

    public Show(Long id_show, String name, String description, int duration, LocalDateTime date_time_lim,
            int max_tickets,float real_price, float discounted_price, int remaining_tickets, LocalDateTime date_time_show) {
        this.id_show = id_show;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.date_time_lim= date_time_lim;
        this.max_tickets = max_tickets;
        this.real_price = real_price;
        this.discounted_price = discounted_price;
        this.remaining_tickets = remaining_tickets;
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
        if (date_time_show != null) {
            this.date_time_show.withNano(0);
        }
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
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Show other = (Show) obj;
        if (Double.doubleToLongBits(commission_sale) != Double.doubleToLongBits(other.commission_sale))
            return false;
        if (date_time_lim == null) {
            if (other.date_time_lim != null)
                return false;
        } else if (!date_time_lim.equals(other.date_time_lim))
            return false;
        if (date_time_show == null) {
            if (other.date_time_show != null)
                return false;
        } else if (!date_time_show.equals(other.date_time_show))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (Float.floatToIntBits(discounted_price) != Float.floatToIntBits(other.discounted_price))
            return false;
        if (duration != other.duration)
            return false;
        if (id_show == null) {
            if (other.id_show != null)
                return false;
        } else if (!id_show.equals(other.id_show))
            return false;
        if (max_tickets != other.max_tickets)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (Float.floatToIntBits(real_price) != Float.floatToIntBits(other.real_price))
            return false;
        if (remaining_tickets != other.remaining_tickets)
            return false;
        return true;
    }
}
