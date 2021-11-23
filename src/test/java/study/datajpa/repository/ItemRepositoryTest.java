package study.datajpa.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.datajpa.entity.Item;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemRepositoryTest {

  @Autowired
  ItemRepository itemRepository;

  @Test
  @DisplayName("")
  void test() throws Exception {
    /* GIVEN */

    Item item = new Item(1L);
    itemRepository.save(item);
    /* WHEN */

    /* THEN */

  }
}