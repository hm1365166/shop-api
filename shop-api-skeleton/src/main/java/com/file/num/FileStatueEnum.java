package com.file.num;

public enum FileStatueEnum {

	SUCCESS("SUCCESS", 0, ""),
	UNAVAILABLE("AVIABLE",-1,"文件不可取");


	private final String name;
	private final int value;
	private final String remark;

	FileStatueEnum(String name, int value, String remark) {
		this.name = name;
		this.value = value;
		this.remark = remark;
	}

	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}

	public String getRemark() {
		return remark;
	}

	public static final FileStatueEnum fromValue(int value) {
		for (FileStatueEnum e : values()) {
			if (value == e.getValue()) {
				return e;
			}
		}
		return null;
	}

	public static final FileStatueEnum fromName(String name) {
		for (FileStatueEnum e : values()) {
			if (name == e.getName()) {
				return e;
			}
		}
		return null;
	}
}
