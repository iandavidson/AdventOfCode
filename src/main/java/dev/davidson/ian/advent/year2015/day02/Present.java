package dev.davidson.ian.advent.year2015.day02;

public record Present(long lw, long lh, long wh, long volume, long minPerim) {


    public static Present newPresent(int l, int w, int h){
        long smallestP = Math.min( 2*l+ 2*w, Math.min(2*l + 2*h, 2*w + 2*h));
        long volume = ((long) l * w * h);

        return new Present((long) l * w, (long) l * h, (long) w * h, volume, smallestP);
    }


    public long lowestValue(){
        return Math.min(lw, Math.min(lh, wh));
    }
}
