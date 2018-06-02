package rmi.server.input;

import java.io.Serializable;

public class Order implements Serializable {
	private static final long serialVersionUID = 1L;

	private String identyfikatorKlienta;
	private String nazwaTowaru;
	private int ilo��Towaru;
	private String numerRachunkuBankowegoKlienta;
	
	public Order(String identyfikatorKlienta, String nazwaTowaru, int ilo��Towaru, String numerRachunkuBankowegoKlienta) {
		this.identyfikatorKlienta = identyfikatorKlienta;
		this.nazwaTowaru = nazwaTowaru;
		this.ilo��Towaru = ilo��Towaru;
		this.numerRachunkuBankowegoKlienta = numerRachunkuBankowegoKlienta;
	}
	
	public String getIdentyfikatorKlienta() {
		return identyfikatorKlienta;
	}
	
	public void setIdentyfikatorKlienta(String identyfikatorKlienta) {
		this.identyfikatorKlienta = identyfikatorKlienta;
	}
	
	public String getNazwaTowaru() {
		return nazwaTowaru;
	}

	public void setNazwaTowaru(String nazwaTowaru) {
		this.nazwaTowaru = nazwaTowaru;
	}

	public int getIlo��Towaru() {
		return ilo��Towaru;
	}

	public void setIlo��Towaru(int ilo��Towaru) {
		this.ilo��Towaru = ilo��Towaru;
	}

	public String getNumerRachunkuBankowegoKlienta() {
		return numerRachunkuBankowegoKlienta;
	}

	public void setNumerRachunkuBankowegoKlienta(String numerRachunkuBankowegoKlienta) {
		this.numerRachunkuBankowegoKlienta = numerRachunkuBankowegoKlienta;
	}
}