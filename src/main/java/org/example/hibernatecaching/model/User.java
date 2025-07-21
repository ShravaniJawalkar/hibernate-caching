package org.example.hibernatecaching.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_dumy", schema = "public",
        uniqueConstraints = {
                @UniqueConstraint(name = "user_name_unique", columnNames = "user_name"),
                @UniqueConstraint(name = "email_unique", columnNames = "email")
        },
        indexes = {
                @Index(name = "user_name_index", columnList = "user_name"),
                @Index(name = "email_index", columnList = "email")
        })
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "userCache"
)
@Cacheable
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    @Column(name = "user_id", unique = true, nullable = false, columnDefinition = "varchar(36)")
    private String id;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "inserted_by", insertable = true, updatable = false)
    private String insertedBy;
    @CreationTimestamp
    @Column(name = "inserted_date")
    private LocalDateTime insertedDate;
    @Column(name = "updated_by", insertable = false, updatable = true)
    private String updatedBy;
    @UpdateTimestamp
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id", nullable = false)
    private Address address;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    public void addOrder(Order order) {
        if (orders != null) {
            orders.add(order);
            order.setUser(this); // Set the user in the order for bidirectional mapping
        } else {
            throw new IllegalStateException("Orders list is not initialized");
        }
    }
}
