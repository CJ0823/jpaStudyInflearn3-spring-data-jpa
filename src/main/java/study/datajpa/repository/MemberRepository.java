package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;


public interface MemberRepository extends JpaRepository<Member, Long>, JpaSpecificationExecutor<Member> {

  @Query(value = "select m.member_id as id, m.username, t.name as teamName " +
          "from member m left join team t ",
          countQuery = "select count(*) from member ", nativeQuery = true)
  Page<MemberProjection> findByNativeProjection(Pageable pageable);

  @Query(value = "select * from member where username = ?", nativeQuery = true)
  Member findByNativeQuery(String username);

  List<NestedClosedProjections> findProjectionsByUsername(@Param("username") String username);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  List<Member> findLockByUsername(String username);

  @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
  Member findReadOnlyByUsername(String username);

//  @Query("select m from Member m left join fetch m.team")
//  @EntityGraph(attributePaths = "team")
//  List<Member> findAllFetch();

  @Modifying(clearAutomatically = true)
  @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
  int bulkAgePlus(@Param("age") int age);

  Page<Member> findByAge(int age, Pageable pageable);

  @Query("select m from Member m where m.username = :username and m.age = :age ")
  List<Member> findUser(@Param("username") String username, @Param("age") int age);

  @Query("select m.username from Member m")
  List<String> findUsernameList();

  @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
  List<MemberDto> findMemberDto();

}
