package me.theentropyshard.pita;

@FunctionalInterface
public interface Callback {
    void doWork(String... args);
}
