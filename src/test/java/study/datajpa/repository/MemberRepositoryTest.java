package study.datajpa.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

  @Autowired
  MemberRepository memberRepository;

  @Test
  @DisplayName("testMember")
  void testMember() throws Exception {
    //given
    Member member = new Member("memberA");
    Member savedMember = memberRepository.save(member);

    //when
    Member findMember = memberRepository.findById(savedMember.getId()).get();

    //then
    assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
    assertThat(findMember.getId()).isEqualTo(member.getId());
    assertThat(findMember).isEqualTo(member);

  }
}