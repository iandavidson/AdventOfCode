package org.example.advent.year2022.day13;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class DistressElement implements Comparable<DistressElement> {

    private static final Character L_B = '[';
    private static final Character R_B = ']';
    private static final Character COMMA = ',';

    private List<DistressElement> innerPackets = new ArrayList<>();

    private Integer value = -1;

    private Boolean isNumber = true;
    private final String rawElement;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DistressElement that = (DistressElement) o;
        return Objects.equals(rawElement, that.rawElement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rawElement);
    }

    public DistressElement(final String line) {
        this.rawElement = line;
        if (line.charAt(0) == L_B) {
            String trimmedLine = line.substring(1, line.length() - 1);

            List<DistressElement> innerPackets = new ArrayList<>();

            int level = 0;
            StringBuilder tmp = new StringBuilder();
            for (char c : trimmedLine.toCharArray()) {
                if (c == COMMA && level == 0) {
                    innerPackets.add(new DistressElement(tmp.toString()));
                    tmp = new StringBuilder();
                } else {
                    level += (c == L_B) ? 1 : (c == R_B) ? -1 : 0;
                    tmp.append(c);
                }
            }
            if (!tmp.toString().isBlank()) {
                innerPackets.add(new DistressElement(tmp.toString()));
            }

            this.innerPackets = innerPackets;
            this.isNumber = false;
        } else {
            this.value = Integer.parseInt(line);

        }
    }

    @Override
    public int compareTo(DistressElement o) {
        if (this.isNumber && o.isNumber) {
            return Integer.compare(o.getValue(), this.value);
        }

        if (!this.isNumber && !o.getIsNumber()) {
            for (int i = 0; i < Math.min(this.innerPackets.size(), o.getInnerPackets().size()); i++) {
                int val = this.innerPackets.get(i).compareTo(o.getInnerPackets().get(i));
                if (val != 0) {
                    return val;
                }
            }
            return o.getInnerPackets().size() - this.innerPackets.size();
        }

        DistressElement deThis = this.isNumber ? new DistressElement(L_B.toString() + this.value + R_B) : this;
        DistressElement deOther = o.getIsNumber() ? new DistressElement(L_B.toString() + o.getValue() + R_B) : o;
        return deThis.compareTo(deOther);
    }
}
