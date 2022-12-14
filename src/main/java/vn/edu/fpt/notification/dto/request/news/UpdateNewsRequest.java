package vn.edu.fpt.notification.dto.request.news;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.notification.dto.request.comment.CreateFileRequest;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateNewsRequest implements Serializable {

    private static final long serialVersionUID = 4130017257555284706L;
    private String title;
    private String content;
    private CreateFileRequest thumbnail;

}
