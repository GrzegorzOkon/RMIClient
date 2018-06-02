package rmi.server.input;

import java.io.Serializable;

public class Order implements Serializable {
	private static final long serialVersionUID = 1L;

	private String identyfikatorKlienta;
	private String nazwaTowaru;
	private int iloœæTowaru;
	private String numerRachunkuBankowegoKlienta;
	
	public Order(String identyfikatorKlienta, String nazwaTowaru, int iloœæTowaru, String numerRachunkuBankowegoKlienta) {
		this.identyfikatorKlienta = identyfikatorKlienta;
		this.nazwaTowaru = nazwaTowaru;
		this.iloœæTowaru = iloœæTowaru;
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

	public int getIloœæTowaru() {
		return iloœæTowaru;
	}

	public void setIloœæTowaru(int iloœæTowaru) {
		this.iloœæTowaru = iloœæTowaru;
	}

	public String getNumerRachunkuBankowegoKlienta() {
		return numerRachunkuBankowegoKlienta;
	}

	public void setNumerRachunkuBankowegoKlienta(String numerRachunkuBankowegoKlienta) {
		this.numerRachunkuBankowegoKlienta = numerRachunkuBankowegoKlienta;
	}
}