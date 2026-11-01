package com.vee.bank.entity;

import com.vee.bank.enums.CustomerType;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CustomerType customerType;

    @Column(nullable = false)
    private LocalDate joinedDate;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> accounts = new ArrayList<>();

    protected Customer() {}

    private Customer(Builder builder) {
        this.fullName = builder.fullName;
        this.email = builder.email;
        this.customerType = builder.customerType;
        this.joinedDate = builder.joinedDate;
        this.accounts = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public CustomerType getCustomerType() {
        return customerType;
    }
    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }
    public LocalDate getJoinedDate() {
        return joinedDate;
    }
    public void setJoinedDate(LocalDate joinedDate) {
        this.joinedDate = joinedDate;
    }
    public List<Account> getAccounts() {
        return accounts;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String fullName, email;
        private CustomerType customerType;
        private LocalDate joinedDate;

        public Builder fullName(String v) { this.fullName = v; return this; }
        public Builder email(String v) { this.email = v; return this; }
        public Builder customerType(CustomerType v) { this.customerType = v; return this; }
        public Builder joinedDate(LocalDate v) { this.joinedDate = v; return this; }
        public Customer build() { return new Customer(this); }
    }
}
