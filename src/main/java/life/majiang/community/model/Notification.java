package life.majiang.community.model;

import lombok.Data;

@Data
public class Notification {

    private Long id;

    private Long notifier;

    private Long receiver;

    private Long outerid;

    private Integer type;

    private Long gmt_Create;

    private Integer status;

    private String notifier_Name;

    private String outer_Title;

}