/* (C)2022 */
package com.example.simpleblog.entity;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "users",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"email"}),
            @UniqueConstraint(columnNames = {"username"})
        })
public class User {
    @Id
    @Type(type = "uuid-char")
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String avatar = "https://ik.imagekit.io/ja17viwyaow/avatar-placeholder_2VLM6EtOW.png";

    // FetchType.EAGER means whenever user entity is retrieved, related roles are also retrieved
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_roles",
            joinColumns =
                    @JoinColumn(
                            name = "user_id", // column name in join table
                            referencedColumnName =
                                    "id" // value of column name references value of "id" column
                            ),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;
}
