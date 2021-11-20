package study.datajpa.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

  @Autowired
  MemberRepository memberRepository;
  @Autowired
  TeamRepository teamRepository;

  @Autowired
  EntityManager em;

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


    System.out.println("memberRepository = " + memberRepository.getClass());
  }

  @Test
  @DisplayName("")
  void queryTest() throws Exception {
    /* GIVEN */
    Team teamA = new Team("teamA");
    Team teamB = new Team("teamB");

    teamRepository.save(teamA);
    teamRepository.save(teamB);

    Member member1 = new Member("member1", 10, teamA);
    Member member2 = new Member("member2", 20, teamA);
    Member member3 = new Member("member3", 30, teamB);
    Member member4 = new Member("member4", 40, teamB);

    memberRepository.save(member1);
    memberRepository.save(member2);
    memberRepository.save(member3);
    memberRepository.save(member4);

    /* WHEN */

    List<MemberDto> memberDto = memberRepository.findMemberDto();

    /* THEN */
    for (MemberDto dto : memberDto) {
      System.out.println("dto.toString() = " + dto.getTeamName());
    }
  }

  @Test
  @DisplayName("페이징 테스트")
  void paging() throws Exception {
    /* GIVEN */
    memberRepository.save(new Member("member1", 10));
    memberRepository.save(new Member("member2", 10));
    memberRepository.save(new Member("member3", 10));
    memberRepository.save(new Member("member4", 10));
    memberRepository.save(new Member("member5", 10));

    int age = 10;
    PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

    /* WHEN */
    Page<Member> page = memberRepository.findByAge(age, pageRequest);
    Page<MemberDto> map = page.map(member -> new MemberDto(member.getId(), member.getUsername(), null));

    /* THEN */
    List<Member> content = page.getContent();
//    long totalElements = page.getTotalElements();

    for (Member member : content) {
      System.out.println("member = " + member);
    }

//    System.out.println("totalElements = " + totalElements);


    int number = page.getNumber();
//    int totalPages = page.getTotalPages();

  }

  @Test
  @DisplayName("")
  void bulkUpdate() throws Exception {
    /* GIVEN */
    memberRepository.save(new Member("member1", 10));
    memberRepository.save(new Member("member2", 19));
    memberRepository.save(new Member("member3", 20));
    memberRepository.save(new Member("member4", 21));
    memberRepository.save(new Member("member5", 40));

    /* WHEN */
    int resultCount = memberRepository.bulkAgePlus(20);

    /* THEN */


  }

  @Test
  @DisplayName("")
  void findMemberLazy() throws Exception {
    /* GIVEN */
    Team teamA = new Team("teamA");
    Team teamB = new Team("teamB");
    teamRepository.save(teamA);
    teamRepository.save(teamB);
    Member member1 = new Member("member1", 10, teamA);
    Member member2 = new Member("member2", 10, teamB);
    memberRepository.save(member1);
    memberRepository.save(member2);
    em.flush();
    em.clear();
    /* WHEN */
    List<Member> members = memberRepository.findAll();

    for (Member member : members) {
      System.out.println("member = " + member.getUsername());
      System.out.println("member.getTeam().getName() = " + member.getTeam().getName());
    }

    /* THEN */

  }

  @Test
  @DisplayName("")
  void queryHint() throws Exception {
    /* GIVEN */
    Member member1 = new Member("member1", 10);
    memberRepository.save(member1);
    em.flush();
    em.clear();

    /* WHEN */
    Member findMember = memberRepository.findReadOnlyByUsername("member1");
    findMember.setUsername("member2");

    em.flush();

    /* THEN */

  }

  @Test
  @DisplayName("")
  void lock() throws Exception {
    /* GIVEN */
    Member member1 = new Member("member1", 10);
    memberRepository.save(member1);
    em.flush();
    em.clear();
    /* WHEN */

    memberRepository.findLockByUsername("member1");

    /* THEN */

  }
}