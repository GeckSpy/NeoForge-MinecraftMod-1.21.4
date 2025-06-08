package net.geckspy.geckspymm.entity.lion;

import java.util.Arrays;
import java.util.Comparator;

public enum LionEntityVariant {
    FEMALE(0),
    MALE(1);

    private final int id;
    private static final LionEntityVariant[] BY_ID = Arrays.stream(values()).sorted(
            Comparator.comparingInt(LionEntityVariant::getId)).toArray(LionEntityVariant[]::new);

    LionEntityVariant(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public static LionEntityVariant byId(int id){
        return BY_ID[id % BY_ID.length];
    }
}
