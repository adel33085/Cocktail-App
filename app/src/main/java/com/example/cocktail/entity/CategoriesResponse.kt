package com.example.cocktail.entity

import com.google.gson.annotations.SerializedName

data class FilterCategoriesResponse(
    @SerializedName("drinks") val Categories: List<CategoryResponse>?
)

data class CategoryResponse(
    @SerializedName("strCategory") val category: String?
)
