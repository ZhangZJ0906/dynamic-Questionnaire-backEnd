package com.example.quiz_1150119.constants;

public enum Type {
	SINGLE("SINGLE"), MUTI("MUTI"), //
	TEXT("TEXT");

	private String type;

	private Type(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public static boolean check(String input) {
		/* values(): 會取出 enum 中所有的列舉 */
		for (Type item : values()) {
			if (input.equalsIgnoreCase(item.getType())) {
				return true;
			}

		}

//		if (input.equalsIgnoreCase(Type.SINGLE.getType()) || input.equalsIgnoreCase(Type.MUTI.getType())
//				|| input.equalsIgnoreCase(Type.TEXT.getType())) {
//			return true;
//
//		}
		return false;
	}
	
	public static boolean isChoice(String input) {
		if (input.equalsIgnoreCase(Type.SINGLE.getType()) || input.equalsIgnoreCase(Type.MUTI.getType())
		) {
			return true;

		}
		return false;
	}
}
