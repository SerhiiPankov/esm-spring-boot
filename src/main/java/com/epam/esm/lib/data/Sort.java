package com.epam.esm.lib.data;

import com.epam.esm.exception.RequestException;

import java.util.Arrays;

public class Sort {
    private String parameter;
    private Direction direction;

    public Sort() {
        parameter = "id";
        direction = Direction.ASC;
    }

    public Sort(String parameter, Direction direction) {
        this.parameter = parameter;
        this.direction = direction;
    }

    public enum Direction {
        ASC("ASC"), DESC("DESC");

        private final String value;

        Direction(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static Direction getDirection(String value) {
            return Arrays.stream(values())
                    .filter(o -> o.getValue().equals(value))
                    .findFirst()
                    .orElseThrow(() -> new RequestException("Parameter sortBy Direction is not valid"));
        }
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
