package me.theentropyshard.pita;

import me.theentropyshard.netschoolapi.NetSchoolAPI;
import me.theentropyshard.pita.view.View;

import javax.swing.*;

public final class Pita {
    private final NetSchoolAPI api;

    private final View view;

    public Pita() {
        if(pita != null) {
            throw new IllegalStateException("Only one Pita instance can exist at a time");
        }
        pita = this;

        this.api = new NetSchoolAPI();

        this.view = new View();
        SwingUtilities.invokeLater(() -> this.view.setVisible(true));

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            synchronized (this.api) {
                this.api.logout();
            }
        }));
    }

    public NetSchoolAPI getAPI() {
        return this.api;
    }

    private static Pita pita;

    public static Pita getPita() {
        return pita;
    }
}
