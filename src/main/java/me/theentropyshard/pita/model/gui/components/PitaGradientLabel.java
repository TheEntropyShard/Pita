/*
 *  Copyright 2022-2023 TheEntropyShard
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package me.theentropyshard.pita.model.gui.components;

import me.theentropyshard.pita.model.gui.components.ui.PitaGradientLabelUI;

import javax.swing.*;
import java.awt.*;

public class PitaGradientLabel extends JLabel {
    private Color leftColor;
    private Color rightColor;

    public PitaGradientLabel() {
        this.setOpaque(false);
        this.setUI(new PitaGradientLabelUI());
    }

    public PitaGradientLabel(String text) {
        this();
        this.setText(text);
    }

    public Color getLeftColor() {
        return this.leftColor;
    }

    public void setLeftColor(Color leftColor) {
        this.leftColor = leftColor;
    }

    public Color getRightColor() {
        return this.rightColor;
    }

    public void setRightColor(Color rightColor) {
        this.rightColor = rightColor;
    }
}
