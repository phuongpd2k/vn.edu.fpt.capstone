package vn.edu.fpt.capstone.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.format.annotation.DateTimeFormat;

@MappedSuperclass
public abstract class BaseModel
{
    @CreatedDate  
    @Column(name = "CREATE_AT")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date createdAt;
    
    @CreatedBy
    @Column(name = "CREATE_BY")
    private String createdBy;
    
    @LastModifiedDate
    @Column(name = "MODIFIED_AT")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date modifiedAt;
    
    @LastModifiedBy
    @Column(name = "MODIFIED_BY")
    private String modifiedBy;

}