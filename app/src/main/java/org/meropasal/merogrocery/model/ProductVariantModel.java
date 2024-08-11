package org.meropasal.merogrocery.model;

import java.util.ArrayList;

public class ProductVariantModel {
    String status;
    ArrayList<Products> products;
    ArrayList<Variants> variants;

    public ProductVariantModel() {
    }

    public ProductVariantModel(String status, ArrayList<Products> products, ArrayList<Variants> variants) {
        this.status = status;
        this.products = products;
        this.variants = variants;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Products> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Products> products) {
        this.products = products;
    }

    public ArrayList<Variants> getVariants() {
        return variants;
    }

    public void setVariants(ArrayList<Variants> variants) {
        this.variants = variants;
    }

    public class Products {
        String id, name;

        public Products() {
        }

        public Products(String id, String name) {
            this.id = id;
            this.name = name;
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
    }

    public class Variants {
        String id, name, variant_code;

        public Variants() {
        }

        public Variants(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public Variants(String id, String name, String variant_code) {
            this.id = id;
            this.name = name;
            this.variant_code = variant_code;
        }

        public String getVariant_code() {
            return variant_code;
        }

        public void setVariant_code(String variant_code) {
            this.variant_code = variant_code;
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
    }
}
