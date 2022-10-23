package com.spring.ecomerce.services.AccountService;

import com.spring.ecomerce.commons.MessageManager;
import com.spring.ecomerce.dtos.ServiceResponse;
import com.spring.ecomerce.entities.Account;
import com.spring.ecomerce.repositories.AccountRepository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MessageManager messageManager;

    private String logicCreateCheck(Account account){
        ServiceResponse<Account> accountFounded = this.findAccountByPhoneNumber(account.getPhoneNumber());
        if(accountFounded.getData() != null){
           return messageManager.getMessage("ERR0001", null);
        }
        return "";
    }

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public ServiceResponse<Account> findAccountByPhoneNumber(String phoneNumber) {
        ServiceResponse<Account> result = new ServiceResponse<Account>();
        try{
            Account account = accountRepository.findByPhoneNumber(phoneNumber);
            result.setData(account);
        }
        catch (Exception ex){
            result.setMessageError(messageManager.getMessage("ERR0003", null));
        }
        return  result;
    }

    @Override
    public long count() {
        return accountRepository.count();
    }

    @Override
    public ServiceResponse<Account> createNewAccount(Account account) {
        ServiceResponse<Account> result = new ServiceResponse<Account>();
        String error = this.logicCreateCheck(account);
        if(!"".equals(error)){
            result.setMessageError(error);
            return result;
        }

        try {
            accountRepository.save(account);
        }
        catch (Exception ex){
            result.setMessageError(messageManager.getMessage("ERR0003", null));
        }
        return result;
    }
}
