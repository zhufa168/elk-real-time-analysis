package com.ruoyi.activiti.domain.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Jayden cxp
 * date 2021-08-27
 */
@Data
public class HistoryDataDTO implements Serializable {
	private String taskNodeName;
	private String createName;
	private String createdDate;
	private List<HistoryFormDataDTO> formHistoryDataDTO;
}
