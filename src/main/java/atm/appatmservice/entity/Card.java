package atm.appatmservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import atm.appatmservice.entity.template.AbsUUIDEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Card extends AbsUUIDEntity implements UserDetails {
    @Column(unique = true, nullable = false, length = 16)
    private String number;

    @Column(nullable = false)
    private String cvvCode;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private Date validityPeriod;

    @Column(nullable = false)
    private String code;

    private Double balance = 0.0;

    @ManyToOne
    private CardType cardType;

    @ManyToOne
    private Currency currency;

    @ManyToOne
    private Bank bank;

    private boolean status;

    @ManyToOne
    private Role role;

    private boolean accountNonExpired = true;

    private boolean accountNonLocked = true;

    private boolean credentialsNonExpired = true;

    private boolean enabled = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(this.role);
    }

    @Override
    public String getPassword() {
        return code;
    }

    @Override
    public String getUsername() {
        return number;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
