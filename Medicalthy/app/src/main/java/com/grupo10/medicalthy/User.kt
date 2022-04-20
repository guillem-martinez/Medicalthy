package com.grupo10.medicalthy

class User(val username: String,
           val name: String,
           val surname: String,
           val age: Int,
           var esCuidador: Boolean,
           var productList: MutableList<Product>,
           var patients: MutableList<User>? = null ) {

    fun añadirMedicamentos(name: String, cnn: String, nComprimidos: Int, frecuency: Int) {
        var newProduct = Product(name, cnn, nComprimidos, frecuency)
        productList.add(newProduct)
    }
}