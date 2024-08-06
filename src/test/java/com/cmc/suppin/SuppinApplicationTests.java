package com.cmc.suppin;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SuppinApplicationTests {

    @Test
    void contextLoads() {
    }

//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Test
//    public void testExistsByUserIdAndStatusNot() {
//        // Given
//        String userId = "testUser";
//        UserStatus status = UserStatus.DELETED;
//
//        // When
//        boolean exists = memberRepository.existsByUserIdAndStatusNot(userId, status);
//
//        // Then
//        assertThat(exists).isFalse();
//    }
}
