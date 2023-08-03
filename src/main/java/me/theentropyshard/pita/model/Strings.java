/*      Pita. A simple desktop client for NetSchool by irTech
 *      Copyright (C) 2022-2023 TheEntropyShard
 *
 *      This program is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *
 *      This program is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package me.theentropyshard.pita.model;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public final class Strings {
    private static final Map<String, String> strings = new HashMap<>();

    public static void init(InputStream inputStream) throws Exception {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(inputStream);
        doc.getDocumentElement().normalize();

        NodeList childNodes = doc.getElementsByTagName("strings").item(0).getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            Strings.strings.put(item.getNodeName(), item.getTextContent());
        }
    }

    public static String get(String key) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Ключ не может быть пустым или равным null");
        }

        if (Strings.strings.containsKey(key)) {
            return Strings.strings.get(key);
        } else {
            throw new RuntimeException("Отсутствует строка для ключа '" + key + "'");
        }
    }

    private Strings() {
        throw new UnsupportedOperationException();
    }
}
