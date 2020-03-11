package life.majiang.community.model;

import lombok.Data;

@Data
public class Comment {

    private Long id;

    private Long parent_Id;

    private Integer type;

    private Long commentator;

    private Long gmt_Create;

    private Long gmt_Modified;

    private Long like_Count;

    private String content;

    private Integer comment_Count;

}