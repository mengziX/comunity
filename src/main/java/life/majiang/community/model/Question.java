package life.majiang.community.model;

import lombok.Data;

@Data
public class Question {

    private Long id;

    private String title;

    private Long gmt_Create;

    private Long gmt_Modified;

    private Long creator;

    private Integer comment_Count;

    private Integer view_Count;

    private Integer like_Count;

    private String tag;

    private String description;


}
