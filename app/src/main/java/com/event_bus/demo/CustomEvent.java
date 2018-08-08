package com.event_bus.demo;

/**
 * Created by Administrator on 2018/7/31.
 */
class CustomEvent {
    private String name;

    public CustomEvent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
