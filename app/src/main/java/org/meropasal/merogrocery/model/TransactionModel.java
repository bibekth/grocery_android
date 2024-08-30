package org.meropasal.merogrocery.model;

import java.util.ArrayList;
import java.util.List;

public class TransactionModel {
    String status, customer_id, payment_type;
    Message message;
    List<BillList> billLists;
    ArrayList<ProductPriceModel.Message.ProductPrices> bill;

    public TransactionModel() {
    }

    public TransactionModel(String status, Message message) {
        this.status = status;
        this.message = message;
    }

    public TransactionModel(String customer_id, String payment_type, ArrayList<ProductPriceModel.Message.ProductPrices> bill) {
        this.customer_id = customer_id;
        this.payment_type = payment_type;
        this.bill = bill;
    }

    public ArrayList<ProductPriceModel.Message.ProductPrices> getBill() {
        return bill;
    }

    public void setBill(ArrayList<ProductPriceModel.Message.ProductPrices> bill) {
        this.bill = bill;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public List<BillList> getBillLists() {
        return billLists;
    }

    public void setBillLists(List<BillList> billLists) {
        this.billLists = billLists;
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

    public class Message {
        Credit credit;
        String credits;
//        Transaction transaction;
        ArrayList<Transaction> transactionArrayList;

        public Message() {
        }

        public Message(Credit credit) {
            this.credit = credit;
        }

        public Message(Credit credit, Transaction transaction) {
            this.credit = credit;
//            this.transaction = transaction;
        }

        public Message(Credit credit, String credits, ArrayList<Transaction> transactionArrayList) {
            this.credit = credit;
            this.credits = credits;
            this.transactionArrayList = transactionArrayList;
        }

//        public Transaction getTransaction() {
//            return transaction;
//        }

        public String getCredits() {
            return credits;
        }

        public void setCredits(String credits) {
            this.credits = credits;
        }

        public ArrayList<Transaction> getTransactionArrayList() {
            return transactionArrayList;
        }

        public void setTransactionArrayList(ArrayList<Transaction> transactionArrayList) {
            this.transactionArrayList = transactionArrayList;
        }

//        public void setTransaction(Transaction transaction) {
//            this.transaction = transaction;
//        }

        public Credit getCredit() {
            return credit;
        }

        public void setCredit(Credit credit) {
            this.credit = credit;
        }

        public class Credit {
            String id, assign_customer_id, credit_amount;

            public Credit() {
            }

            public Credit(String id, String assign_customer_id, String credit_amount) {
                this.id = id;
                this.assign_customer_id = assign_customer_id;
                this.credit_amount = credit_amount;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getAssign_customer_id() {
                return assign_customer_id;
            }

            public void setAssign_customer_id(String assign_customer_id) {
                this.assign_customer_id = assign_customer_id;
            }

            public String getCredit_amount() {
                return credit_amount;
            }

            public void setCredit_amount(String credit_amount) {
                this.credit_amount = credit_amount;
            }
        }

        public class Transaction {
            String id, total_credit, created_at;
            AssignedCustomer assigned_customer;

            public Transaction() {
            }

            public Transaction(String id, String total_credit, String created_at, AssignedCustomer assigned_customer) {
                this.id = id;
                this.total_credit = total_credit;
                this.created_at = created_at;
                this.assigned_customer = assigned_customer;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTotal_credit() {
                return total_credit;
            }

            public void setTotal_credit(String total_credit) {
                this.total_credit = total_credit;
            }

            public AssignedCustomer getAssigned_customer() {
                return assigned_customer;
            }

            public void setAssigned_customer(AssignedCustomer assigned_customer) {
                this.assigned_customer = assigned_customer;
            }

            public class AssignedCustomer {
                String id, customer_id, vendor_id, assigned_name;
                AssignCustomer assign_customer;
                AssignVendor assign_vendor;

                public AssignedCustomer() {
                }

                public AssignedCustomer(String id, String customer_id, String vendor_id, String assigned_name, AssignCustomer assign_customer, AssignVendor assign_vendor) {
                    this.id = id;
                    this.customer_id = customer_id;
                    this.vendor_id = vendor_id;
                    this.assigned_name = assigned_name;
                    this.assign_customer = assign_customer;
                    this.assign_vendor = assign_vendor;
                }

                public AssignedCustomer(String id, String customer_id, String vendor_id, String assigned_name) {
                    this.id = id;
                    this.customer_id = customer_id;
                    this.vendor_id = vendor_id;
                    this.assigned_name = assigned_name;
                }

                public AssignCustomer getAssign_customer() {
                    return assign_customer;
                }

                public void setAssign_customer(AssignCustomer assign_customer) {
                    this.assign_customer = assign_customer;
                }

                public AssignVendor getAssign_vendor() {
                    return assign_vendor;
                }

                public void setAssign_vendor(AssignVendor assign_vendor) {
                    this.assign_vendor = assign_vendor;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getCustomer_id() {
                    return customer_id;
                }

                public void setCustomer_id(String customer_id) {
                    this.customer_id = customer_id;
                }

                public String getVendor_id() {
                    return vendor_id;
                }

                public void setVendor_id(String vendor_id) {
                    this.vendor_id = vendor_id;
                }

                public String getAssigned_name() {
                    return assigned_name;
                }

                public void setAssigned_name(String assigned_name) {
                    this.assigned_name = assigned_name;
                }

                public class AssignCustomer{
                    String id, name, phone_number, address, email;

                    public AssignCustomer() {
                    }

                    public AssignCustomer(String id, String name, String phone_number, String address, String email) {
                        this.id = id;
                        this.name = name;
                        this.phone_number = phone_number;
                        this.address = address;
                        this.email = email;
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
                }
                public class AssignVendor{
                    String id, firm_name, name, contact, email, address, pan_vat;

                    public AssignVendor() {
                    }

                    public AssignVendor(String id, String firm_name, String name, String contact, String email, String address, String pan_vat) {
                        this.id = id;
                        this.firm_name = firm_name;
                        this.name = name;
                        this.contact = contact;
                        this.email = email;
                        this.address = address;
                        this.pan_vat = pan_vat;
                    }

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public String getFirm_name() {
                        return firm_name;
                    }

                    public void setFirm_name(String firm_name) {
                        this.firm_name = firm_name;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getContact() {
                        return contact;
                    }

                    public void setContact(String contact) {
                        this.contact = contact;
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

                    public String getPan_vat() {
                        return pan_vat;
                    }

                    public void setPan_vat(String pan_vat) {
                        this.pan_vat = pan_vat;
                    }
                }
            }
        }
    }

    public class BillList{
        String product_price_id, quantity, total_amount;

        public BillList() {
        }

        public BillList(String product_price_id, String quantity, String total_amount) {
            this.product_price_id = product_price_id;
            this.quantity = quantity;
            this.total_amount = total_amount;
        }

        public String getProduct_price_id() {
            return product_price_id;
        }

        public void setProduct_price_id(String product_price_id) {
            this.product_price_id = product_price_id;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(String total_amount) {
            this.total_amount = total_amount;
        }
    }
}
