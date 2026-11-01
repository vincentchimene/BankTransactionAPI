package com.vee.bank.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "accounts")
public class Account {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Pattern(regexp = "\\d{10}", message = "Account number must contain exactly 10 digits(NUBAN)")
    @Column(nullable = false, unique = true, length = 10, updatable = false)
    private String accountNumber;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String accountName;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance =  BigDecimal.ZERO;

    @Version
    private Long version;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "sourceAccount", fetch = FetchType.LAZY)
    private List<Transfer> transfers = new ArrayList<>();

    @OneToMany(mappedBy = "sourceAccount", fetch = FetchType.LAZY)
    private List<AirtimePurchase> airtimePurchases = new ArrayList<>();

    protected Account() {}

    private Account(Builder builder) {
        this.accountNumber      = builder.accountNumber;
        this.accountName        = builder.accountName;
        this.balance            = BigDecimal.ZERO;
        this.customer           = builder.customer;
    }

    public Long getId() {
        return id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }
    public void setAccountName(String accountName) {
        if (accountName == null || accountName.isBlank()) {
            throw new IllegalArgumentException("Account name cannot be blank");
        }
        this.accountName = accountName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Transfer> getTransfers() {
        return Collections.unmodifiableList(transfers);
    }

    public List<AirtimePurchase> getAirtimePurchases() {
        return Collections.unmodifiableList(airtimePurchases);
    }

    public static Builder builder() { return new Builder(); }
    public static final class Builder {
        private String accountNumber, accountName;
        private Customer customer;
        public Builder accountNumber(String valueAccountNumber) { this.accountNumber = valueAccountNumber; return this; }
        public Builder accountName(String valueAccountName) { this.accountName = valueAccountName; return this; }
        public Builder customer(Customer valueCustomer) { this.customer = valueCustomer; return this; }

        public Account build() {
            if (accountNumber == null || !accountNumber.matches("\\d{10}")) {
                throw new IllegalArgumentException("Account number must contain exactly 10 digits");
            }
            if (accountName == null || accountName.isBlank()) {
                throw new IllegalArgumentException("Account name is required");
            }
            if (accountName.length() > 50) {
                throw new IllegalArgumentException("Account name cannot exceed 50 characters");
            }
            if (customer == null){
                throw new IllegalArgumentException("Customer is required");
            }

            return new Account(this);
        }
    }
}
