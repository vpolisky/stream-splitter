package me.polisky.streamsplitter;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class StreamSplitterTest {

  @Test
  void splitBy() {
    class A {

      private String key;
      private Integer value;

      A(String key, Integer value) {
        this.key = key;
        this.value = value;
      }

      public String getKey() {
        return key;
      }
    }

    A a1 = new A("a", 1);
    A a2 = new A("a", 2);
    A a3 = new A("b", 3);
    A a4 = new A("c", 4);
    List<A> list = Arrays.asList(a1, a2, a3, a4);

    StreamSplitter<A, String> splitter = StreamSplitter.of(list.stream());
    splitter.splitBy(A::getKey);

    List<A> list1 = splitter.get("a").collect(Collectors.toList());
    List<A> list2 = splitter.get("b").collect(Collectors.toList());
    List<A> list3 = splitter.get("c").collect(Collectors.toList());

    assertThat(list1).contains(a1, a2);
    assertThat(list1).containsAnyOf(a3, a4);
    assertThat(list2).contains(a3);
    assertThat(list2).containsAnyOf(a1, a2, a4);
    assertThat(list3).contains(a4);
    assertThat(list3).containsAnyOf(a1, a2, a3);
  }
}