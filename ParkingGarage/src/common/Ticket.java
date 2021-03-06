package common;

import java.time.LocalDateTime;

public class Ticket {
	
	private LocalDateTime checkinTime;
	private String uniqueID;
	private boolean paymentStatus = false;
	
	
	public Ticket(LocalDateTime checkinTime)
	{
		this.checkinTime = checkinTime;
		// Trim off all non numeric characters
		uniqueID = checkinTime.toString().replaceAll("[^0-9]", "");
		// Trim off the milliseconds
		uniqueID = uniqueID.substring(0, uniqueID.length()-3);
	}
	
	public LocalDateTime getCheckinTime()
	{
		return checkinTime;
	}

	public String getUniqueID()
	{
		return uniqueID;
	}
	
	public boolean getPaymentStatus() 
	{
		return paymentStatus;
	}

	public void setPaymentStatus(boolean paymentStatus) 
	{
		this.paymentStatus = paymentStatus;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if(!(obj == null) && obj instanceof Ticket) 
		{
			Ticket tic = (Ticket)obj;
			return this.checkinTime.equals(tic.getCheckinTime());
		}
		return false;
	}
	
	@Override
	public String toString()
	{
		return checkinTime.toString();
	}
}
