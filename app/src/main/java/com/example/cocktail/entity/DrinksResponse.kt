package com.example.cocktail.entity

import com.example.cocktail.domain.Drink
import com.google.gson.annotations.SerializedName

data class DrinksResponse(
    @SerializedName("drinks") val drinks: List<DrinkResponse>?
)

data class DrinkResponse(
    @SerializedName("idDrink") val id: Int?,
    @SerializedName("strDrink") val name: String?,
    @SerializedName("strDrinkThumb") val thumb: String?
)

fun DrinkResponse.toDrink(): Drink? {
    if (id != null && name != null && thumb != null) {
        return Drink(id = id, name = name, thumb = thumb)
    }
    return null
}
