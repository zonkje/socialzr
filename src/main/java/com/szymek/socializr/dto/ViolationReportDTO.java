package com.szymek.socializr.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class ViolationReportDTO extends TextWidgetDTO {

    Long reportedUserId;

}
