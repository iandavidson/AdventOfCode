package dev.davidson.ian.advent.year2017.day24;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ElectromagneticMoat {
    private static final String INPUT_PATH = "adventOfCode/2017/day24/input.txt";
    private static final String SAMPLE_PATH = "adventOfCode/2017/day24/sample.txt";

    public static void main(String[] args) {
        ElectromagneticMoat electromagneticMoat = new ElectromagneticMoat();
        List<BridgeComponent> components = readFile(INPUT_PATH);

        electromagneticMoat.execute(components);
    }

    private static List<BridgeComponent> readFile(final String filePath) {
        List<BridgeComponent> bridgeComponents = new ArrayList<>();

        ClassLoader cl = ElectromagneticMoat.class.getClassLoader();
        File file = new File(
                Objects.requireNonNull(cl.getResource(filePath)).getFile()
        );
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                bridgeComponents.add(new BridgeComponent(
                        Arrays.stream(scanner.nextLine().split("/"))
                                .mapToInt(Integer::parseInt).boxed()
                                .collect(Collectors.toList())
                ));
            }
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Couldn't read file at provided path");
        }

        return bridgeComponents;
    }


    public void execute(final List<BridgeComponent> components) {

        List<Bridge> bridges = new ArrayList<>();
        for (BridgeComponent component : components) {
            if (component.contains(0)) {
                Bridge bridge = Bridge.builder().build();
                bridge.init(component);
                bridges.add(bridge);
            }
        }

        List<Bridge> results = new ArrayList<>();

        for (Bridge bridge : bridges) {
            dfs(bridge, results, components);
        }

        Collections.sort(results);

        log.info("Part1: {} ", results.getFirst().getStrength());

        Collections.sort(results, (a, b) -> {
            if (a.getBridgeLength() == b.getBridgeLength()) {
                return b.getStrength() - a.getStrength();
            } else {
                return b.getBridgeLength() - a.getBridgeLength();
            }
        });
        log.info("Part2: {} ", results.getFirst().getStrength());
    }

    public void dfs(final Bridge bridge, final List<Bridge> results, final List<BridgeComponent> components) {
        int count = 0;
        for (BridgeComponent bridgeComponent : components) {
            if (bridge.canAddComponent(bridgeComponent)) {
                Bridge bridgeCopy = bridge.clone();
                bridgeCopy.add(bridgeComponent);
                dfs(bridgeCopy, results, components);
                count++;
            }
        }

        if (count == 0) {
            results.add(bridge);
        }
    }

}
