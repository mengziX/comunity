package life.majiang.community.dto;

import life.majiang.community.model.User;
import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    private Long parent_Id;
    private Integer type;
    private Long commentator;
    private Long gmt_Create;
    private Long gmt_Modified;
    private Long like_Count;
    private Integer commentCount;
    private String content;
    private User user;
}
