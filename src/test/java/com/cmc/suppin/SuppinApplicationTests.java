package com.cmc.suppin;

import com.cmc.suppin.global.enums.UserStatus;
import com.cmc.suppin.member.domain.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class SuppinApplicationTests {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void testExistsByUserIdAndStatusNot() {
        // Given
        String userId = "testUser";
        UserStatus status = UserStatus.DELETED;

        // When
        boolean exists = memberRepository.existsByUserIdAndStatusNot(userId, status);

        // Then
        assertThat(exists).isFalse();
    }
}
