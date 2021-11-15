package study.datajpa.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

  @Autowired
  MemberJpaRepository memberJpaRepository;

  @Test
  @DisplayName("testMember")
  void testMember() throws Exception {
    //given
    Member member = new Member("memberA");

    //when
    Member savedMember = memberJpaRepository.save(member);
    Member findMember = memberJpaRepository.find(savedMember.getId());

    //then
    assertThat(findMember.getUsername()).isEqualTo("memberA");

  }
}