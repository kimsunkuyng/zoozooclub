package com.zoozooclub.admin.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Attachment {

	private int attmId;
	private int refProductNo;
	private String originalName;
	private String renameName;
	private String attmPath;
	private int attmLevel;
	private String attmStatus;
}
