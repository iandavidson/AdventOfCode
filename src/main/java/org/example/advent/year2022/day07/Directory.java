package org.example.advent.year2022.day07;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
@Data
@AllArgsConstructor
public class Directory implements Comparable<Directory> {
    private final String name;

    @Builder.Default
    private final Directory parent = null;

    @Builder.Default
    private final List<Directory> childDirectories = new ArrayList<>();

    @Builder.Default
    private final List<SystemFile> childFiles = new ArrayList<>();

    @Builder.Default
    private Long size = 0L;

    public long calculateSize() {
        Long sum = 0L;
        for (SystemFile file : childFiles) {
            sum += file.size();
        }

        for (Directory directory : childDirectories) {
            sum += directory.calculateSize();
        }

        return sum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Directory directory = (Directory) o;
        return Objects.equals(name, directory.name) && Objects.equals(size, directory.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, size);
    }

    @Override
    public int compareTo(Directory o) {
        return (int) (size - o.getSize());
    }
}
