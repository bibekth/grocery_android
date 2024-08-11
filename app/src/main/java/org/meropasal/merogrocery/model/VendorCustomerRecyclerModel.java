package org.meropasal.merogrocery.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VendorCustomerRecyclerModel {
    private String status;
    private Message message;

    public VendorCustomerRecyclerModel() {
    }

    public VendorCustomerRecyclerModel(String status, Message message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public static class Message{
        private List<Customer> customer;
        private List<Vendor> vendor;
        public Message() {
        }

        public Message(List<Customer> customer, List<Vendor> vendor) {
            this.customer = customer;
            this.vendor = vendor;
        }

        @SerializedName("customer")
        public List<Customer> getCustomer() {
            return customer;
        }

        public void setCustomer(List<Customer> customer) {
            this.customer = customer;
        }

        public List<Vendor> getVendor() {
            return vendor;
        }

        public void setVendor(List<Vendor> vendor) {
            this.vendor = vendor;
        }

        public static class Customer implements Parcelable {
            private Integer id;
            private String name, phone_number, address, email, status, assigned_name, amount;

            public Customer() {
            }

            public Customer(Integer id, String name, String phone_number, String address, String email, String status, String amount, String assigned_name) {
                this.id = id;
                this.name = name;
                this.phone_number = phone_number;
                this.address = address;
                this.email = email;
                this.status = status;
                this.assigned_name = assigned_name;
                this.amount = amount;
            }

            protected Customer(Parcel in) {
                if (in.readByte() == 0) {
                    id = null;
                } else {
                    id = in.readInt();
                }
                name = in.readString();
                phone_number = in.readString();
                address = in.readString();
                email = in.readString();
                status = in.readString();
                assigned_name = in.readString();
                amount = in.readString();
            }

            public static final Creator<Customer> CREATOR = new Creator<Customer>() {
                @Override
                public Customer createFromParcel(Parcel in) {
                    return new Customer(in);
                }

                @Override
                public Customer[] newArray(int size) {
                    return new Customer[size];
                }
            };

            public String getAssigned_name() {
                return assigned_name;
            }

            public void setAssigned_name(String assigned_name) {
                this.assigned_name = assigned_name;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
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

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(@NonNull Parcel dest, int flags) {
                if (id == null) {
                    dest.writeByte((byte) 0);
                } else {
                    dest.writeByte((byte) 1);
                    dest.writeInt(id);
                }
                dest.writeString(name);
                dest.writeString(phone_number);
                dest.writeString(address);
                dest.writeString(email);
                dest.writeString(status);
                dest.writeString(assigned_name);
                dest.writeString(amount);
            }
        }

        public static class Vendor{
            private Integer id;
            private String name, email, phone_number, address, pan_vat, status;

            public Vendor() {
            }

            public Vendor(Integer id, String name, String email, String phone_number, String address, String pan_vat, String status) {
                this.id = id;
                this.name = name;
                this.email = email;
                this.phone_number = phone_number;
                this.address = address;
                this.pan_vat = pan_vat;
                this.status = status;
            }

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getPhone_number() {
                return phone_number;
            }

            public void setPhone_number(String phone_number) {
                this.phone_number = phone_number;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getPan_vat() {
                return pan_vat;
            }

            public void setPan_vat(String pan_vat) {
                this.pan_vat = pan_vat;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }
}
