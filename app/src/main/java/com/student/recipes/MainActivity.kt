package com.student.recipes

import android.graphics.Color
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import java.io.InputStream

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val container = findViewById<LinearLayout>(R.id.recipeContainer)

        try {
            val inputStream: InputStream = assets.open("recipes.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            
            val jsonString = String(buffer, Charsets.UTF_8)
            val jsonArray = JSONArray(jsonString)
            
            for (i in 0 until jsonArray.length()) {
                val recipe = jsonArray.getJSONObject(i)
                val name = recipe.getString("name")
                val calories = recipe.getInt("calories")
                val ingredients = recipe.getJSONArray("ingredients")
                
                var ingredientsList = ""
                for (j in 0 until ingredients.length()) {
                    ingredientsList += "• " + ingredients.getString(j) + "\n"
                }

                val recipeView = TextView(this)
                recipeView.text = "$name\n🔥 Calories: $calories kcal\nIngredients:\n$ingredientsList"
                recipeView.textSize = 18f
                recipeView.setPadding(0, 0, 0, 48)
                recipeView.setTextColor(Color.DKGRAY)
                
                container.addView(recipeView)
            }
        } catch (e: Exception) {
            val errorView = TextView(this)
            errorView.text = "System Error: ${e.message}"
            container.addView(errorView)
        }
    }
}
