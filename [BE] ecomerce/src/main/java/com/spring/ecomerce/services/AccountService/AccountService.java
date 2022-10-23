package com.spring.ecomerce.services.AccountService;

import com.spring.ecomerce.dtos.ServiceResponse;
import com.spring.ecomerce.entities.Account;

public interface AccountService {

    ServiceResponse<Account> findAccountByPhoneNumber(String phongnumber);

    long count();

    ServiceResponse<Account> createNewAccount(Account account);
}
