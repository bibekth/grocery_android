package org.meropasal.merogrocery.model;

import java.util.List;

public class AllCustomerModel {
    String status;
    private List<Data> data;

    private Data singleData;

    public AllCustomerModel() {
    }

    public AllCustomerModel(String status, List<Data> data) {
        this.status = status;
        this.data = data;
    }

    public AllCustomerModel(Data singleData) {
        this.singleData = singleData;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data{
        String id, name, phone_number, email, address, status;

        public Data() {
        }

        public Data(String id, String name, String phone_number, String email, String address, String status) {
            this.id = id;
            this.name = name;
            this.phone_number = phone_number;
            this.email = email;
            this.address = address;
            this.status = status;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
