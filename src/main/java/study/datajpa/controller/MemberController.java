package study.datajpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberRepository;

import javax.annotation.PostConstruct;
import java.util.stream.IntStream;

@RestController
@RequiredArgsConstructor
public class MemberController {

  private final MemberRepository memberRepository;

  @GetMapping("/members/{id}")
  public String findMember(@PathVariable Long id) {
    Member member = memberRepository.findById(id).get();
    return member.getUsername();
  }

  @GetMapping("/members2/{id}")
  public String findMember2(@PathVariable Member member) {
    return member.getUsername();
  }

  @GetMapping("/members")
  public Page<Member> list(@PageableDefault(size=5, sort="username") Pageable pageable) {
    return memberRepository.findAll(pageable);
  }

  @PostConstruct
  public void init() {
    IntStream.range(0, 101).boxed().forEach(i -> memberRepository.save(new Member("user" + i, i)));
  }
}
