package vn.edu.fpt.notification.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 26/10/2022 - 19:39
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AttachFile implements Serializable {

    private static final long serialVersionUID = -2409234951793008808L;
    private String fileName;
    private String fileKey;
}
