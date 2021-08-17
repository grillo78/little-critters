package com.grillo78.littlecritters.common.entities.ants;

import java.util.Random;

public enum AntTypes {

    QUEEN, WORKER, MALE;

    public static AntTypes getRandomType(){
        AntTypes type;
        switch (new Random().nextInt(3)){
            default:
            case 0:
                type = QUEEN;
                break;
            case 1:
                type = WORKER;
                break;
            case 2:
                type = MALE;
                break;
        }
        return type;
    }
}
