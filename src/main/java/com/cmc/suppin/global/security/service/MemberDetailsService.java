package com.cmc.suppin.global.security.service;

import com.cmc.suppin.global.security.exception.SecurityException;
import com.cmc.suppin.global.security.user.UserDetailsImpl;
import com.cmc.suppin.member.domain.Member;
import com.cmc.suppin.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.cmc.suppin.global.exception.MemberErrorCode.MEMBER_ALREADY_DELETED;
import static com.cmc.suppin.global.exception.MemberErrorCode.MEMBER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new SecurityException(MEMBER_NOT_FOUND));

        if (member.isDeleted()) {
            throw new SecurityException(MEMBER_ALREADY_DELETED);
        }

        List<GrantedAuthority> authorities = getAuthorities(member);

        return UserDetailsImpl.builder()
                .id(member.getId())
                .userId(member.getUserId())
                .password(member.getPassword())
                .authorities(authorities)
                .build();
    }

    private List<GrantedAuthority> getAuthorities(Member member) {
        return member.getRole() != null ?
                List.of(new SimpleGrantedAuthority(member.getRole().name()))
                : null;
    }
}
