package vn.edu.fpt.notification.dto.response.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 04/12/2022 - 17:04
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class _CreateCommentResponse implements Serializable {

    private static final long serialVersionUID = 7443762738441341781L;
    private String commentId;
}
