package dev.davidson.ian.advent.year2017.day20;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Builder;

@Builder
public record Particle(Integer id, Delta x, Delta y, Delta z) {
    private static final Pattern PATTERN = Pattern.compile("^p=<(.*)>, v=<(.*)>, a=<(.*)>");
    //        Pattern pattern = Pattern.compile("^.*?(\\d+): (\\w+): (\\d+), (\\w+): (\\d+), (\\w+): (\\d+)");

    public static Particle newParticle(final String line, final int id) {
        //p=<698,2441,1117>, v=<98,345,156>, a=<-9,-22,-10>
        Matcher matcher = PATTERN.matcher(line);
        if (matcher.find()) {
            String[] p = matcher.group(1).trim().split(",");
            String[] v = matcher.group(2).trim().split(",");
            String[] a = matcher.group(3).trim().split(",");
            return Particle.builder()
                    .x(Delta.builder()
                            .position(Integer.parseInt(p[0]))
                            .velocity(Integer.parseInt(v[0]))
                            .acceleration(Integer.parseInt(a[0]))
                            .build()
                    )
                    .y(Delta.builder()
                            .position(Integer.parseInt(p[1]))
                            .velocity(Integer.parseInt(v[1]))
                            .acceleration(Integer.parseInt(a[1]))
                            .build()
                    )
                    .z(Delta.builder()
                            .position(Integer.parseInt(p[2]))
                            .velocity(Integer.parseInt(v[2]))
                            .acceleration(Integer.parseInt(a[2]))
                            .build()
                    )
                    .id(id)
                    .build();
        }

        throw new IllegalStateException("Couldn't parse line with defined regex");
    }
}
