package vn.edu.fpt.notification.dto.response.news;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 04/12/2022 - 14:43
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GetNewsResponse implements Serializable {

    private static final long serialVersionUID = 873530419794713812L;
    private String newsId;
    private String title;
    private String author;
    private String thumbnail;
    private LocalDateTime createdDate;
    private Integer views;
    private Integer comments;
}
