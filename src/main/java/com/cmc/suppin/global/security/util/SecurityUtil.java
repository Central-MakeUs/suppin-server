package com.cmc.suppin.global.security.util;

import com.cmc.suppin.global.exception.SecurityErrorCode;
import com.cmc.suppin.global.security.exception.SecurityException;
import com.cmc.suppin.global.security.reslover.Account;
import com.cmc.suppin.global.security.user.UserDetailsImpl;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtil {

    public static Account getCurrentAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        validateAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return Account.of(userDetails.getId(), userDetails.getUserId(), userDetails.getAuthority());
    }

    private static void validateAuthentication(Authentication authentication) {
        if (Objects.isNull(authentication) || !(authentication instanceof UsernamePasswordAuthenticationToken)) {
            log.error(">>>>>> Invalid Authentication : {}", authentication);
            throw new SecurityException(SecurityErrorCode.UNAUTHORIZED);
        }
    }
}
