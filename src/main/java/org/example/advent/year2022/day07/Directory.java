package org.example.advent.year2022.day07;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class Directory{
    private final String name;

    @Builder.Default
    private final Directory parent = null;

    @Builder.Default
    private final List<Directory> childDirectories = new ArrayList<>();

    @Builder.Default
    private final List<SystemFile> childFiles = new ArrayList<>();

    public long calculateSize(){
        Long sum = 0L;
        for(SystemFile file : childFiles){
            sum += file.size();
        }

        for(Directory directory : childDirectories){
            sum += directory.calculateSize();
        }

        return sum;
    }
}
