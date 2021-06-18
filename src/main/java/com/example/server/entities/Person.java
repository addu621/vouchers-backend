package com.example.server.entities;


import javax.persistence.*;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long person_id;
    private String first_name;
    private String middle_name;
    private String last_name;
    private String email;
    private String password;
    private String mobile;
    private String image_url;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Wallet wallet;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private BuyerProfile buyer_profile;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private SellerProfile seller_profile;

    public Person(Long person_id, String first_name, String middle_name, String last_name, String email, String password, String mobile, String image_url, Wallet wallet, BuyerProfile buyer_profile, SellerProfile seller_profile) {
        this.person_id = person_id;
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
        this.image_url = image_url;
        this.wallet = wallet;
        this.buyer_profile = buyer_profile;
        this.seller_profile = seller_profile;
    }

    public Long getPerson_id() {
        return person_id;
    }

    public void setPerson_id(Long person_id) {
        this.person_id = person_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public BuyerProfile getBuyer_profile() {
        return buyer_profile;
    }

    public void setBuyer_profile(BuyerProfile buyer_profile) {
        this.buyer_profile = buyer_profile;
    }

    public SellerProfile getSeller_profile() {
        return seller_profile;
    }

    public void setSeller_profile(SellerProfile seller_profile) {
        this.seller_profile = seller_profile;
    }
}
