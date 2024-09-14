package dev.davidson.ian.advent.year2017.day09;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Builder
@Data
public class Group {

    private final List<Group> inside;
    private final String rawGroup;
    private final Integer depth;


    public static Group newGroup(final String line, final int depth) {
        String rawGroup = line.substring(1, line.length() - 1);
        List<Group> inside = new ArrayList<>();
        int left = 0;
        int right = 1;

        boolean garbage = false;


        while (right < rawGroup.length()) {
            Character ch = rawGroup.charAt(right);

            switch (ch) {
                case '!' -> {
                    right += 2;
                }
                case '<' -> {
                    if (garbage) {
                        right++;
                        continue;
                    }

                    garbage = true;
                    right++;
                    //move to next valid >
                }
                case '>' -> {

                    garbage = false;
                    right++;
                }
                case '{' -> {
                    left = right;
                    right++;
                }
                case '}' -> {
                    if(garbage){
                        right++;
                        continue;
                    }

                    String inner = rawGroup.substring(left, right + 1);
//                    log.info("found inner: {}", inner);
                    inside.add(Group.newGroup(inner, depth + 1));
                    right++;
                }
                default -> {
//                    log.info("found some stuff I don't need: {}", ch);
                    right++;
                }
            }
        }

        return new Group(
                inside,
                rawGroup,
                depth
        );
    }

}
