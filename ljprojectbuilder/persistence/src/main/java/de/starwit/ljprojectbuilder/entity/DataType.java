package de.starwit.ljprojectbuilder.entity;

public enum DataType {

	String("string"),
	Integer("int"),
	Double("double"),
	Date("date"),
	Time("time"),
	Timestamp("timestamp"),
	BigDecimal("bigdecimal"),
	Enum("enum");
	
	private String translationKey;
	
	DataType(String translationKey) {
		this.translationKey = translationKey;
	}

	public String getTranslationKey() {
		return translationKey;
	}

}
