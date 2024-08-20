package dev.davidson.ian.advent.year2016.day09;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class CompressionRule {
    //LGOFNEIZBERESE as is LGOFNEIZBERESE
    //(3x3)WREHFF -> WREWREWRE
    //(6x1)(2x3)WREHFFHTWBHRRRU -> (6x1)WRWRWR -> WRWRWR

    @Builder.Default
    private final Integer prefixLength = 0;

    @Builder.Default
    private final Integer repeats = 1;
    private String word;

    public Long getDecompressedLength() {
        if(prefixLength == 0 && repeats == 1){
            return (long) word.length();
        }else if(word.isBlank()){
            return 0L;
        }else if(prefixLength < word.length()){
            //
            return ((long) prefixLength * repeats);
        }else{
            //prefix length is word length
            return (long) word.length() * repeats;
        }
    }



}
