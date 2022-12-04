package vn.edu.fpt.notification.dto.request.news;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data       
@Builder
public class CreateNewsRequest implements Serializable {

    private static final long serialVersionUID = -2631961108798301072L;
    private String title;
    private String content;
    private MultipartFile thumbnail;
}
