package vn.edu.fpt.notification.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 22/12/2022 - 15:38
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateNotifyRequest implements Serializable {

    private static final long serialVersionUID = 230001067666293091L;
    private String accountId;
    private CreateNotifyContentRequest content;
}
