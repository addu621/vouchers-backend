package com.example.server.entities;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CartId implements Serializable {
    public Long voucher_id;
    public Long buyer_id;
}
