package org.meropasal.merogrocery.model;

import java.util.List;

public class ProductPriceModel {
    String status;
    Message message;
    RequiredParam requiredParam;

    public ProductPriceModel() {
    }

    public ProductPriceModel(String status, Message message, RequiredParam requiredParam) {
        this.status = status;
        this.message = message;
        this.requiredParam = requiredParam;
    }

    public RequiredParam getRequiredParam() {
        return requiredParam;
    }

    public void setRequiredParam(RequiredParam requiredParam) {
        this.requiredParam = requiredParam;
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
        List<ProductPrices> productPrices;

        public Message() {
        }

        public Message(List<ProductPrices> productPrices) {
            this.productPrices = productPrices;
        }

        public List<ProductPrices> getProductPrices() {
            return productPrices;
        }

        public void setProductPrices(List<ProductPrices> productPrices) {
            this.productPrices = productPrices;
        }

        public static class ProductPrices{
            String id, vendor_id, product_id, variant_id, quantity, price;
            Product product;
            Variant variant;
            Vendor vendor;

            public ProductPrices() {
            }

            public ProductPrices(String product_id, String quantity, String price) {
                this.product_id = product_id;
                this.quantity = quantity;
                this.price = price;
            }

            public ProductPrices(String id, String vendor_id, String product_id, String variant_id, String quantity, String price, Product product, Variant variant, Vendor vendor) {
                this.id = id;
                this.vendor_id = vendor_id;
                this.product_id = product_id;
                this.variant_id = variant_id;
                this.quantity = quantity;
                this.price = price;
                this.product = product;
                this.variant = variant;
                this.vendor = vendor;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getVendor_id() {
                return vendor_id;
            }

            public void setVendor_id(String vendor_id) {
                this.vendor_id = vendor_id;
            }

            public String getProduct_id() {
                return product_id;
            }

            public void setProduct_id(String product_id) {
                this.product_id = product_id;
            }

            public String getVariant_id() {
                return variant_id;
            }

            public void setVariant_id(String variant_id) {
                this.variant_id = variant_id;
            }

            public String getQuantity() {
                return quantity;
            }

            public void setQuantity(String quantity) {
                this.quantity = quantity;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public Product getProduct() {
                return product;
            }

            public void setProduct(Product product) {
                this.product = product;
            }

            public Variant getVariant() {
                return variant;
            }

            public void setVariant(Variant variant) {
                this.variant = variant;
            }

            public Vendor getVendor() {
                return vendor;
            }

            public void setVendor(Vendor vendor) {
                this.vendor = vendor;
            }

            public static class Product{
                String name, description;

                public Product() {
                }

                public Product(String name, String description) {
                    this.name = name;
                    this.description = description;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }
            }

            public static class Variant{
                String name, variant_code,  description;

                public Variant() {
                }

                public Variant(String name, String variant_code, String description) {
                    this.name = name;
                    this.variant_code = variant_code;
                    this.description = description;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getVariant_code() {
                    return variant_code;
                }

                public void setVariant_code(String variant_code) {
                    this.variant_code = variant_code;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }
            }

            public static class Vendor{
                String firm_name, name,  contact, email, address, pan_vat;

                public Vendor() {
                }

                public Vendor(String firm_name, String name, String contact, String email, String address, String pan_vat) {
                    this.firm_name = firm_name;
                    this.name = name;
                    this.contact = contact;
                    this.email = email;
                    this.address = address;
                    this.pan_vat = pan_vat;
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

    public static class RequiredParam{
        String product_id, variant_id, quantity, price, status;

        public RequiredParam() {
        }

        public RequiredParam(String product_id, String variant_id, String quantity, String price) {
            this.product_id = product_id;
            this.variant_id = variant_id;
            this.quantity = quantity;
            this.price = price;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getVariant_id() {
            return variant_id;
        }

        public void setVariant_id(String variant_id) {
            this.variant_id = variant_id;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }

    public static class postProductPrice{
        String product_price_id, quantity, total_amount;

        public postProductPrice() {
        }

        public postProductPrice(String product_price_id, String quantity, String total_amount) {
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
