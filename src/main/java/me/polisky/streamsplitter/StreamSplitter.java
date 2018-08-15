package me.polisky.streamsplitter;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

public class StreamSplitter<T, R> {

  private final Stream<T> stream;
  private Map<R, Stream<T>> splitStream = new HashMap<>();

  private StreamSplitter(Stream<T> stream) {
    this.stream = stream;
  }

  static public <T, R> StreamSplitter<T, R> of(Stream<T> stream) {
    return new StreamSplitter<>(stream);
  }

  public void splitBy(Function<T, R> criterion) {
    splitStream = stream.collect(groupingBy(criterion)).entrySet().stream()
        .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().stream()));
  }

  public Stream<T> get(R value) {
    return splitStream.getOrDefault(value, Stream.empty());
  }

}
