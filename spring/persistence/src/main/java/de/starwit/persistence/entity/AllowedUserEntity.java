package de.starwit.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name = "ALLOWEDUSER")
public class AllowedUserEntity extends AbstractEntity<Long> {
    
    @NotBlank
	@Column(name = "USERALIAS", nullable = false, unique = true)
    private String userAlias;

    public String getUserAlias() {
        return userAlias;
    }

    public void setUserAlias(String userAlias) {
        this.userAlias = userAlias;
    }
}