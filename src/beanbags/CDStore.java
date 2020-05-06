package beanbags;
import java.io.IOException;

/**
 * CDStore.
 * 
 * Implementation of CutDownStore interface.
 *
 * @author George Rogers
 * @version 1.0
 */

public class CDStore implements CutDownStore {
	
	private ObjectArrayList storeList = new ObjectArrayList();
	private ObjectArrayList soldList = new ObjectArrayList();
	
	/**
	 * Method to search ObjectArrayLists for a BeanBag object with the given ID.
	 * @param id ID of beanbag to search for
	 * @param list BeanBag object array to search
	 * @return <code>null</code> if beanbag does not exist, casted BeanBag object if beanbag does exist
	 */
	public BeanBag getBeanBagByID(String id, ObjectArrayList list) {
		for (int i=0; i<list.size(); i++) { //loop over ObjectArrayList
			BeanBag b = (BeanBag)list.get(i); //cast Object to BeanBag instance
			if (b.getID()==id) {
				return b;
			}
		}
		return null; //return null if no ID match found
	}
	
	/**
	 * Method to validate that ID is a positive, 8 char hexadecimal number.
	 * @param id ID to validate
	 * @return <code>true</code> if ID is valid, <code>false</code> if ID is not valid
	 */
	public boolean idIsValid(String id) {
		return id.matches("^[0-9a-fA-F]{8}$"); //regex test using hexadecimal characters and length check (8)
	}

	public void addBeanBags(int num, String manufacturer, String name, String id, short year, byte month)
			throws IllegalNumberOfBeanBagsAddedException, BeanBagMismatchException, IllegalIDException,
			InvalidMonthException {
		//input validation
		if (num<1) {
			throw new IllegalNumberOfBeanBagsAddedException();
		}
		if (month<1 || month>12) {
			throw new InvalidMonthException();
		}
		if (!idIsValid(id)) {
			throw new IllegalIDException();
		}
		BeanBag b = getBeanBagByID(id, storeList);
		BeanBag newBeanBag = new BeanBag(num, manufacturer, name, id); //init new BeanBag object with given data
		if (b == null) {
			storeList.add(newBeanBag); //add new beanbag if ID doesn't exist
		} else {
			if (b.isMismatched(newBeanBag)) {
				throw new BeanBagMismatchException();
			}
			b.setNum(b.getNum() + num); //increase existing stock levels if ID does exist
		}
	}

	public void addBeanBags(int num, String manufacturer, String name, String id, short year, byte month,
			String information) throws IllegalNumberOfBeanBagsAddedException, BeanBagMismatchException,
			IllegalIDException, InvalidMonthException {
		//input validation
		if (num<1) {
			throw new IllegalNumberOfBeanBagsAddedException();
		}
		if (month<1 || month>12) {
			throw new InvalidMonthException();
		}
		if (!idIsValid(id)) {
			throw new IllegalIDException();
		}
		BeanBag b = getBeanBagByID(id, storeList);
		BeanBag newBeanBag = new BeanBag(num, manufacturer, name, id, information); //init new BeanBag object with given data
		if (b == null) {
			storeList.add(newBeanBag); //add new beanbag if ID doesn't exit
		} else {
			if (b.isMismatched(newBeanBag)) {
				throw new BeanBagMismatchException();
			}
			b.setNum(b.getNum() + num); //increase existing stock levels if ID does exist
		}
	}

	public void setBeanBagPrice(String id, int priceInPence)
			throws InvalidPriceException, BeanBagIDNotRecognisedException, IllegalIDException {
		//input validation
		if (priceInPence < 1) {
			throw new InvalidPriceException();
		}
		if (!idIsValid(id)) {
			throw new IllegalIDException();
		}
		BeanBag b = getBeanBagByID(id, storeList);
		if (b == null) {
			throw new BeanBagIDNotRecognisedException();
		}
		b.setPrice(priceInPence); //use setter method to edit price
	}

