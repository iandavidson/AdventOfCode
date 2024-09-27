package dev.davidson.ian.advent.year2017.day20;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Builder;

@Builder
public record Particle(Integer id, Delta x, Delta y, Delta z) {
    private static final Pattern PATTERN = Pattern.compile("^p=<(.*)>, v=<(.*)>, a=<(.*)>");

    public static Particle newParticle(final String line, final int id) {
        Matcher matcher = PATTERN.matcher(line);
        if (matcher.find()) {
            String[] p = matcher.group(1).trim().split(",");
            String[] v = matcher.group(2).trim().split(",");
            String[] a = matcher.group(3).trim().split(",");
            return Particle.builder()
                    .x(Delta.builder()
                            .position(Long.parseLong(p[0]))
                            .velocity(Long.parseLong(v[0]))
                            .acceleration(Long.parseLong(a[0]))
                            .build()
                    )
                    .y(Delta.builder()
                            .position(Long.parseLong(p[1]))
                            .velocity(Long.parseLong(v[1]))
                            .acceleration(Long.parseLong(a[1]))
                            .build()
                    )
                    .z(Delta.builder()
                            .position(Long.parseLong(p[2]))
                            .velocity(Long.parseLong(v[2]))
                            .acceleration(Long.parseLong(a[2]))
                            .build()
                    )
                    .id(id)
                    .build();
        }

        throw new IllegalStateException("Couldn't parse line with defined regex");
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        Particle o = (Particle) other;

        return Objects.equals(this.id, o.id);
    }

    public Particle update() {
        return Particle.builder()
                .x(x.update())
                .y(y.update())
                .z(z.update())
                .id(id)
                .build();
    }

    public Long getTotalManhattanDistance() {
        return Math.abs(x.position()) + Math.abs(y.position()) + Math.abs(z.position());
    }

    public String toCoordinateId() {
        return x.position() +
                "$" +
                y.position() +
                "$" +
                z.position();
    }
}
