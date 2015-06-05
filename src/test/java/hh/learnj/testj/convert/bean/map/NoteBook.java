package hh.learnj.testj.convert.bean.map;

public class NoteBook {
	
	private double numberOfSheets;
	private String description;

	public NoteBook(double numberOfSheets, String description) {
		super();
		this.numberOfSheets = numberOfSheets;
		this.description = description;
	}

	public double getNumberOfSheets() {
		return numberOfSheets;
	}

	public String getDescription() {
		return description;
	}
}
