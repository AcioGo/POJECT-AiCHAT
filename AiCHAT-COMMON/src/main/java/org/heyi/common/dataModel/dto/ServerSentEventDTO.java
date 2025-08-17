package org.heyi.common.dataModel.dto;

import lombok.Data;

@Data
public class ServerSentEventDTO {
    private String id;
    private Long retry;
    private String type;
    private String data;
}
