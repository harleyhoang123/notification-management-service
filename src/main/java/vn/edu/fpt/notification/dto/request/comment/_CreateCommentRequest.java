package vn.edu.fpt.notification.dto.request.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 04/12/2022 - 17:02
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class _CreateCommentRequest implements Serializable {

    private static final long serialVersionUID = -8167438879102905215L;
    private String content;
}
