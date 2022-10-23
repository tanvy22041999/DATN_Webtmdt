package com.spring.ecomerce.securities.provider;

import com.spring.ecomerce.securities.AccountDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Slf4j
@Service
public class AccountAuthenticationProvider implements AuthenticationProvider {

    private final AccountDetailsService accountDetailsService;

    private final MessageSource messageSource;

    public AccountAuthenticationProvider(AccountDetailsService accountDetailsService, MessageSource messageSource) {
        this.accountDetailsService = accountDetailsService;
        this.messageSource = messageSource;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AccountAuthenticationToken token = (AccountAuthenticationToken) authentication;
        String username = token.getName();
        String password = token.getCredentials() == null ? null : token.getCredentials().toString();
        boolean verifyCredentials = Boolean.parseBoolean(token.isVerifyCredentials().toString());
        UserDetails userDetails = accountDetailsService.loadUserByUsername(username);
        Locale locale = LocaleContextHolder.getLocale();
        if (!userDetails.isEnabled())
            throw new BadCredentialsException(messageSource.getMessage("error.account.disable", null, locale));
        if (verifyCredentials) {
            assert password != null;
            if (password.equals(userDetails.getPassword())) {
                return new AccountAuthenticationToken(username, password, verifyCredentials, userDetails.getAuthorities());
            } else {
                throw new BadCredentialsException(messageSource.getMessage("error.account.wrongpass", null, locale));
            }
        } else {
            return new AccountAuthenticationToken(username, "N/A", verifyCredentials, userDetails.getAuthorities());
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(AccountAuthenticationToken.class);
    }
}
