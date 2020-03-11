package life.majiang.community.dto;

import lombok.Data;

/**
 * Created by codedrinker on 2019/6/14.
 */
@Data
public class NotificationDTO {
    private Long id;

    private Long notifier;

    private Long outerid;

    private Integer type;

    private Long gmt_Create;

    private Integer status;

    private String notifier_Name;

    private String outer_Title;

    private String typeName;

}
