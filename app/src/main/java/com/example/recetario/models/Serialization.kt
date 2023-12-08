package com.example.recetario.models

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class Serialization {
    companion object {
        fun serializeRecipe(recipe: Recipe): ByteArray {
            val byteArrayOutputStream = ByteArrayOutputStream()
            val objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
            objectOutputStream.writeObject(recipe)
            objectOutputStream.close()

            return byteArrayOutputStream.toByteArray()
        }

        fun deserializeRecipe(byteArray: ByteArray): Recipe {
            val byteArrayInputStream = ByteArrayInputStream(byteArray)
            val objectInputStream = ObjectInputStream(byteArrayInputStream)

            val recipe = objectInputStream.readObject() as Recipe
            objectInputStream.close()

            return recipe
        }
    }
}