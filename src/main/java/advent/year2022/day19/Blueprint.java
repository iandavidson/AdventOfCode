package advent.year2022.day19;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Blueprint(int id, int oreRobotOreCost, int clayRobotOreCost, int obsidianRobotOreCost, int obsidianRobotClayCost, int geodeRobotOreCost, int geodeRobotObsidianCost) {

    public static Blueprint newBlueprint(final String line) {
        final Pattern p = Pattern.compile(
                "Blueprint (\\d+): Each ore robot costs (\\d+) ore. Each clay robot costs (\\d+) ore. Each obsidian robot costs (\\d+) ore and (\\d+) clay. Each geode robot costs (\\d+) ore and (\\d+) obsidian.");

        final Matcher m = p.matcher(line);
        if (m.find()) {
            return new Blueprint(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)),
                    Integer.parseInt(m.group(3)), Integer.parseInt(m.group(4)), Integer.parseInt(m.group(5)),
                    Integer.parseInt(m.group(6)), Integer.parseInt(m.group(7)));
        }

        throw new IllegalStateException("invalid input");
    }
}





