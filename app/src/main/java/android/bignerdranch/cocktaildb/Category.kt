package android.bignerdranch.cocktaildb

data class Category(val name: String, var isSelected: Boolean) {


    override fun toString(): String {
        return "$name-$isSelected"
    }
}