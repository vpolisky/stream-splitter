Allows to split a (finite) stream into multiple streams by values of its elements:

`Stream.of(["a", 1], ["a", 2], ["b", 1]) -> ["a": Stream.of(1, 2), "b": Stream.of(1)]`