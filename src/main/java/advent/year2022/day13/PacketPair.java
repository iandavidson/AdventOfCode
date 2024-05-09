package advent.year2022.day13;

public record PacketPair(DistressElement left, DistressElement right) {
    public Boolean isPacketValid() {
        return left.compareTo(right) > 0;
    }
}
