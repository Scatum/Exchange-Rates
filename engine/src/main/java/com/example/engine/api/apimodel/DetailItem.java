package com.example.engine.api.apimodel;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class DetailItem {

    @SerializedName("date")
    private double date;

    @SerializedName("list")
    private Map<String, DetailListitem> list;


    class DetailListitem {

        @SerializedName("head")
        private int head;

        @SerializedName("title")
        private Map<String, String> title;

        @SerializedName("address")
        private Map<String, String> address;

        @SerializedName("location")
        private Map<String, Float> location;

        @SerializedName("contacts")
        private String contacts;

        @SerializedName("workhours")
        private List<JsonElement> workhours;

        public int getHead() {
            return head;
        }

        public void setHead(int head) {
            this.head = head;
        }

        public Map<String, String> getTitle() {
            return title;
        }

        public void setTitle(Map<String, String> title) {
            this.title = title;
        }

        public Map<String, String> getAddress() {
            return address;
        }

        public void setAddress(Map<String, String> address) {
            this.address = address;
        }

        public Map<String, Float> getLocation() {
            return location;
        }

        public void setLocation(Map<String, Float> location) {
            this.location = location;
        }

        public String getContacts() {
            return contacts;
        }

        public void setContacts(String contacts) {
            this.contacts = contacts;
        }

        public List<JsonElement> getWorkhours() {
            return workhours;
        }

        public void setWorkhours(List<JsonElement> workhours) {
            this.workhours = workhours;
        }
    }

}
