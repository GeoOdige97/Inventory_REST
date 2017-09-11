package tools;

public enum StatusEnum {
	
	GOOD("Good"),REPAIR("Repair"),LOST("Lost"),BROKEN("Broken");
	private String name;
	private StatusEnum(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
}
