package common;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;

import server.ParkingGarageImpl;

public class ExitGate {

	private String gateName;
	private ParkingGarageImpl garage;

	public ExitGate(String name, ParkingGarageImpl garage)
	{
		this.gateName = name;
		this.garage = garage;
	}

	public String getGateName()
	{
		return gateName;
	}

	/**
	 * Checks if the ticket hasn't been paid for and if it has pays for the ticket
	 * @param ticket Ticket's payment status to be checked
	 */
	public void payForTicket(Ticket ticket)
	{
		if( !ticket.getPaymentStatus() )
		{
			ticket.setPaymentStatus(true);
		}

	}

	/**
	 * Attempts to pay for ticket and starts checkout process
	 * @param ticketID uniqueID of ticket to be found
	 * @return boolean true if car checked out, false otherwise
	 */
	public boolean attemptCheckoutCar(String ticketID)
	{
		Ticket ticket = findTicket(ticketID);
		if( ticket != null)
		{
			if( ticket.getPaymentStatus() )
			{
				payForTicket( ticket );
			}
			return true;
		}
		return false;
	}

	/**
	 * Searches garage for the ticket
	 * @param ticketID uniqueID to be found
	 * @return t Ticket if found otherwise null
	 */
	private Ticket findTicket(String ticketID)
	{
		HashSet<Ticket> tickets = garage.getTickets();
		Ticket t = null;
		for(Ticket ticket : tickets)
		{
			if( ticket.getUniqueID().equals(ticketID) )
			{
				t = ticket;
				break;
			}
		}

		return t;
	}

	/**
	 * Removes the car from the garage system while recording exit and payment
	 * @param ticketID uniqueID to be found
	 * @param payment payment the car used
	 */
	public void removeCarFromGarage(String ticketID, Payment payment)
	{
		RecordManager records = garage.getRecordManager();
		Ticket t = findTicket(ticketID);		
		records.addOccupationRecord(payment.getDateOfPayment(), CarStatus.LEAVE);
		records.addFinancialRecord(t, payment);
		garage.removeCarFromGarage(t);
	}

	/**
	 * Calculates the amount needed to pay for parking
	 * @param ticketID uniqueID to be found
	 * @return double amount due for parking
	 */
	public double amountDueOnTicket(String ticketID) 
	{
		Ticket t = findTicket(ticketID);
		LocalDateTime ldt = LocalDateTime.now();
		LocalDateTime tempDateTime = LocalDateTime.from( t.getCheckinTime() );
		double amountDue = tempDateTime.until( ldt, ChronoUnit.HOURS) + 1.00;


		return amountDue;
	}

}
