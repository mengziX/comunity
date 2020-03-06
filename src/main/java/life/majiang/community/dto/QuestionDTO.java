package life.majiang.community.dto;

import life.majiang.community.model.User;
import lombok.Data;

import java.util.List;

@Data
public class QuestionDTO {

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

    private User user;
}
