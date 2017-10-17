package com.example.ksentini.projectandroid;

/**
 * Created by ksentini on 08/05/17.
 */

public class pizza1 {


        private int id_pizza ;
        private int prix_pizza ;
        private String nom_pizza ;
        private String description_pizza ;

        public pizza1() {
        }

        public pizza1(int id_pizza, int prix_pizza, String nom_pizza, String description_pizza) {
            this.id_pizza = id_pizza;
            this.prix_pizza = prix_pizza;
            this.nom_pizza = nom_pizza;
            this.description_pizza = description_pizza;
        }

        public String getDescription_pizza() {
            return description_pizza;
        }

        public int getId_pizza() {
            return id_pizza;
        }

        public String getNom_pizza() {
            return nom_pizza;
        }

        public void setDescription_pizza(String description_pizza) {
            this.description_pizza = description_pizza;
        }

        public int getPrix_pizza() {
            return prix_pizza;
        }

        public void setId_pizza(int id_pizza) {
            this.id_pizza = id_pizza;
        }

        public void setNom_pizza(String nom_pizza) {
            this.nom_pizza = nom_pizza;
        }

        public void setPrix_pizza(int prix_pizza) {
            this.prix_pizza = prix_pizza;
        }

    }