	public void sellBeanBags(int num, String id)
			throws BeanBagNotInStockException, InsufficientStockException, IllegalNumberOfBeanBagsSoldException,
			PriceNotSetException, BeanBagIDNotRecognisedException, IllegalIDException {
		//input validation
		if (num < 1) {
			throw new IllegalNumberOfBeanBagsSoldException();
		}
		if (!idIsValid(id)) {
			throw new IllegalIDException();
		}
		BeanBag b = getBeanBagByID(id, storeList);
		if (b == null) {
			throw new BeanBagIDNotRecognisedException();
		}
		if (b.getNum() < num) {
			throw new InsufficientStockException();
		}
		int n = b.getNum();
		if (n < 1) {
			throw new BeanBagNotInStockException();
		}
		if (n < 0) {
			throw new PriceNotSetException();
		}
		b.setNum(n - num); //reduce stock levels
		BeanBag soldBeanBag = getBeanBagByID(id, soldList);
		if (soldBeanBag == null) {
			soldBeanBag = new BeanBag(num, b.getManufacturer(), b.getName(), id, b.getInfo()); //create new soldList object if none exists
		} else {
			soldBeanBag.setNum(soldBeanBag.getNum() + num); //edit soldList records if beanbag has already been sold in the past
		}
	}

	public int beanBagsInStock() {
		int total = 0;
		for (int i=0; i<storeList.size(); i++) { //loop over each BeanBag object and total num attribute
			BeanBag b = (BeanBag)storeList.get(i);
			total += b.getNum();
		}
		return total;
	}

	public int beanBagsInStock(String id) throws BeanBagIDNotRecognisedException, IllegalIDException {
		//input validation
		if (!idIsValid(id)) {
			throw new IllegalIDException();
		}
		BeanBag b = getBeanBagByID(id, storeList);
		if (b == null) { //check if ID exists
			throw new BeanBagIDNotRecognisedException();
		}
		return b.getNum();
	}

	public int getNumberOfDifferentBeanBagsInStock() {
		int total = 0;
		for (int i=0; i<storeList.size(); i++) { //iterate over each beanbag object and check if they have stock
			BeanBag b = (BeanBag)storeList.get(i);
			if (b.getNum() > 0) {
				total++; //increment total if beanbag has stock
			}
		}
		return total;
	}

	public int getNumberOfSoldBeanBags() {
		int total = 0;
		for (int i=0; i<soldList.size(); i++) {
			BeanBag b = (BeanBag)soldList.get(i);
			total += b.getNum(); //iterate over soldList and find sum of num attributes of BeanBag objects
		}
		return total;
	}

	public int getNumberOfSoldBeanBags(String id) throws BeanBagIDNotRecognisedException, IllegalIDException {
		//input validation
		if (!idIsValid(id)) {
			throw new IllegalIDException();
		}
		BeanBag b = getBeanBagByID(id, soldList);
		if (b == null) { //check ID exists 
			throw new BeanBagIDNotRecognisedException();
		}
		return b.getNum();
	}

	public int getTotalPriceOfSoldBeanBags() {
		int total = 0;
		for (int i=0; i<soldList.size(); i++) {
			BeanBag b = (BeanBag)soldList.get(i);
			total += b.getNum() * b.getPrice(); //multiply number sold by price to find total value sold for
		}
		return total;
	}

	public int getTotalPriceOfSoldBeanBags(String id) throws BeanBagIDNotRecognisedException, IllegalIDException {
		if (!idIsValid(id)) {
			throw new IllegalIDException();
		}
		BeanBag b = getBeanBagByID(id, soldList);
		if (b == null) {
			throw new BeanBagIDNotRecognisedException();
		}
		return b.getNum() * b.getPrice(); //multiply number sold by price to find total value sold for
	}

	public String getBeanBagDetails(String id) throws BeanBagIDNotRecognisedException, IllegalIDException {
		String i = getBeanBagByID(id, storeList).getInfo();
		if (i.isEmpty() || i == null || i.isBlank()) { //return empty string if info is blank or is empty
			return "";
		} else {
			return i; //return information string
		}
	}

	public void empty() {
		//init new ObjectArrayLists to wipe old ones
		storeList = new ObjectArrayList();
		soldList = new ObjectArrayList();
	}

	public void resetSaleAndCostTracking() {
		//init new ObjectArrayList to wipe old one
		soldList = new ObjectArrayList();
	}

	public void replace(String oldId, String replacementId) throws BeanBagIDNotRecognisedException, IllegalIDException {
		//input validation
		if (!idIsValid(replacementId)) {
			throw new IllegalIDException();
		}
		BeanBag stockBeanBag = getBeanBagByID(oldId, storeList);
		if (stockBeanBag == null) {
			throw new BeanBagIDNotRecognisedException();
		}
		stockBeanBag.setID(replacementId); //update ID with setter method
		BeanBag soldBeanBag = getBeanBagByID(oldId, soldList);
		if (soldBeanBag != null) { //check if ID exists in soldList and should be updated
			soldBeanBag.setID(replacementId);
		}
	}
}
