package com.example.app_clickup.entity;

import com.example.app_clickup.entity.template.AbsUUIDEntity;
import com.example.app_clickup.entity.enums.SystemRoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

/**
 *  SPRINGDAGI ASOSIY USER SIFATIDA KORISHI UCHUN UserDetails implements OLAMIZ <br>
 *  QAYSI CLASS IMPLEMENTS OLSA USERDETAILSDAN U SPRINGDA SISTEMAGA KIRUVCHI KARENT PRINSIPLE <br>
 *  BOLISH IMKONINI BERADI.
 */

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User extends AbsUUIDEntity implements UserDetails {

    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private String color;

    private String initialLetter;

    /**
     *  LAZY ORTIQCHA MA'LUMOTLARNI OLIB KELMAYDI QACHONKI <br>
     *  User.avatar desak ushanda olib keladi
     */
    @OneToOne(fetch = FetchType.LAZY)
    private Attachment avatar;

    private String emailCode;

    @Enumerated(EnumType.STRING)
    private SystemRoleName systemRoleName;

    private boolean accountNonExpired = true;

    private boolean accountNonLocked = true;

    private boolean credentialsNonExpired = true;

    private boolean enabled;

    // BU USERNING XUQUQLAR ROYXATLARI
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(this.systemRoleName.name());
        return Collections.singletonList(simpleGrantedAuthority);
    }

    // BU USERNING USERNAMENI QAYTARUVCHI METHOD
    @Override
    public String getUsername() {
        return this.email;
    }

    // ACCOUNTNING AMMAL QILISH MUDDATINI QAYTARADI
    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    // ACCOUNT BLOCKLANGANLIK XOLATINI QAYTARADI
    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    // ACCOUNTNING ISHONCHLILIK MUDDATI TUGAGAN YOKI TUGAMAGANLIGINI QAYTARADI
    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    // ACCOUNTNING YONIQ YOKI O'CHIQLIGINI QAYTRADI
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public User(String fullName, String email, String password, SystemRoleName systemRoleName) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.systemRoleName = systemRoleName;
    }

}
