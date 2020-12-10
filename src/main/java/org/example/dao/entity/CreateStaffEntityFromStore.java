package org.example.dao.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "staff", schema = "sakila")
public class CreateStaffEntityFromStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="staff_id")
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToOne
    @JoinColumn(name="address_id")
    private AddressEntity address;

    @Lob
    @Column(name = "picture", columnDefinition="BLOB")
    private byte[] picture;

    @Column
    private String email;

    @Column(name="store_id")
    private int storeId;

    @Column
    private int active;

    @Column
    private String username;

    @Column
    private String password;

    @Column(name="last_update")
    private Timestamp lastUpdate;
}
