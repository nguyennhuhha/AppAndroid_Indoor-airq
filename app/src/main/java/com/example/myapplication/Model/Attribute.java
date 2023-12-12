package com.example.myapplication.Model;

import android.text.InputType;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;

public class Attribute {
    @SerializedName("name")
    public String name;

    @SerializedName("type")
    public String type;

    @SerializedName("meta")
    public JsonObject meta;

    @SerializedName("value")
    public JsonElement value = new JsonParser().parse("");;

    public Attribute(String name, String type) {
        this.name = name;
        this.type = type;
    }


    public String getValueString() {
        if (value.isJsonNull()) return "";
        else if (value.isJsonObject()) return Attribute.formatJsonValue(String.valueOf(value.getAsJsonObject()));
        else if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isNumber()) return String.valueOf(value.getAsDouble());
        else if (value.isJsonPrimitive() && value.getAsJsonPrimitive().isNumber()) return String.valueOf(value.getAsDouble());
        else return value.getAsString();
    }

    public static String formatJsonValue(String text){
        StringBuilder json = new StringBuilder();
        String indentString = "";

        for (int i = 0; i < text.length(); i++) {
            char letter = text.charAt(i);
            switch (letter) {
                case '{':
                case '[':
                    if (i != 0) {
                        json.append("\n");
                    }
                    json.append(indentString).append(letter).append("\n");
                    indentString = indentString + "\t\t";
                    json.append(indentString);
                    break;
                case '}':
                case ']':
                    indentString = indentString.replaceFirst("\t\t", "");
                    json.append("\n").append(indentString).append(letter);
                    break;
                case ',':
                    json.append(letter).append("\n").append(indentString);
                    break;
                default:
                    json.append(letter);
                    break;
            }
        }

        return json.toString();
    }
}
