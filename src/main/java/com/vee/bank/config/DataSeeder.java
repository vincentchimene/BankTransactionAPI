package com.vee.bank.config;

import com.vee.bank.entity.Account;
import com.vee.bank.entity.Customer;
import com.vee.bank.enums.CustomerType;
import com.vee.bank.repository.AccountRepository;
import com.vee.bank.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Seeds the in-memory H2 database with sample customers and accounts
 * when the application starts (active by default; excluded in tests).
 */
@Component
@Profile("!test")
public class DataSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    public DataSeeder(CustomerRepository customerRepository, AccountRepository accountRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public void run(String... args) {

        // ── Retail customer (joined 1 year ago) ─────────────────────────────
        Customer retail = customerRepository.save(Customer.builder()
                .fullName("Ada Okafor")
                .email("ada.okafor@example.com")
                .customerType(CustomerType.RETAIL)
                .joinedDate(LocalDate.now().minusYears(1))
                .build());

        accountRepository.save(Account.builder()
                .accountNumber("1000000001")
                .accountName("Ada Okafor")
                .balance(new BigDecimal("500000.00"))
                .customer(retail)
                .build());

        // ── Business customer (joined 2 years ago) ───────────────────────────
        Customer business = customerRepository.save(Customer.builder()
                .fullName("Femi Holdings Ltd")
                .email("accounts@femiltd.com")
                .customerType(CustomerType.BUSINESS)
                .joinedDate(LocalDate.now().minusYears(2))
                .build());

        accountRepository.save(Account.builder()
                .accountNumber("2000000001")
                .accountName("Femi Holdings Ltd")
                .balance(new BigDecimal("5000000.00"))
                .customer(business)
                .build());

        accountRepository.save(Account.builder()
                .accountNumber("2000000002")
                .accountName("Femi Holdings Ltd – Payroll")
                .balance(new BigDecimal("1000000.00"))
                .customer(business)
                .build());

        // ── Loyal customer (joined 6 years ago) ──────────────────────────────
        Customer loyal = customerRepository.save(Customer.builder()
                .fullName("Ngozi Adeyemi")
                .email("ngozi.adeyemi@example.com")
                .customerType(CustomerType.RETAIL)
                .joinedDate(LocalDate.now().minusYears(6))
                .build());

        accountRepository.save(Account.builder()
                .accountNumber("3000000001")
                .accountName("Ngozi Adeyemi")
                .balance(new BigDecimal("200000.00"))
                .customer(loyal)
                .build());

        // ── Receiving / third-party account ──────────────────────────────────
        Customer thirdParty = customerRepository.save(Customer.builder()
                .fullName("Chidi Okeke")
                .email("chidi.okeke@example.com")
                .customerType(CustomerType.RETAIL)
                .joinedDate(LocalDate.now().minusYears(3))
                .build());

        accountRepository.save(Account.builder()
                .accountNumber("9000000001")
                .accountName("Chidi Okeke")
                .balance(BigDecimal.ZERO)
                .customer(thirdParty)
                .build());

        log.info("=== Sample data seeded. Accounts: 1000000001, 2000000001, 2000000002, 3000000001, 9000000001 ===");
    }
}
