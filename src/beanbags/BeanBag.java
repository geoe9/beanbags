package beanbags;
import java.io.IOException;

public class BeanBag {
	
	//init BeanBag attributes
	private int num, price;
	private String manufacturer, name, id, information;
	
	/**
	 * Creates default instance of BeanBag.
	 */
	public BeanBag() {
		num=0;price=-1;manufacturer="Default";name="Default";id="00000000";information="";
	}

	/**
	 * Creates instance of BeanBag with given attribute values.
	 * No information attribute value given.
	 * @param num Number of beanbags
	 * @param manufacturer Manufacturer of beanbag
	 * @param name Name of beanbag
	 * @param id Beanbag ID (hexadecimal)
	 */
	public BeanBag(int num, String manufacturer, String name, String id) {
		this.num = num;
		this.manufacturer = manufacturer;
		this.name = name;
		this.id = id;
		this.information = "";
		this.price = -1; //price not set
	}
	
	/**
	 * Overloaded constructor.
	 * Creates instance of BeanBag with given attribute values.
	 * Information attribute given.
	 * @param num Number of beanbags
	 * @param manufacturer Manufacturer of beanbag
	 * @param name Name of beanbag
	 * @param id Beanbag ID (hexadecimal)
	 * @param information Additional (free-text) information of beanbag object
	 */
	public BeanBag(int num, String manufacturer, String name, String id, String information) {
		this.num = num;
		this.manufacturer = manufacturer;
		this.name = name;
		this.id = id;
		this.information = information;
		this.price = -1; //price not set
	}
	/**
	 * Getter method for price of beanbag.
	 * @return Price of beanbag
	 */
	public int getPrice() {
		return this.price;
	}
	/**
	 * Setter method for price of beanbag.
	 * @param p Value in pence to set price of beanbag to
	 */
	public void setPrice(int p) {
		this.price = p;
	}
	
	/**
	 * Getter method for number of beanbags.
	 * @return Number of beanbags
	 */
	public int getNum() {
		return this.num;
	}
	
	/**
	 * Setter method for number of beanbags.
	 * @param n Number of beanbags
	 */
	public void setNum(int n) {
		this.num = n;
	}
	
	/**
	 * Getter method for ID of beanbag.
	 * @return ID of beanbag
	 */
	public String getID() {
		return this.id;
	}
	
	/**
	 * Setter method for ID if beanbag.
	 * @param id Hexadecimal value to set ID of beanbag to
	 */
	public void setID(String id) {
		this.id = id;
	}
	
	/**
	 * Getter method for manufacturer of beanbag.
	 * @return Manufacturer of beanbag
	 */
	public String getManufacturer() {
		return this.manufacturer;
	}
	
	/**
	 * Getter method for name of beanbag.
	 * @return Name of beanbag
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Getter method for beanbag information field.
	 * @return Information (free-text) field of beanbag
	 */
	public String getInfo() {
		return this.information;
	}
	
	/**
	 * Method checks if beanbags have different values for
	 * manufacturer, name and information.
	 * 
	 * @param b Beanbag to compare to
	 * 
	 * @return <code>true</code> if a mismatch has occurred
	 */
	public boolean isMismatched(BeanBag b) {
		//boolean expression comparing beanbag attributes
		return !((this.information == b.information) && (this.manufacturer == b.manufacturer) && (this.name == b.name));
	}

}
