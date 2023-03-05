package com.mvpmatch.backend.adapter.database.product;

import com.mvpmatch.backend.adapter.database.user.UserDBO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDBO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(name = "amount_available")
    private int amountAvailable;

    @Column(name = "cost")
    private int cost;

    @Column(name = "product_name")
    private String productName;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private UserDBO seller;

}
