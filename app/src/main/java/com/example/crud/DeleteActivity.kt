package com.example.crud

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DeleteActivity : AppCompatActivity() {
	private var lastId = ""
	private val firestore = Firebase.firestore

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_delete)
		supportActionBar!!.hide()

		val products = ArrayList<String>()

		val deleteButton = findViewById<Button>(R.id.deleteButton)
		val backButton = findViewById<Button>(R.id.backButton)

		deleteButton.setOnClickListener {
			val productsCollection = firestore.collection("product")
			productsCollection.document(lastId).delete()
				.addOnSuccessListener {
					Toast.makeText(this, "Product has been deleted successfully", Toast.LENGTH_SHORT).show()
					goBack()
				}
		}

		firestore.collection("product").get()
			.addOnSuccessListener { result ->
				for (product in result) products.add(product.id)

			}


		backButton.setOnClickListener { goBack() }
	}

	private fun goBack() {
		val intent = Intent(this, MainActivity::class.java)
		startActivity(intent)
		finish()
	}
}