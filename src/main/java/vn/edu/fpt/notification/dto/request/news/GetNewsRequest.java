package vn.edu.fpt.notification.dto.request.news;

import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.fpt.notification.dto.common.AuditableRequest;
import vn.edu.fpt.notification.dto.common.PageableRequest;
import vn.edu.fpt.notification.utils.RequestDataUtils;

import java.io.Serializable;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 04/12/2022 - 14:44
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
public class GetNewsRequest extends AuditableRequest {

    private static final long serialVersionUID = -5102854532201040313L;
    private String title;

    public String getTitle() {
        return RequestDataUtils.convertSearchableData(title);
    }
}
