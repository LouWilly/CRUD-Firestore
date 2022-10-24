package com.example.crud

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.jar.Attributes

class UpdateActivity : AppCompatActivity() {
	private var lastId = ""
	private val firestore = Firebase.firestore

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_update)
		supportActionBar!!.hide()

		val products = ArrayList<String>()
		val nameEditText = findViewById<EditText>(R.id.edtNome)
		val adressEditText = findViewById<EditText>(R.id.edtEndereco)
		val boroughEditText = findViewById<EditText>(R.id.edtBairro)
		val cepEditText = findViewById<EditText>(R.id.edtCep)
		val updateButton = findViewById<Button>(R.id.updateButton)
		val backButton = findViewById<Button>(R.id.backButton)

		updateButton.setOnClickListener {
			val productsCollection = firestore.collection("product")
			val data = hashMapOf(
				"name" to nameEditText.text.toString(),
				"adress" to adressEditText.text.toString(),
				"borough" to boroughEditText.text.toString(),
				"cep" to cepEditText.text.toString().toDouble()

			)
			productsCollection.document(lastId).set(data)
				.addOnSuccessListener {
					Toast.makeText(this, "Product has been updated successfully", Toast.LENGTH_SHORT).show()
					goBack()
				}
		}

		backButton.setOnClickListener { goBack() }

		firestore.collection("product").get()
			.addOnSuccessListener { result ->
				for (product in result) products.add(product.id)

		}
	}

	private fun goBack() {
		val intent = Intent(this, MainActivity::class.java)
		startActivity(intent)
		finish()
	}

	fun grabDocumentData(id: String) {
		val nameEditText = findViewById<EditText>(R.id.edtNome)
		val adressEditText = findViewById<EditText>(R.id.edtEndereco)
		val boroughEditText = findViewById<EditText>(R.id.edtBairro)
		val cepEditText = findViewById<EditText>(R.id.edtCep)

		val firestoreProducts = firestore.collection("product")
		firestoreProducts.document(id).get()
			.addOnSuccessListener { result ->
				val productName = result.data?.get("name").toString()
				val productPrice = result.data?.get("price").toString()


				nameEditText.setText(nameEditText)
				adressEditText.setText(adressEditText)
				boroughEditText.setText(boroughEditText)
				cepEditText.setText(cepEditText)
			}
	}
}

private fun EditText.setText(nameEditText: EditText?) {

}
