package vn.edu.fpt.notification.dto.common;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.notification.dto.cache.UserInfo;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonPropertyOrder({"accountId", "userInfo"})
public class UserInfoResponse implements Serializable {

    private static final long serialVersionUID = 5663868981739818955L;
    private String accountId;
    private UserInfo userInfo;
}
