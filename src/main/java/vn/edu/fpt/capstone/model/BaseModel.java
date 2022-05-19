package vn.edu.fpt.capstone.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@MappedSuperclass
@Data
public abstract class BaseModel
{
    @CreatedDate  
    @Column(name = "CREATE_AT")
    private Date createdAt;
    
    @CreatedBy
    @Column(name = "CREATE_BY")
    private String createdBy;
    
    @LastModifiedDate
    @Column(name = "MODIFIED_AT")
    private Date modifiedAt;
    
    @LastModifiedBy
    @Column(name = "MODIFIED_BY")
    private String modifiedBy;

}