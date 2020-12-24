package DBTool;

public class Goods {

	private int gid;
	private String name;
	private float price;
	private int quantity;
	private int number;
	private String link;

	public void setGid(int gid) {
		this.gid = gid;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	
	public void setLink(String link) {
		this.link = link;
	}

	public int getGid() {
		return gid;
	}

	public String getName() {
		return name;
	}

	public float getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}
	
	public int getNumber() {
		return number;
	}

	public String getLink() {
		return link;
	}
}
