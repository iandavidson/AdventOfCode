package dev.davidson.ian.advent.year2017.day24;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Builder
public class Bridge implements Comparable<Bridge> {

    @Builder.Default
    private final List<BridgeComponent> bridgeComponents = new ArrayList<>();

    @Builder.Default
    private final List<Connection> connections = new ArrayList<>();

    public Bridge clone() {
        return new Bridge(new ArrayList<>(bridgeComponents), new ArrayList<>(connections));
    }

    public void init(final BridgeComponent bridgeComponent) {
        this.bridgeComponents.add(bridgeComponent);
        this.connections.add(new Connection(0));
    }

    public int getStrength() {
        return bridgeComponents.stream().mapToInt(BridgeComponent::strength).sum();
    }

    public int getBridgeLength() {
        return bridgeComponents.size();
    }

    public boolean canAddComponent(final BridgeComponent component) {

        if (this.bridgeComponents.contains(component)) {
            return false;
        }


        BridgeComponent end = bridgeComponents.getLast();

        //if none match, just return false fast
        if (!end.contains(component.ports().getFirst()) && !end.contains(component.ports().get(1))) {
            return false;
        }

        boolean endIsDouble = end.isSymmetric();
        Connection lastConnection = connections.getLast();

        if (end.contains(component.ports().getFirst())) {

            if (endIsDouble) {
                return true;
            }

            if (lastConnection.value() == component.ports().getFirst()) {
                return false;
            }
        }

        if (end.contains(component.ports().get(1))) {

            if (endIsDouble) {
                return true;
            }

            return lastConnection.value() != component.ports().get(1);
        }

        return true;

    }


    public void add(final BridgeComponent nextbridgeComponent) {

        BridgeComponent end = bridgeComponents.getLast();

        Integer connectionValue;
        if (end.contains(nextbridgeComponent.ports().getFirst())) {
            connectionValue = nextbridgeComponent.ports().getFirst();
        } else {
            connectionValue = nextbridgeComponent.ports().get(1);
        }


        this.bridgeComponents.add(nextbridgeComponent);
        this.connections.add(new Connection(connectionValue));
    }


    @Override
    public int compareTo(Bridge o) {
        return o.getStrength() - this.getStrength();
    }
}
